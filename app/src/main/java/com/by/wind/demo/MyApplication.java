package com.by.wind.demo;

import android.app.Application;

//import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Wind on 2017/11/15.
 */

public class MyApplication extends Application {
    private static MyApplication app;

    public MyApplication() {
        app = this;
    }

    public static synchronized MyApplication getInstance() {
        if (app == null) {
            app = new MyApplication();
        }
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //RealmConfig.getInstance().initDataConfig(this);
        //LeakCanary.install(this);
        //registerUncaughtExceptionHandler();
    }

    // 注册App异常崩溃处理器
    /*private void registerUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler());
    }*/
}
