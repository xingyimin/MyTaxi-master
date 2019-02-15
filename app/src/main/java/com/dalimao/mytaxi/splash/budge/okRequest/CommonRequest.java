package com.dalimao.mytaxi.splash.budge.okRequest;
import java.io.File;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;


import okhttp3.Request;

public class CommonRequest {
    public static Request createPostRequest(String url,RequestParams params){

    }

    public static Request createPostRequest(String url,RequestParams params,RequestParams headers){
        //添加请求体
        FormBody.Builder mFromBodyBuild=new FormBody.Builder();
        if(params!=null){
            for(Map.Entry<String,String>entry:params.urlParams.entrySet()){
                mFromBodyBuild.add(entry.getKey(),entry.getValue());
            }
        }
        //添加请求头
        Headers.Builder mHeadersBuild=new Headers.Builder();
        if(headers!=null){
            for(Map.Entry<String,String>entry:headers.urlParams.entrySet()){
                mHeadersBuild.add(entry.getKey(),entry.getValue());
            }
        }
        FormBody mFormBody=mFromBodyBuild.build();
        Headers mHeaders=mHeadersBuild.build();

        Request request=new Request.Builder().url(url)
                .post(mFormBody)
                .headers(mHeaders)
                .build();

        return request;

    }

    public static Request createGetRequest(String url,RequestParams params){
        return  createGetRequest(url, params,null);
    }

    public static Request createGetRequest(String url,RequestParams params,RequestParams headers){
        StringBuilder urlBuilder=new StringBuilder(url).append("?");
        if (params!=null){
            for (Map.Entry<String,String>entry:params.urlParams.entrySet()){
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");

            }
        }
        Headers.Builder mHeadersBuild=new Headers.Builder();
        if (headers!=null){
            for (Map.Entry<String,String>entry:headers.urlParams.entrySet()){
                mHeadersBuild.add(entry.getKey(),entry.getValue());

            }
        }
        Headers mHeaders=mHeadersBuild.build();
        return new Request.Builder()
                .url(urlBuilder.substring(0, urlBuilder.length() - 1))
                .get()
                .headers(mHeaders)
                .build();
    }

    public static Request createMonitorRequest(String url,RequestParams params){
        StringBuilder urlBuilder=new StringBuilder(url).append("&");
        if (params!=null&&params.hasParams()){
            for (Map.Entry<String,String>entry:params.urlParams.entrySet()){
                urlBuilder.append(entry.getKey()).append("=").append("=").append(entry.getValue()).append("&");
            }
        }
        return new Request.Builder()
                .url(urlBuilder.substring(0,urlBuilder.length()-1))
                .get()
                .build();
    }

    private static final MediaType FILE_TYPE=MediaType.parse("application/octet-stream");
    public static Request createMuiltPostRequest(String url,RequestParams params){
        MultipartBody.Builder requestBody=new MultipartBody.Builder();
        requestBody.setType(MultipartBody.FORM);
        if(params!=null){
            for(Map.Entry<String,Object>entry:params.fileParams.entrySet()){
                if(entry.getValue()instanceof File){
                    requestBody.addPart(Headers.of("Content-Disposition","form-data;name=\""
                            +entry.getKey()+"\""),
                            RequestBody.create(FILE_TYPE,(File) entry.getValue()));
                }else if(entry.getValue()instanceof String){
                    requestBody.addPart(Headers.of("Content-Disposition","form-data;name=\""
                                    +entry.getKey()+"\""),
                            RequestBody.create(null,(String)entry.getValue()));

                }
            }
        }
        return new Request.Builder()
                .url(url)
                .post(requestBody.build())
                .build();
    }
}
