package com.by.wind.model;

import android.os.Handler;
import android.os.Looper;

import com.by.wind.component.net.CallBack;
import com.by.wind.component.net.CallBack;


/**
 * Created by DELL on 2018/3/27.
 */

public class UserModel {

    private Handler handler = new Handler(Looper.getMainLooper());

    public void login(final String username, final String password, final CallBack callBack) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (username.equals("123") && password.equals("124")) {
                    callBack.onSuccess();
                } else {
                    callBack.onFail("account or password error!");
                }
            }
        },2000);
    }
}
