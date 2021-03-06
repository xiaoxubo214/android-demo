package com.by.wind.util;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.by.wind.R;
import com.by.wind.BaseApplication;

/**
 * Created by wind on 17/12/24.
 */

public class ToastUtil {
    public static void showToast(String msg) {
        toastRoot(msg);
    }

    public static void showToast(int resId) {
        toastRoot(resId);
    }

    private static void toastRoot(String s) {

        View view = View.inflate(BaseApplication.getInstance(), R.layout.custom_toast_layout, null);
        TextView textView = (TextView) view.findViewById(R.id.tvToast);
        if (!TextUtils.isEmpty(s)) {
            textView.setText(s);
            Toast toast = new Toast(BaseApplication.getInstance());
            toast.setDuration(Toast.LENGTH_LONG);
            // toast.setGravity(Gravity.BOTTOM, 0,200);
            toast.setMargin(0, 0.05f);
            toast.setView(view);
            toast.show();
        }
    }

    public static void show(String s) {
        if (View.inflate(BaseApplication.getInstance(), R.layout.custom_toast_layout, null) == null) {
            Log.e("Toast", "is null");
        } else {
            Log.e("Toast", "not null");
        }

        Log.e("Toast","start");
        View view = View.inflate(BaseApplication.getInstance(), R.layout.custom_toast_layout, null);
        TextView textView = (TextView) view.findViewById(R.id.tvToast);
        if (!TextUtils.isEmpty(s)) {
            textView.setText(s);
            Toast toast = new Toast(BaseApplication.getInstance());
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setMargin(0, 0.05f);
            toast.setView(view);
            toast.show();
        }
    }

    private static void toastRoot(int s) {

        View view = View.inflate(BaseApplication.getInstance(), R.layout.custom_toast_layout, null);
        TextView textView = (TextView) view.findViewById(R.id.tvToast);
        textView.setText(s);

        Toast toast = new Toast(BaseApplication.getInstance());
        toast.setDuration(Toast.LENGTH_LONG);
        // toast.setGravity(Gravity.BOTTOM, 0,200);
        toast.setMargin(0, 0.05f);
        toast.setView(view);
        toast.show();

    }
}
