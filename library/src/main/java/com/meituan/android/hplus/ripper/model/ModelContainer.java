package com.meituan.android.hplus.ripper.model;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by huzhaoxu on 2017/1/3.
 */

class ModelContainer {

    private Map<String, AbstractModel> modelMap;

    protected Set<String> asyncRequestLockSet;

    WhiteBoard whiteBoard;

    ModelContainer(WhiteBoard board){
        modelMap = new HashMap<>();
        whiteBoard = board;
    }

    void addModel(AbstractModel model){
        model.container = this;
        modelMap.put(model.key , model);
    }

    void requestData(String key){
        modelMap.get(key).requestData();
    }
    synchronized boolean lockSet(String key) {
        if (asyncRequestLockSet == null) {
            asyncRequestLockSet = new LinkedHashSet<>();
        }
        if (asyncRequestLockSet.contains(key)) {
            return false;
        } else {
            asyncRequestLockSet.add(key);
            return true;
        }
    }

    synchronized boolean unLockSet(String key) {
        asyncRequestLockSet.remove(key);
        return true;
    }

    <T> void onAsyncData(String key, T data){
        whiteBoard.notify(key, data);
    }

}
