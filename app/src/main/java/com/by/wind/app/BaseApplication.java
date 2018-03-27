package com.by.wind.app;

import android.app.Application;

import com.by.wind.util.common.AppException;
import com.by.wind.component.net.ApiService;
import com.by.wind.component.net.RetrofitUtil;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Wind on 2017/11/15.
 */

public class BaseApplication extends Application {
    private static BaseApplication app;
    public ApiService apiService;
    public BaseApplication() {
        app = this;
    }

    public static synchronized BaseApplication getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        //RealmConfig.getInstance().initDataConfig(this);
        LeakCanary.install(this);
        registerUncaughtExceptionHandler();

        apiService = RetrofitUtil.createApi(this,ApiService.class);
    }

    // 注册App异常崩溃处理器
    private void registerUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler());
    }
}
