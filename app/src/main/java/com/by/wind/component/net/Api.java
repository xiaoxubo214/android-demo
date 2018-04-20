package com.by.wind.component.net;

import com.by.wind.model.UserModel;


import rx.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

import static java.net.Proxy.Type.HTTP;

/**
 * Created by Wind on 2017/11/21.
 */

public interface Api {


    @POST("/login")
    Observable<UserModel> login(@Field("username") String userName,@Field("password") String password);

    @GET
    Observable<String>getRefreshToken(@Field("token") String token);
}
