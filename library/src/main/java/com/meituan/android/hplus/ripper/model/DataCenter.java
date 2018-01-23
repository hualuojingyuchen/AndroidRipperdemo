package com.meituan.android.hplus.ripper.model;

import android.os.Bundle;
import android.util.Pair;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by huzhaoxu on 2016/12/30.
 */

class DataCenter {

    private Map<String, Object> publicDataMap;
    private Map<Class, Map<String, Object>> privateDataMap;

    private static final String PUBLIC_KEY = "public_data_map";
    private static final String PRIVATE_KEY = "private_data_map";


    DataCenter() {
        publicDataMap = new HashMap<>();
        privateDataMap = new HashMap<>();
    }

    /**
     * 存储一个公共数据。如果数据不是Serializable，那么在onRestoreInstance不会被恢复
     *
     * @param key  存储数据的键
     * @param data 存储数据的值
     */
    void storePublicData(String key, Object data) {
        publicDataMap.put(key, data);
    }


    /**
     * 存储一个私有数据。如果数据不是Serializable，那么在onRestoreInstance不会被恢复
     *
     * @param key   存储数据的键
     * @param clazz 私有数据所对应block的类
     * @param data  存储数据的值
     */
    void storePrivateData(String key, Class clazz, Object data) {
        if (privateDataMap.get(clazz) == null) {
            privateDataMap.put(clazz, new HashMap<String, Object>());
        }
        privateDataMap.get(clazz).put(key, data);
    }

    /**
     * 读取一个共有数据
     *
     * @param key   存储数据的键
     * @param clazz 存储数据的类型
     * @param <T>   存储数据的类型
     * @return 存储的共有数据
     */
    <T> T getPublicData(String key, Class<T> clazz) {
        Object data = publicDataMap.get(key);
        if(data == null){
            return null;
        }
        if (clazz.isInstance(data)) {
            return (T) data;
        } else {
            throw new ClassCastException("data is not class:" + clazz.getSimpleName());
        }
    }

    /**
     * 读取一个私有数据
     * @param key        存储数据的键
     * @param clazz      存储数据的类型
     * @param blockClass 对应block的类
     * @param <T>        存储数据的类型
     * @return 存储的私有数据
     */
    <T> T getPrivateData(String key, Class<T> clazz, Class blockClass) {
        if(privateDataMap.get(blockClass) == null){
            privateDataMap.put(blockClass , new HashMap<String, Object>());
        }
        Object data = privateDataMap.get(blockClass).get(key);
        if(data == null){
            return null;
        }
        if (clazz.isInstance(data)) {
            return (T) data;
        } else {
            throw new ClassCastException("data is not class:" + clazz.getSimpleName());
        }
    }

    /**
     * 用于在Activity onSaveInstance的时候调用，备份现有的数据状态
     *
     * @param bundle 存储数据的bundle
     */
    void onSaveInstance(Bundle bundle) {
        Set<String> unfitPublicSet = new HashSet<>();
        for (Map.Entry<String, Object> entry : publicDataMap.entrySet()) {
            if (!(entry.getValue() instanceof Serializable)) {
                unfitPublicSet.add(entry.getKey());
            }
        }
        for (String key : unfitPublicSet) {
            publicDataMap.remove(key);
        }
        Set<Pair<Class, String>> unfitPrivateSet = new HashSet<>();
        for (Map.Entry<Class, Map<String, Object>> entryLevel1 : privateDataMap.entrySet()) {
            for (Map.Entry<String, Object> entryLevel2 : entryLevel1.getValue().entrySet()) {
                if (!(entryLevel2.getValue() instanceof Serializable)) {
                    unfitPrivateSet.add(new Pair<Class, String>(entryLevel1.getKey(), entryLevel2.getKey()));
                }
            }
        }
        for (Pair<Class, String> pair : unfitPrivateSet) {
            privateDataMap.get(pair.first).remove(pair.second);
        }
        bundle.putSerializable(PUBLIC_KEY, (Serializable) publicDataMap);
        bundle.putSerializable(PRIVATE_KEY, (Serializable) privateDataMap);
    }

    /**
     * 在Activity的onCreate调用，恢复备份数据
     * @param bundle 存储数据的bundle
     */
    void onRestoreInstance(Bundle bundle) {
        try {
            Map<String, Object> m1 = (Map<String, Object>) bundle.getSerializable(PUBLIC_KEY);
            Map<Class, Map<String, Object>> m2 = (Map<Class, Map<String, Object>>) bundle.getSerializable(PRIVATE_KEY);
            publicDataMap.clear();
            privateDataMap.clear();
            publicDataMap.putAll(m1);
            privateDataMap.putAll(m2);
        } catch (ClassCastException e) {
            //just catch
        }

    }


}
