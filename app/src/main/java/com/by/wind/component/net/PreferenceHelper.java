package com.by.wind.component.net;

import android.content.Context;
import android.content.SharedPreferences;

import com.by.wind.BaseApplication;
import com.by.wind.model.UserToken;
import com.by.wind.util.JsonUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by wind on 17/4/26.
 */

public class PreferenceHelper {

    private static final String LOGININFO_PREFERENCES = "login_info_preferences";

    private static final String LAST_TEACHER_TOKEN = "last_teacher_token";
    private static final String LAST_IS_LOGIN = "last_is_login";
    private static final String LAST_LOGIN_TYPE = "last_login_type";

    private static final String CACHE_DATA_PRE = "cache_data_pre";

    /**
     * 清空用户信息
     */
    public static void clearLoginInfo() {
        getEditor(LOGININFO_PREFERENCES).clear().commit();
        clearCacheDataPre();
    }

    /**
     * 清空 CACHE_DATA_PRE
     */
    public static void clearCacheDataPre() {
        getEditor(CACHE_DATA_PRE).clear().commit();
    }

    /**
     * 获取 SharedPreferences
     *
     * @param preName
     * @return
     */
    private static SharedPreferences getSharedPreferences(String preName) {
        SharedPreferences preferences = BaseApplication.getInstance().getSharedPreferences(preName, Context.MODE_PRIVATE);
        return preferences;
    }

    /**
     * 获取sharepreference 编辑
     *
     * @param preName
     * @return
     */
    private static SharedPreferences.Editor getEditor(String preName) {
        return getSharedPreferences(preName).edit();
    }

    /**
     * 退出登录状态
     */
    public void quitLoginStates() {
        PreferenceHelper.clearLoginInfo();
    }



    /**
     * 保存用户Token
     *
     * @param userToken
     */
    public static void saveUserToken(UserToken userToken) {
        SharedPreferences.Editor editor = getEditor(LOGININFO_PREFERENCES);
        if (userToken != null) {
            editor.putString(LAST_TEACHER_TOKEN, JsonUtil.toJson(userToken));
        } else {
            editor.putString(LAST_TEACHER_TOKEN, "");
        }
        editor.commit();
    }


    /**
     * 是否登录
     */
    public static Boolean isLogin() {
        SharedPreferences preferences = getSharedPreferences(LOGININFO_PREFERENCES);
        Boolean isLogin = preferences.getBoolean(LAST_IS_LOGIN, false);
        return isLogin;
    }

    /**
     * 设置登陆状态
     *
     * @param isLogin
     */
    public static void setIsLogin(Boolean isLogin) {
        SharedPreferences.Editor editor = getEditor(LOGININFO_PREFERENCES);
        editor.putBoolean(LAST_IS_LOGIN, isLogin);
        editor.commit();
    }

    /**
     * 获取登录type
     *
     * @return
     */
    public static String getLoginType() {
        SharedPreferences preferences = getSharedPreferences(LOGININFO_PREFERENCES);
        String loginType = preferences.getString(LAST_LOGIN_TYPE, "");
        return loginType;
    }


    /**
     ＊ 获取用户Token
     */
    public static UserToken getUserToken() {
        UserToken userToken = null;
        try {
            SharedPreferences preferences = getSharedPreferences(LOGININFO_PREFERENCES);
            String userTokenJson = preferences.getString(LAST_TEACHER_TOKEN, "");
            if (userTokenJson != null && !"".equals(userTokenJson)) {
                Type memberInfoType = new TypeToken<UserToken>() {}.getType();
                userToken = (UserToken) JsonUtil.toObject(userTokenJson, memberInfoType);
            }
        } catch (Exception e) {
        }
        return userToken;
    }

}
