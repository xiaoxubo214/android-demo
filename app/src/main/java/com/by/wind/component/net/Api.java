package com.by.wind.component.net;

import com.by.wind.Constants;
import com.by.wind.entity.UserInfo;
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
    Observable<RetrofitResult<UserToken>> login(@Field("request_type") String requestType,@Field("phone_h") String username,@Field("password") String password);

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
    Observable<RetrofitResult<UserToken>> doForget(@Field("request_type") String requestType,@Field("phone_h") String username,@Field("password") String password);


    @FormUrlEncoded
    @POST(Constants.URL_API)
    Observable<RetrofitResult<UserToken>> doRegister(@Field("request_type") String requestType,@Field("phone_h") String username,@Field("password") String password);

    @FormUrlEncoded
    @POST(Constants.URL_API)
    Observable<RetrofitResult<String>> getCode(@Field("request_type") String requestType, @Field("phone_h") String phone);

    @FormUrlEncoded
    @POST(Constants.URL_API)
    Observable<RetrofitResult<UserInfo>> getUserInfo(@Field("request_type") String requestType, @Field("phone_h") String phone);
}
