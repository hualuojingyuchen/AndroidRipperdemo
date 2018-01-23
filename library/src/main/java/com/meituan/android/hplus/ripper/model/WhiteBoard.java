package com.meituan.android.hplus.ripper.model;

import android.os.Bundle;
import android.util.Pair;

import com.meituan.android.hplus.ripper.block.BlockManager;
import com.meituan.android.hplus.ripper.block.IBlock;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by huzhaoxu on 2016/12/2.
 */

public class WhiteBoard {

    public static final String CREATE_VIEW_KEY = "create_view";

    public static final String UPDATE_VIEW_KEY = "update_view";

    private DataCenter dataCenter;

    private LocalMessageBus messageBus;

    private ServiceCenter serviceCenter;

    private ModelContainer modelContainer;

    private List<BlockManager> managers;


    public WhiteBoard() {
        dataCenter = new DataCenter();
        messageBus = new LocalMessageBus();
        serviceCenter = new ServiceCenter();
        modelContainer = new ModelContainer(this);
        managers = new ArrayList<>();
        messageBus.setDefaultKey(CREATE_VIEW_KEY);
        messageBus.setDefaultKey(UPDATE_VIEW_KEY);
    }

    public void addBlockManager(BlockManager manager) {
        if (manager != null && !managers.contains(manager)) {
            managers.add(manager);
        }
    }

    /**
     * 发送createView消息，供layoutmanager使用
     */
    public void notifyCreateView() {
        messageBus.notifyLast(CREATE_VIEW_KEY, null);
    }

    /**
     * 发送一个公共数据
     *
     * @param key    公共数据的key
     * @param object 公共数据的数据
     */
    public void notify(String key, Object object) {
        dataCenter.storePublicData(key, object);
        for (BlockManager manager : managers) {
            manager.addConcernKeys(key);
        }
        messageBus.notify(key, object);
        updateViewAll();
    }

    /**
     * 发送一组公共事件
     *
     * @param dataList 公共数据列表
     */
    public void notify(List<Pair<String, Object>> dataList) {
        for (Pair<String, Object> pair : dataList) {
            dataCenter.storePublicData(pair.first, pair.second);
            for (BlockManager manager : managers) {
                manager.addConcernKeys(pair.first);
            }
        }
        messageBus.notify(dataList);
        updateViewAll();
    }


    /**
     * 发送一个私有数据,并更新block自身
     *
     * @param key    私有数据的key
     * @param object 私有数据
     * @param block  数据所属的block
     */
    public void notifyPrivate(String key, Object object, IBlock block) {
        dataCenter.storePrivateData(key, block.getClass(), object);
        updateView(block);
    }

    /**
     * 发送一组私有数据,并更新block自身
     *
     * @param dataList 私有数据列表
     * @param block    数据所属的block
     */
    public void notifyPrivate(List<Pair<String, Object>> dataList, IBlock block) {
        for (Pair<String, Object> pair : dataList) {
            dataCenter.storePrivateData(pair.first, block.getClass(), pair.second);
        }
        updateView(block);
    }

    public void setTTL(int ttl) {
        messageBus.setTTL(ttl);
    }

    private void createViewAll() {
        messageBus.notifyLast(CREATE_VIEW_KEY, null);
    }

    private void createView(IBlock block) {
        messageBus.notifyLast(CREATE_VIEW_KEY, block);
    }

    /**
     * 通知全部block更新(并不能保证View在被调用之后就立刻被更新，比如ListView，RecyclerView)
     */
    private void updateViewAll() {
        messageBus.notifyLast(UPDATE_VIEW_KEY, null);
    }

    /**
     * 通知特定block更新(并不能保证View在被调用之后就立刻被更新，比如ListView，RecyclerView)
     *
     * @param block 需要更新的block
     */
    private void updateView(IBlock block) {
        messageBus.notifyLast(UPDATE_VIEW_KEY, block);
    }

    /**
     * 获取特定类型的共有数据
     *
     * @param key   数据的key
     * @param clazz 数据期待的类型
     * @param <T>   数据类型的范型
     * @return 存储的共有数据
     */
    public <T> T getData(String key, Class<T> clazz) {
        return dataCenter.getPublicData(key, clazz);
    }

    /**
     * 获取特定类型的私有数据
     *
     * @param key   数据的key
     * @param clazz 数据期待的类型
     * @param block 拥有私有数据的block
     * @param <T>   数据类型的范型
     * @return 存储的私有数据
     */
    public <T> T getPrivateData(String key, Class<T> clazz, IBlock block) {
        return dataCenter.getPrivateData(key, clazz, block.getClass());
    }

    /**
     * 获取特定类型私有数据，但是不加入到ui更新列表。主要在外部容器使用
     * 如果需要加入更新列表，请使用{@link #getObservable(String,Class, IBlock)}.
     * @param key 数据的key
     * @param clazz 期待的Observable范型
     * @param <T> 范型
     * @return 该key关联的Observable
     */
    public <T> Observable<T> getObservable(String key, Class<T> clazz) {
        return getObservable(key, clazz, null);
    }

    /**
     * 获取特定类型公有数据，并加入更新列表。主要供block使用
     * @param key 数据的key
     * @param clazz 期待的Observable范型
     * @param block 需要被通知的block
     * @param <T> 范型
     * @return 该key关联的Observable
     */
    public <T> Observable<T> getObservable(String key, Class<T> clazz, IBlock block) {
        if (block != null) {
            for (BlockManager manager : managers) {
                manager.addBlockConcern(block, key);
            }
        }
        return messageBus.getObservable(key, clazz);
    }

    public void registerAsyncModel(AbstractModel model) {
        modelContainer.addModel(model);
    }

    public void getAsyncData(String key) {
        modelContainer.requestData(key);
    }

    public <T> void registerService(T service, Class<T> clazz) {
        serviceCenter.registerService(service, clazz);
    }

    public void unRegisterService(Class clazz) {
        serviceCenter.unRegisterService(clazz);
    }

    public <T> T getService(Class<T> clazz) {
        return serviceCenter.getService(clazz);
    }

    public void onSaveInstance(Bundle bundle) {
        dataCenter.onSaveInstance(bundle);
    }

    public void onRestoreInstance(Bundle bundle) {
        dataCenter.onRestoreInstance(bundle);
    }

}
