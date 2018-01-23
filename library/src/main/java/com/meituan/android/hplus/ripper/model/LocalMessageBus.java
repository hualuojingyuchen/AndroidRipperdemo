package com.meituan.android.hplus.ripper.model;

import android.os.Looper;
import android.util.Pair;

import com.meituan.android.hplus.ripper.debug.DebugOption;
import com.meituan.android.hplus.ripper.debug.Logger;
import com.meituan.android.hplus.ripper.util.Crash;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * Created by huzhaoxu on 2016/12/30.
 */

class LocalMessageBus {

    private static final int DEFAULT_MAX_TTL = 10;

    private Map<String, Subject> presenterMap;

    private Set<String> defaultKeySet;

    private Set<String> calledKeySet;

    private BlockingQueue<MessageBean> notifyQueue;

    private int maxTTL;

    private int currentTTL;

    private boolean inSpread = false;

    LocalMessageBus() {
        presenterMap = new HashMap<>();
        calledKeySet = new HashSet<>();
        defaultKeySet = new HashSet<>();
        notifyQueue = new LinkedBlockingQueue<>();
        maxTTL = DEFAULT_MAX_TTL;
    }

    /**
     * 设置最多消息传递次数
     * @param ttl 消息传递的最大次数
     */
    void setTTL(int ttl) {
        maxTTL = ttl;
    }

    /**
     * 增加缺省key，缺省key被作为内部通信使用，禁止业务方使用相同的key
     * @param key
     */
    void setDefaultKey(String key) {
        defaultKeySet.add(key);
        presenterMap.put(key, PublishSubject.create());
    }

    /**
     * 清除所有ttl和key记录，保证下一次能够正常
     */
    private void finish() {
        Logger.markStart();
        currentTTL = 0;
        calledKeySet.clear();
        inSpread = false;
        Logger.markEnd();
    }

    /**
     * 通过缺省key发送消息。缺省不受ttl限制
     * @param key 内部使用的缺省的key
     * @param data 对应的数据
     */
    void notifyLast(String key, Object data){
        Logger.markStart();
        Logger.i("notifyLast-" + key, data);
        Iterator<MessageBean> iterator = notifyQueue.iterator();
        MessageBean bean = null;
        while(iterator.hasNext()){
            MessageBean currentBean = iterator.next();
            if(currentBean.pair != null && currentBean.pair.first.equals(key)){
                bean = currentBean;
                break;
            }
        }
        if(bean != null){
            Logger.i("move " + key +  " to last");
            notifyQueue.remove(bean);
            notifyQueue.add(bean);
        }else{
            Logger.i("add key: " + key);
            bean = new MessageBean();
            bean.pair = new Pair<String, Object>(key, data);
            notifyQueue.add(bean);
        }
        spread();
        Logger.i("queue" , notifyQueue);
        Logger.markEnd();
    }

    /**
     * 发送公共消息
     * @param key 公共的key
     * @param data 公共的数据
     */
    void notify(String key, Object data) {
        Logger.markStart();
        Logger.i(key, data);
        if (calledKeySet.contains(key)) {
            if(DebugOption.isDebug()){
                Logger.i(key , calledKeySet);
                Logger.e(key + " exist");
                finish();
                throw new IllegalArgumentException("key has been notified, please check if blocks have notify circulation");
            }
        } else if (defaultKeySet.contains(key)) {
            if(DebugOption.isDebug()){
                Logger.e(key + " is default");
                finish();
                throw new IllegalArgumentException("key has been occupied by default action");
            }
        }
        calledKeySet.add(key);
        MessageBean bean = new MessageBean();
        bean.pair = new Pair<String, Object>(key, data);
        notifyQueue.add(bean);
        spread();
        Logger.markEnd();
    }

    /**
     * 发送一组公共消息
     * @param data 公共消息列表
     */
    void notify(List<Pair<String, Object>> data) {
        Logger.markStart();
        Logger.i("list", data);
        for (Pair<String,Object> pair : data) {
            if (calledKeySet.contains(pair.first)) {
                if(DebugOption.isDebug()){
                    Logger.e(pair.first + " exist");
                    finish();
                    throw new IllegalArgumentException("key has been notified, please check if blocks have notify circulation");
                }
                return;
            } else if (defaultKeySet.contains(pair.first)) {
                finish();
                if(DebugOption.isDebug()){
                    throw new IllegalArgumentException("key has been occupied by default action");
                }
                return;
            }
            calledKeySet.add(pair.first);
        }
        MessageBean bean = new MessageBean();
        bean.pairList = data;
        notifyQueue.add(bean);
        Logger.i("datas" , notifyQueue);
        spread();
        Logger.markEnd();
    }

    /**
     * 扩散公共消息。保证后notify的消息在在先notify的消息后被扩散
     */
    private void spread(){
        Logger.markStart();
        if(Looper.myLooper() != Looper.getMainLooper()){
            finish();
            if(DebugOption.isDebug()){
                throw new IllegalStateException("this method must run on UI thread");
            }else{
                return;
            }
        }
        if (!inSpread) {
            Logger.i("spread start");
            inSpread = true;
            MessageBean currentBean;
            while ((currentBean = notifyQueue.poll()) != null) {
                if (currentTTL >= maxTTL) {
                    if(DebugOption.isDebug()){
                        finish();
                        throw new IllegalStateException("exceed max ttl");
                    }
                }
                if (currentBean.pair != null) {
                    Logger.i("spread:" + currentBean.pair.first);
                    notifyKey(currentBean.pair.first, currentBean.pair.second);
                    if(defaultKeySet.contains(currentBean.pair.first)){
                        //defaultkey不计入ttl
                        finish();
                    }else{
                        currentTTL++;
                    }
                } else if (currentBean.pairList != null) {
                    //这里不可能有default的，暂时这么假定
                    for (Pair<String, Object> pair : currentBean.pairList) {
                        Logger.i("spread:" + pair.first);
                        notifyKey(pair.first, pair.second);
                    }
                    currentTTL++;
                }
            }
            inSpread = false;
            Logger.i("spread end");
        }else{
            Logger.i("wait for spread");
        }
        Logger.markEnd();
    }

    /**
     * 获取一个公共数据的观察者
     * @param key 公共数据对应的key
     * @param <T> 公共数据的范型
     * @return
     */
    <T> Observable<T> getObservable(String key, Class<T> clazz) {
        if (presenterMap.get(key) == null) {
            presenterMap.put(key, PublishSubject.create());
        }
        return presenterMap.get(key).asObservable();
    }

    /**
     * 扩散一个单个公共消息
     * @param key 公共消息的key
     * @param data 公共消息的数据
     */
    private void notifyKey(String key, Object data) {
        Logger.markStart();
        if (presenterMap.get(key) == null) {
            presenterMap.put(key, PublishSubject.create());
        }
        try{
            presenterMap.get(key).onNext(data);
        }catch (Throwable t){
            Crash.crashAndExit(t);
        }
        Logger.markEnd();
    }

}
