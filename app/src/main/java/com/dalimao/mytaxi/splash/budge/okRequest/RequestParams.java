package com.dalimao.mytaxi.splash.budge.okRequest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestParams {
    public ConcurrentHashMap<String,String>urlParams=new ConcurrentHashMap<>();
    public ConcurrentHashMap<String,Object>fileParams=new ConcurrentHashMap<>();

    public RequestParams(){
        this((Map<String,String>) null);
    }

    public RequestParams(Map<String,String>source){
        if(source!=null){
            for(Map.Entry<String,String>entry:source.entrySet()){
                put(entry.getKey(),entry.getValue());
            }
        }
    }

    private void put(String key, String value) {
        if (key!=null&&value!=null){
            urlParams.put(key, value);
        }
    }

    public void put(String key,Object object){
        if (key!=null){
            fileParams.put(key, object);
        }
    }

    public boolean hasParams(){
        if (urlParams.size()>0||fileParams.size()>0){
            return true;
        }
    return false;
    }
}
