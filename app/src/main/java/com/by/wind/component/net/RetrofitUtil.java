package com.by.wind.component.net;

import android.content.Context;

import com.by.wind.Constants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Wind on 2017/12/8.
 */

public class RetrofitUtil {

    private static Retrofit retrofit;

    public RetrofitUtil(){

    }

    public static <T> T createApi(Context context, Class<T> clazz){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit.create(clazz);
    }
}
