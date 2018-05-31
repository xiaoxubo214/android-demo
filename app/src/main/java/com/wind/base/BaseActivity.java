package com.wind.base;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.umeng.analytics.MobclickAgent;
import com.wind.base.event.ActivityLifeCycleEvent;

import java.lang.reflect.Method;

import rx.subjects.PublishSubject;

/**
 * Created by wind create 18/5/5.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public final static String TAG = "BaseActivity";

    public final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject = PublishSubject.create();


    private BroadcastReceiver mBroadcastReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.CREATE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
       /* Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE;
        window.setAttributes(params);*/
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.RESUME);
        MobclickAgent.onResume(this);
        super.onResume();

    }

    @Override
    protected void onPause() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.PAUSE);
        MobclickAgent.onPause(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.STOP);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lifecycleSubject.onNext(ActivityLifeCycleEvent.DESTROY);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected boolean requestPermission(String permission, int requestCode) {
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
//            boolean rationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
//            if (!rationale) {
//                ToastUtil.show("权限被拒绝");
//            }
            requestPermissions(new String[]{permission}, requestCode);
        }
        return false;
    }

    // 显示键盘
    public void showKeyBoard(View view) {
        try {
            if (!isFinishing()) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm == null) {
                    return;
                }

                if (view != null) {
                    view.requestFocus();
                }

                Method method = imm.getClass().getMethod("showSoftInputUnchecked", int.class, ResultReceiver.class);
                method.setAccessible(true);
                method.invoke(imm, 0, null);
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * 隐藏键盘
     */
    public void hideKeyBoard() {
        try {
            if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
                if (getCurrentFocus() != null)
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            if (getCurrentFocus() != null) {
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public void setFullScreen(final View view) {
        //系统版本大于4.4才能设置沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //让状态栏透明
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (view != null) {
                getWindow().getDecorView().post(new Runnable() {
                    @Override
                    public void run() {
                        Rect frame = new Rect();
                        getWindow().getDecorView()
                                .getWindowVisibleDisplayFrame(frame);
                        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                        marginParams.topMargin = frame.top;
                        view.setLayoutParams(marginParams);
                    }
                });
            }
        }
    }
}
