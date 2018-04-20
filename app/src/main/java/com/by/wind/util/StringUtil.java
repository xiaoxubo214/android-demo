package com.by.wind.util;

import android.text.TextUtils;

import com.by.wind.BaseApplication;
import com.by.wind.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yangrenije on 2017/6/21.
 */

public class StringUtil {

    /**
     * 提取字符串中第一个数字串
     *
     * @param str
     * @return
     */
    public static String matchNumb(String str) {
        Pattern p = Pattern.compile("\\d*");
        Matcher m = p.matcher(str);
        if (m.find()) return m.group();
        else return null;
    }

    public static String courseName(String name, int sequeence) {
        return BaseApplication.getInstance().getResources().getString(R.string.course_name, name, sequeence);
    }

    /**
     * @param value
     * @return
     */
    public static boolean isEmpty(String value) {
        return TextUtils.isEmpty(value) || "null".equals(value);
    }

    /**
     * @param resId
     * @return
     */
    public static String getRes(int resId) {
        if (resId <= 0) return null;
        return BaseApplication.getInstance().getResources().getString(resId);
    }

    public static String byte2HexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (int i = 0, len = bytes.length; i < len; i++) {
            String hex = Integer.toHexString(bytes[i]);
            if (hex.length() == 1) {
                hex = "0" + hex;
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
