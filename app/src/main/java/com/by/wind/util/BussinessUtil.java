package com.by.wind.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;

/**
 * Created by wind ic_on 16/11/7.
 */

public class BussinessUtil {

    public static boolean isValidate(ArrayList<?> list) {
        if(list != null && list.size() > 0){
            return true;
        }
        return false;
    }

    /**
     * 检测网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }

        return false;
    }

    /**
     * 隐藏键盘
     */
    public static void hideKeyBoard(Activity context) {
        try {
            if (context.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
                if (context.getCurrentFocus() != null)
                    ((InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            if (context.getCurrentFocus() != null) {
                ((InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
