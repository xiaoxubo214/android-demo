package com.by.wind.component.net;

import android.content.Context;
import android.util.Log;

import com.by.wind.BaseApplication;
import com.by.wind.Constants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Wind on 2017/12/8.
 */

public class RetrofitUtil {

    private static Retrofit retrofit;

    public RetrofitUtil(){

    }

    public static <T> T createApi(Context context, Class<T> clazz){
        Log.e("Retrofit","create");
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.HOST)
                    .client(OkHttpUtil.getInstance(BaseApplication.getInstance()))
                    .addConverterFactory(GsonConverterFactory.create())//Json转换器
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit.create(clazz);
    }
}
