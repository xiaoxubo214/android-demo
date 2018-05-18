package com.by.wind.component.net;

import com.by.wind.Constants;
import com.by.wind.entity.UserInfo;
import com.by.wind.entity.UserToken;


import java.util.Map;

import retrofit2.http.Body;
import rx.Observable;

import retrofit2.http.POST;


/**
 * Created by Wind on 2017/11/21.
 */

public interface Api {

    @POST(Constants.URL_API)
    Observable<RetrofitResult<UserToken>> api(@Body Map<String,Object> params);

    @POST(Constants.URL_API)
    Observable<RetrofitResult<UserInfo>> getUserInfo(@Body Map<String,Object> params);
}
