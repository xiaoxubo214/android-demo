package com.by.wind.demo.net;

import retrofit2.http.GET;

import static java.net.Proxy.Type.HTTP;

/**
 * Created by Wind on 2017/11/21.
 */

public class ApiService {

    @GET("home/banner")
    public void getBanner() {

    }
}
