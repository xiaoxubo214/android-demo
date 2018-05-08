package com.by.wind;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.by.wind.util.common.AppException;
import com.orhanobut.hawk.Hawk;
import com.squareup.leakcanary.LeakCanary;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * Created by Wind on 2017/11/15.
 */

public class BaseApplication extends Application {
    private static BaseApplication app;
    private String mVersionName;
    private int mVersionCode;
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
        ZXingLibrary.initDisplayOpinion(this);
        registerUncaughtExceptionHandler();
    }

    // 注册App异常崩溃处理器
    private void registerUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler());
    }

    // 获取软件版本号
    public String getVersionName() {
        try {
            PackageManager packageManager = getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            mVersionName = packInfo.versionName;
            return "v" + mVersionName;
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return "V1.0";
    }

    // 获取软件版本号
    public int getVersionCode() {
        if (mVersionCode > 0) {
            return mVersionCode;
        } else {
            try {
                PackageManager packageManager = getPackageManager();
                PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
                mVersionCode = packInfo.versionCode;
                return mVersionCode;
            } catch (PackageManager.NameNotFoundException ignored) {
            }
            return 15;
        }
    }

}
