package com.by.wind.component.net;

import android.content.Context;

import okhttp3.OkHttpClient;

/**
 * Created by Wind on 2017/12/8.
 */

public class OkHttpUtil {
    public static OkHttpClient.Builder okHttpInstance;

    public static OkHttpClient getInstance(Context context) {
        if(okHttpInstance == null) {
         /*   File cacheDir = new File(context.getCacheDir(), Constant.RESPONSE_CACHE);
            okHttpInstance = new OkHttpClient.Builder();
            okHttpInstance.cache(new Cache(cacheDir,Constant.RESPONSE_CACHE_SIZE));
            okHttpInstance.connectTimeout(Constant.CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);
            LogInterceptor*/
        }
        return  null;
    }
}
