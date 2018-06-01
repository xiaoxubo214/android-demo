package com.by.wind.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.by.wind.BaseApplication;
import com.by.wind.Constants;
import com.by.wind.component.event.MessageEvent;
import com.by.wind.entity.RequestInfo;
import com.by.wind.util.BussinessUtil;
import com.by.wind.util.DeviceUtil;
import com.by.wind.util.JsonUtil;
import com.by.wind.util.PreferenceHelper;
import com.by.wind.widget.TitleActivity;
import com.by.wind.R;
import com.umeng.message.PushAgent;
import com.wind.base.loading.LoadingDialog;


import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends TitleActivity {


    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.not_network_iv)
    ImageView mIvNotNetwork;

    private IntentFilter mIntentFilter;
    private NetworkChangeReceiver mNetworkChangeReceiver;

    LoadingDialog mLoadingDialog;

    private static String PAGE_URL = Constants.PAGE_MESSAGE;
    private static String contentTitle = "";

    public static void open(Context context, String pageUrl,String title) {
        PAGE_URL = pageUrl;
        contentTitle = title;
        Intent intent = new Intent();
        intent.setClass(context, WebViewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_web_view);
        llCustomTitle.set_show_left_button(true);
        llCustomTitle.setTvLeftButton("返回");
        showTitleContent(contentTitle);
        PushAgent.getInstance(this).onAppStart();
        mLoadingDialog = new LoadingDialog(this,false);
/*        if (mLoadingDialog != null && !mLoadingDialog.isShowing() ) {
            mLoadingDialog.show();
        }*/
    }

    @Override
    protected void initializeViews() {

 /*       mLoadingDialog = new LoadingDialog(this,false);
        if ( mLoadingDialog != null ) {
            mLoadingDialog.show();
        }*/

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
        mWebView.post(new Runnable() {
            @Override
            public void run() {
                try {
                    RequestInfo requestInfo = new RequestInfo();
                    requestInfo.setRequest_type(PAGE_URL);
                    requestInfo.setPhone_h(PreferenceHelper.getUserTokenData().phone_h);
                    requestInfo.setDevice_type(Constants.DEVICE_PLATFORM);
                    requestInfo.setDevice_ver(BaseApplication.getInstance().getVersionCode());
                    requestInfo.setDevice_id(DeviceUtil.getIMEI(BaseApplication.getInstance().getApplicationContext()));
                    requestInfo.setApp_ver(BaseApplication.getInstance().getVersionName());
                    requestInfo.setAccess_token(PreferenceHelper.getUserTokenData().access_token);
                    String jsonString = JsonUtil.toJson(requestInfo);
                    mWebView.postUrl(Constants.URL_PAGE,jsonString.getBytes());
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        //Log.e(TAG,Constants.URL_PAGE + Constants.PARAM_SPLIT + PAGE_URL);
        //mWebView.loadUrl(Constants.URL_PAGE + Constants.PARAM_SPLIT + PAGE_URL);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                showTitleContent(title);
            }

        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });
        /*
        mWebView.setOnTouchListener(new View.OnTouchListener() {
            float startX = 0;
            float scrollSize = 120;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = (int) event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        int endX = (int) event.getX();
                        if(endX>startX && mWebView.canGoBack() && endX-startX>scrollSize){
                            mWebView.goBack();
                        }else if(endX<startX &&mWebView.canGoForward() && startX-endX>scrollSize){
                            mWebView.goForward();
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });*/

        if(!BussinessUtil.isNetWorkConnected(this)) {
            mWebView.setVisibility(View.GONE);
            mIvNotNetwork.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ( keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
          mWebView.goBack();
          return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void initializeData() {

    }

    @Override
    protected void bindViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerNetwork();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mNetworkChangeReceiver);
    }

    private void registerNetwork() {
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mNetworkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(mNetworkChangeReceiver, mIntentFilter);
    }

    class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(!BussinessUtil.isNetWorkConnected(WebViewActivity.this)) {
                mWebView.setVisibility(View.GONE);
                mIvNotNetwork.setVisibility(View.VISIBLE);
            } else {
                mWebView.setVisibility(View.VISIBLE);
                mIvNotNetwork.setVisibility(View.GONE);
            }
        }
    }
}
