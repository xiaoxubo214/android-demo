package com.by.wind;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.by.wind.component.lib_zxing.DisplayUtil;
import com.by.wind.util.common.AppException;
import com.orhanobut.hawk.Hawk;
//import com.squareup.leakcanary.LeakCanary;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.by.wind.component.lib_zxing.activity.ZXingLibrary;

import org.android.agoo.huawei.HuaWeiRegister;
import org.android.agoo.mezu.MeizuRegister;
import org.android.agoo.xiaomi.MiPushRegistar;

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
    PushAgent mPushAgent;

    public static synchronized BaseApplication getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Hawk.init(this);
        //LeakCanary.install(this);
        ZXingLibrary.initDisplayOpinion(this);
        //registerUncaughtExceptionHandler();
        UMConfigure.init(this, Constants.UM_APP_KEY, Constants.UM_CHANNEL, UMConfigure.DEVICE_TYPE_PHONE, Constants.UM_MESSAGE_SECRET);
        mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.e("BaseApplication","success");
            }

            @Override
            public void onFailure(String s, String s1) {
                //注册失败
                Log.e("BaseApplication","error");
            }
        });
        MiPushRegistar.register( this, Constants.MI_PUSH_APP_ID, Constants.MI_PUSH_APP_KEY);
        HuaWeiRegister.register(this);
        MeizuRegister.register(this, Constants.MEIZU_PUSH_APP_ID, Constants.MEIZU_PUSH_APP_KEY);
        initDisplayOpinion();
    }

    private void initDisplayOpinion() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        DisplayUtil.density = dm.density;
        DisplayUtil.densityDPI = dm.densityDpi;
        DisplayUtil.screenWidthPx = dm.widthPixels;
        DisplayUtil.screenhightPx = dm.heightPixels;
        DisplayUtil.screenWidthDip = DisplayUtil.px2dip(getApplicationContext(), dm.widthPixels);
        DisplayUtil.screenHightDip = DisplayUtil.px2dip(getApplicationContext(), dm.heightPixels);
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
    public String getVersionCode() {
        if (mVersionCode > 0) {
            return mVersionCode + "";
        } else {
            try {
                PackageManager packageManager = getPackageManager();
                PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
                mVersionCode = packInfo.versionCode;
                return mVersionCode + "";
            } catch (PackageManager.NameNotFoundException ignored) {
            }
            return 15 + "";
        }
    }

}
