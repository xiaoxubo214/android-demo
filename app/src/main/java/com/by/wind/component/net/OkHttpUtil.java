package com.by.wind.component.net;

import android.content.Context;
import android.util.Base64;

import com.by.wind.BaseApplication;
import com.by.wind.Constants;
import com.by.wind.util.DeviceUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wind create 18/2/23.
 */
public class OkHttpUtil {
    private static OkHttpClient.Builder okHttpInstance;

    public static OkHttpClient getInstance(Context context) {
        if (okHttpInstance == null) {
            synchronized (OkHttpUtil.class) {
                if (okHttpInstance == null) {
                    File cacheDir = new File(context.getCacheDir(), Constants.RESPONSE_CACHE);
                    okHttpInstance = new OkHttpClient.Builder();
                    okHttpInstance.cache(new Cache(cacheDir, Constants.RESPONSE_CACHE_SIZE));
                    okHttpInstance.connectTimeout(Constants.HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
                    okHttpInstance.readTimeout(Constants.HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS);
                    LoggingInterceptor logging = new LoggingInterceptor();
                    okHttpInstance.interceptors().add(headerInterceptor);
                    okHttpInstance.interceptors().add(logging);

                }
            }
        }
        return okHttpInstance.build();
    }

    private static Interceptor headerInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            HttpUrl.Builder authorizedUrlBuilder = originalRequest.url().newBuilder()
                    .scheme(originalRequest.url().scheme())
                    .host(originalRequest.url().host())
                    //添加统一参数 如手机唯一标识符,token等
                    .addQueryParameter("device_type", "android")
                    .addQueryParameter("device_ver", DeviceUtil.getIMEI(BaseApplication.getInstance().getApplicationContext()))
                    .addQueryParameter("device_id", DeviceUtil.getIMEI(BaseApplication.getInstance().getApplicationContext()))
                    .addQueryParameter("app_ver", BaseApplication.getInstance().getVersionName());
            Request.Builder requestBuilder = originalRequest.newBuilder()
//                    .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
//                    .addHeader("Accept-Encoding", "gzip, deflate")
//                    .addHeader("Connection", "keep-alive")
//                    .addHeader("Accept", "*/*")
//                    .addHeader("Cookie", "add cookies here")
//                    .addHeader("AppType", "TPOS")
                    .method(originalRequest.method(), originalRequest.body())
                    .url(authorizedUrlBuilder.build());
            Request request;
            if (PreferenceHelper.getUserToken() == null) {
                request = requestBuilder.build();
            } else {
                String basicToken = "Bearer " + Base64.encodeToString(PreferenceHelper.getUserToken().accessToken.getBytes(), Base64.NO_WRAP);
                request = requestBuilder.addHeader("Authorization", basicToken).build();
            }
            return chain.proceed(request);
        }
    };

}
