package com.by.wind.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;


import com.by.wind.BaseApplication;
import com.by.wind.Constants;
import com.by.wind.R;
import com.by.wind.component.event.MessageEvent;
import com.by.wind.entity.RequestInfo;
import com.by.wind.util.DeviceUtil;
import com.by.wind.util.JsonUtil;
import com.by.wind.util.PreferenceHelper;
import com.wind.base.BaseActivity;
import com.wind.base.loading.LoadingDialog;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by christy on 17/4/27.
 */

public class SplashActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView mWebView;

    private final static int FINISHED = 100;
    private final static int COUNT_DOWN_TIME = 2500;
    LoadingDialog mLoadingDialog;


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == FINISHED) {
                if(mLoadingDialog != null && mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
                    mLoadingDialog = null;
                   MainActivity.open(SplashActivity.this);
                   SplashActivity.this.finish();
                    Log.e(TAG,"Do finished");
                }
            }
        }
    };


    public static void open(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SplashActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isTaskRoot()){finish();return;}
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        setFullScreen(null);
        initialView();
        if (PreferenceHelper.isLogin() == true) {
            initWebView();
        }
    }

    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setJavaScriptEnabled(true);
        settings.setDisplayZoomControls(true);
        settings.setSupportZoom(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setVerticalScrollBarEnabled(false);


        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setRequest_type(Constants.PAGE_MESSAGE);
        requestInfo.setPhone_h(PreferenceHelper.getUserTokenData().phone_h);
        requestInfo.setDevice_type(Constants.DEVICE_PLATFORM);
        requestInfo.setDevice_ver(BaseApplication.getInstance().getVersionCode());
        requestInfo.setDevice_id(DeviceUtil.getIMEI(BaseApplication.getInstance().getApplicationContext()));
        requestInfo.setApp_ver(BaseApplication.getInstance().getVersionName());
        requestInfo.setAccess_token(PreferenceHelper.getUserTokenData().access_token);
        String jsonString = JsonUtil.toJson(requestInfo);
        mWebView.postUrl(Constants.URL_PAGE,jsonString.getBytes());

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {


            //获取网站标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                //mWebView.setText(title);
            }
        });

    }

    private void initialView() {
        mLoadingDialog = new LoadingDialog(this,false);
        if (mLoadingDialog != null && !mLoadingDialog.isShowing() ) {
            mLoadingDialog.show();
        }
        if (PreferenceHelper.isLogin() == true) {
            Log.e(TAG,"is login");
            handler.sendEmptyMessageDelayed(FINISHED,COUNT_DOWN_TIME);
            //new CountDownTimerUtils(mSeekBar, this.getActivity(), COUNTDOWN_TIME_MILLION, COUNTDOWN_INTERVAL).start();
        } else {
            LoginActivity.open(this);
        }
    }

}
