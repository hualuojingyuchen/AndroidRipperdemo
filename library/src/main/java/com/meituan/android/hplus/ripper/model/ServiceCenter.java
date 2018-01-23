package com.meituan.android.hplus.ripper.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huzhaoxu on 2016/12/28.
 */

public class ServiceCenter {

    private Map<Class , Object> serviceMap;

    public ServiceCenter(){
        serviceMap = new HashMap<>();
    }

    public <T> void registerService(T service, Class<T> clazz){
        serviceMap.put(clazz , service);
    }

    public <T> void unRegisterService(Class<T> clazz){
        serviceMap.remove(clazz);
    }


    public <T> T getService(Class<T> clazz){
        T service = (T)serviceMap.get(clazz);
        if(service == null){
            throw new IllegalStateException("service has not been registered");
        }
        return service;
    }


}
