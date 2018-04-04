package com.by.wind.component.net;

import com.by.wind.model.UserModel;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

import static java.net.Proxy.Type.HTTP;

/**
 * Created by Wind on 2017/11/21.
 */

public interface ApiService {


    @POST("/login")
    Observable<UserModel> login();
}
