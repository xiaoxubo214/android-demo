package com.by.wind.component.net;

import com.by.wind.Constants;
import com.by.wind.entity.UserToken;


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
    @POST(Constants.URL_API)
    Observable<RetrofitResult<UserToken>> login(@Field("username") String username,@Field("password") String password);

    /**
     * 刷新token
     *
     * @param accessToken
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.URL_API)
    Observable<RetrofitResult<UserToken>> getRefreshToken(@Path("accessToken") String accessToken, @Field("refreshToken") String refreshToken, @Field("_method") String patch);

    @FormUrlEncoded
    @POST(Constants.URL_API)
    Observable<RetrofitResult<String>> forget();

    @FormUrlEncoded
    @POST(Constants.URL_API)
    Observable<RetrofitResult<String>> getCode(@Field("request_type") String requesType, @Field("phone_h") String phone);
}
