package com.by.wind.component.net;

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
        return null;
    }
}
