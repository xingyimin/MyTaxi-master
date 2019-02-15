package com.dalimao.mytaxi.splash.budge.okCallBack;

/*
* DisposeDataListener再一层封装DisposeDataHandle
* */

public class DisposeDataHandle {
    public DisposeDataListener listener=null;
    public Class<?>aClass=null;
    public String mSource=null;

    public DisposeDataHandle(DisposeDataListener listener){
        this.listener=listener;
    }

    public DisposeDataHandle(DisposeDataListener listener,Class<?>aClass){
        this.listener=listener;
        this.aClass=aClass;
    }

    public DisposeDataHandle(DisposeDataListener listener,String mSource){
        this.listener=listener;
        this.mSource=mSource;
    }
}
