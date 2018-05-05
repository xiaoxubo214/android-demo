package com.by.wind;

import android.app.Application;

import com.by.wind.component.net.Api;
import com.by.wind.component.net.RetrofitUtil;
import com.by.wind.util.common.AppException;
import com.orhanobut.hawk.Hawk;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Wind on 2017/11/15.
 */

public class BaseApplication extends Application {
    private static BaseApplication app;
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
        Hawk.init(this);
        LeakCanary.install(this);
        registerUncaughtExceptionHandler();

    }

    // 注册App异常崩溃处理器
    private void registerUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler());
    }
}
