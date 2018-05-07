package com.by.wind.widget;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.by.wind.BaseApplication;
import com.by.wind.Constants;
import com.by.wind.R;
import com.by.wind.ui.activity.MainActivity;
import com.by.wind.util.DensityUtil;
import com.wind.base.BaseActivity;
import com.by.wind.util.CustomTitleBar;
import com.by.wind.util.SystemBarTintManager;

/**
 * Created by wind ic_on 16/11/15.
 */

public abstract class TitleActivity extends BaseActivity implements CustomTitleBar.TitleOnClickListener {

    public static final String TAG = TitleActivity.class.getSimpleName();
    protected LinearLayout llBody;
    protected LinearLayout llBaseLayout;
    public CustomTitleBar llCustomTitle;
    private int baseLayoutId;
    public SystemBarTintManager mTintManager ;
    private String mClassName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClassName = TitleActivity.this.getClass().getName();
        if (BaseApplication.getInstance() != null) {
            baseLayoutId = R.layout.activity_base_title;
            setContentView(baseLayoutId);
            initHeader();
            setTintBarStyle();
            setContentView();
            bindViews();
            initializeViews();
            initializeData();
        } else {
            protectApp();
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        llBody.removeAllViews();
        llBody.addView(view, params);
    }

    @Override
    public void setContentView(int layoutResID) {
        if (layoutResID == baseLayoutId)
            super.setContentView(layoutResID);
        else {
            getLayoutInflater().inflate(layoutResID, llBody);
        }
    }

    protected void initHeader() {
        llBaseLayout = (LinearLayout) findViewById(R.id.llBaseLayout);
        llCustomTitle = (CustomTitleBar) findViewById(R.id.llCustomTitle);
        llBody = (LinearLayout) findViewById(R.id.llBody);
        llCustomTitle.setOnTitleClickListener(this);
    }

    public void showTitleContent(String title){//
        showTitleContent(title,0,true,R.drawable.home_title_scan_black);
    }

    public void showTitleContent(String title,int drawableID,boolean flag){//
        llCustomTitle.setTvCenterLeftButton(title);
        llCustomTitle.setIvLeftButton(drawableID);
        llCustomTitle.setIvLeftButtonVisible(flag);
        llCustomTitle.set_show_left_button(!flag);
    }

    public void showTitleContent(String title, int leftDrawableID, boolean flag , int rightDrawableID){
        llCustomTitle.setTvCenterLeftButton(title);
        llCustomTitle.setVisibility(View.VISIBLE);
        llCustomTitle.setIvLeftButtonVisible(!TextUtils.isEmpty(title) ? true : false);
        llCustomTitle.setIvLeftButton(leftDrawableID != 0 ? leftDrawableID : R.drawable.icon_return_back);
        llCustomTitle.setIvRightButtonVisible(flag);
        llCustomTitle.setIvRightButton(rightDrawableID);
    }

    private void setTintBarStyle(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ){
            setBarStyle();
            setStatusBar();
        }
    }
    protected void setStatusBar(){
        int statusBarHeight = DensityUtil.getStatusBarHeight(this);
        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams)llCustomTitle.getLayoutParams();
        marginParams.topMargin = statusBarHeight;
        llCustomTitle.setLayoutParams(marginParams);
    }

    protected void setBarStyle() {
        // 设置状态栏透明
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    public void setStatusBarColor(int color){
        if(mTintManager == null){
            mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);
        }
        mTintManager.setStatusBarTintResource(color);
    }

    protected abstract void setContentView();
    protected abstract void initializeViews();
    protected abstract void initializeData();
    protected abstract void bindViews();


    @Override
    public void onLeftClick() {
        onBackPressed();
    }

    @Override
    public void onRightClick() {
    }

    @Override
    public void onCenterClick() {

    }

    protected void protectApp() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constants.KEY_PROTECT_APP, true);
        startActivity(intent);
        finish();
    }

}
