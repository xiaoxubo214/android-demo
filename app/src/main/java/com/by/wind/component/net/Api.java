package com.by.wind.component.net;

import com.by.wind.model.UserToken;


import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Path;
import rx.Observable;
import retrofit2.http.Field;

import retrofit2.http.POST;


/**
 * Created by Wind on 2017/11/21.
 */

public interface Api {

    @FormUrlEncoded
    @POST("/login")
    Observable<RetrofitResult<String>> login(@Field("username") String username,@Field("password") String password);

    /**
     * 刷新token
     *
     * @param accessToken
     * @return
     */
    @POST("/token")
    Observable<RetrofitResult<UserToken>> getRefreshToken(@Path("accessToken") String accessToken, @Field("refreshToken") String refreshToken, @Field("_method") String patch);
}
