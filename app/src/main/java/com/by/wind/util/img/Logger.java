package com.by.wind.util.img;

import android.util.Log;

/**
 * Created by DELL on 2018/3/22.
 */

public class Logger {
    private final static boolean DEBUG = true;

    public static void i(String tag,String msg) {
        if(DEBUG)
            Log.i(tag,msg);
    }

    public static void e(String tag,String msg) {
        if(DEBUG)
            Log.e(tag,msg);
    }
}
