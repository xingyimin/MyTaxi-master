package com.dalimao.mytaxi.splash.budge.okCallBack;

import android.os.Looper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Handler;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Response;

public class CommonJsonCallback implements Callback{
    protected final String RESULT_CODE="ecode";// 有返回则对于http请求来说是成功的，但还有可能是业务逻辑上的错误
    protected final int RESULT_CODE_VALUE=0;
    protected final String ERROR_MSG="emsg";
    protected final String EMPTY_MSG="e";
    protected final String COOKIE_STORE="Set-Cookie";

    protected final int NETWORK_ERROR=-1;
    protected final int JSON_ERROR=-2;
    protected final int OTHER_ERROR=-3;


    //将其他线程中的数据转发到UI线程
    private android.os.Handler mDeliveryHander;
    private DisposeDataListener mListener;
    private Class<?>mClass;

    public CommonJsonCallback(DisposeDataHandle handle){
        this.mListener=handle.listener;
        this.mClass=handle.aClass;
        this.mDeliveryHander=new android.os.Handler(Looper.getMainLooper());
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        //此时还处于非UI线程，需要post转发
        mDeliveryHander.post(new Runnable() {
            @Override
            public void run() {
                mListener.onFailure(new OKHttpException(NETWORK_ERROR,e));
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final String result=response.body().string();
        final ArrayList<String>cookieLists=handleCookie(response.headers());
        mDeliveryHander.post(new Runnable() {
            @Override
            public void run() {
                handleResponse(result);
                if(mListener instanceof DisposeHandleCookieListener){
                    ((DisposeHandleCookieListener) mListener).onCookie(cookieLists);
                }
            }
        });
    }

    private void handleResponse(Object result) {
       if (result == null|| result.toString().trim().equals("")){
           mListener.onFailure(new OKHttpException(NETWORK_ERROR,EMPTY_MSG));
            return;
       }
       try {
           JSONObject jsonObject=new JSONObject(result.toString());
           if (mClass==null){
               mListener.onSuccess(result);
           }else {
            Object obj= Object obj = ResponseEntityToModule.parseJsonObjectToModule(result, mClass);
            if (obj != null) {
                mListener.onSuccess(obj);
            } else {
                //json不合法
                mListener.onFailure(new OkHttpException(
                        JSON_ERROR, EMPTY_MSG
                ));
            }
            }
       }catch (JSONException e) {
           mListener.onFailure(new OKHttpException(OTHER_ERROR,e.getMessage()));
           e.printStackTrace();
       }
    }

    private ArrayList<String> handleCookie(Headers headers) {
        ArrayList<String>tempList=new ArrayList<String>();
        for(int i=0;i<headers.size();i++){
            if(headers.name(i).equalsIgnoreCase(COOKIE_STORE)){
                tempList.add(headers.value(i));
            }
        }
        return tempList;
    }


}
