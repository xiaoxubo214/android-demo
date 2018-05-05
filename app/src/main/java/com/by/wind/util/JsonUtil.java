package com.by.wind.util;

import android.text.Html;
import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by christy ic_on 17/2/16.
 */

public class JsonUtil {
    /**
     * 将对象转为json
     */
    public static String toJson(Object o) {
        Gson gson = new Gson();
        String str = "";
        if (o != null) {
            str = gson.toJson(o);
        }
        return str;
    }

    /**
     * 将对象转为json string类型
     */
    public static String toJsonString(Object o) {
        Gson gson = new Gson();
        String str = "";
        if (o != null) {
            str = gson.toJson(o);
        }
        return str;
    }

    /**
     * 将对象转为json
     */
    public static Object toObject(String jsonStr, Type type) {
        Gson gson = new Gson();
        Object o = null;
        try {
            if (jsonStr != null && !"".equals(jsonStr)) {
                o = gson.fromJson(jsonStr, type);
            }
        } catch (Exception e) {
        }
        return o;
    }

    public static String getString(JSONObject root, String key) {
        try {
            if (root.isNull(key)) {
                return "";
            } else {
                return Html.fromHtml(root.getString(key)).toString();
            }
        } catch (Exception e) {
            return "";
        }
    }

    public static <T> T rechangeObject(JSONObject jsonObject, Type type) {
        Gson gson = new Gson();
        T o = null;
        try {
            if (jsonObject != null) {
                String json = jsonObject.toString();
                if (!TextUtils.isEmpty(json)) {
                    o = gson.fromJson(json, type);
                }
            }
        } catch (Exception e) {
        }

        return o;
    }

    public static <T> T toObjectT(String jsonStr, Type type) {
        Gson gson = new Gson();
        T o = null;
        try {
            if (jsonStr != null && !"".equals(jsonStr)) {
                o = gson.fromJson(jsonStr, type);
            }
        } catch (Exception e) {
        }
        return o;
    }
}
