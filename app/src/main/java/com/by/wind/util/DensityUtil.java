package com.by.wind.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;

import java.lang.reflect.Field;

/**
 * Created by wind on 17/12/22.
 */

public class DensityUtil {
    public static int dip2px(Context paramContext, float paramFloat) {
        float f = paramContext.getResources().getDisplayMetrics().density;
        return (int) (paramFloat * f + 0.5F);
    }

    public static int px2dip(Context paramContext, float paramFloat) {
        float f = paramContext.getResources().getDisplayMetrics().density;
        return (int) (paramFloat / f + 0.5F);
    }

    public static int sp2px(Context paramContext, float paramFloat) {
        float f = paramContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (paramFloat * f + 0.5F);
    }

    public static int px2sp(Context paramContext, float paramFloat) {
        float f = paramContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (paramFloat / f + 0.5F);
    }

    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
        }
        return statusBarHeight;
    }

    public static int dpToPx(Resources res, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
    }

    /**
     * 得到设备屏幕的宽度
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 如果有底部导航栏 获取底部导航栏高度
     *
     * @param context
     * @return
     */
    public static int getBottomNavigatorHeight(Context context) {
        int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (0 != rid) {
            int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    /**
     * 判断底部导航栏是否显示
     *
     * @param act
     * @return
     */
    public static boolean isNavigationBarShow(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = act.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y != size.y;
        } else {
            boolean menu = ViewConfiguration.get(act).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if (menu || back) {
                return false;
            } else {
                return true;
            }
        }
    }
}
