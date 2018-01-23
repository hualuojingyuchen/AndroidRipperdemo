package com.meituan.android.hplus.ripper.model;

import com.meituan.android.hplus.ripper.block.IAvoidStateLoss;

/**
 * Created by huzhaoxu on 2016/12/29.
 */

public abstract class AbstractModel<T> {

    ModelContainer container;
    String key;
    protected IAvoidStateLoss stateLoss;

    public AbstractModel(String key, IAvoidStateLoss sl){
        this.key = key;
        stateLoss = sl;
    }

    public final void requestData(){
        if(container.lockSet(key)){
            onRequestData();
        }
    }

    protected final void notifyData(T data){
        container.onAsyncData(key, data);
        container.unLockSet(key);

    }

    protected abstract void onRequestData();


}
