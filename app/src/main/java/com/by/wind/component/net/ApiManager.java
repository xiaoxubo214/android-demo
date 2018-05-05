package com.by.wind.component.net;

import com.by.wind.BaseApplication;

public class ApiManager {

    private static ApiManager apiManager;
    private Api api;

    public static ApiManager getInstance() {
        if (apiManager == null) {
            apiManager = new ApiManager();
        }
        return apiManager;
    }

    public Api getApiService() {
        if (api == null) {
            api = RetrofitUtil.createApi(BaseApplication.getInstance(),Api.class);
        }
        return api;
    }
}
