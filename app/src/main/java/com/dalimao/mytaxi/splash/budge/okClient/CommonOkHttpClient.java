package com.dalimao.mytaxi.splash.budge.okClient;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CommonOkHttpClient {
    private static final int TIME_OUT=30;
    private static OkHttpClient mClient;
    static {
        OkHttpClient.Builder okHttpClientBuilder=new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request=chain.request()
                        .newBuilder()
                        .addHeader("user-Agent","Imooc-mobile")
                        .build();
                return chain.proceed(request);
            }
        });
        //okHttpClientBuilder.cookieJar()
        //设置超时时间
         okHttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
         okHttpClientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
         okHttpClientBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
         //支持重定向
         okHttpClientBuilder.followRedirects(true);
         //https支持
         okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
             @Override public boolean verify(String hostname, SSLSession session) {
                 return true;
             }
         }); /** * trust all the https point */
         okHttpClientBuilder.sslSocketFactory(HttpsUtils.initSSLSocketFactory(), HttpsUtils.initTrustManager()); mOkHttpClient = okHttpClientBuilder.build(); }
         public static OkHttpClient getOkHttpClient() { return mClient; }
         /** * 通过构造好的Request,Callback去发送请求 * * @param request * @param callback */
         public static Call get(Request request, DisposeDataHandle handle) {
             Call call = mClient.newCall(request);
             call.enqueue(new CommonJsonCallback(handle));
             //CommonJsonCallback Callback回调 return call;
         }
          public static Call post(Request request, DisposeDataHandle handle) {
                 Call call = mClient.newCall(request);
                 call.enqueue(new CommonJsonCallback(handle));
                 return call;
         }
         public static Call downloadFile(Request request, DisposeDataHandle handle) {
             Call call = mClient.newCall(request);
             call.enqueue(new CommonFileCallback(handle));
             return call;
         }



    }
}
