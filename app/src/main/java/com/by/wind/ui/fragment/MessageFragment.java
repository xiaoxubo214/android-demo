package com.by.wind.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import com.by.wind.util.BussinessUtil;
import com.by.wind.util.DeviceUtil;
import com.by.wind.util.JsonUtil;
import com.by.wind.util.PreferenceHelper;
import com.wind.base.BaseFragment;
import com.wind.base.loading.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 */
public class MessageFragment extends BaseFragment implements LoadingDialog.ProgressCancelListener {



    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.not_network_iv)
    ImageView mIvNotNetwork;
    Unbinder unbinder;
    //private LoadingDialog mLoadingDialog;

    public static MessageFragment newInstance(String content){
        MessageFragment f = new MessageFragment();
        if (content != null) {
            final Bundle args = new Bundle();
            args.putString("content", content);
            f.setArguments(args);
        }
        return f;
    }

    @Override
    protected void lazyLoad() {

    }

    public WebView getWebView() {
        return mWebView;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_messge;
    }

    @Override
    protected void initAllView(Bundle savedInstanceState) {
        //mLoadingDialog = new LoadingDialog(context, this, true);

        WebSettings settings = mWebView.getSettings();
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setJavaScriptEnabled(true);
        settings.setDisplayZoomControls(true);
        settings.setSupportZoom(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        //mWebView.loadUrl(getIntent().getStringExtra("url"));
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.post(new Runnable() {
            @Override
            public void run() {
                try {
                    RequestInfo requestInfo = new RequestInfo();
                    requestInfo.setRequest_type(Constants.PAGE_MESSAGE);
                    requestInfo.setPhone_h(PreferenceHelper.getUserTokenData().phone_h);
                    requestInfo.setDevice_type(Constants.DEVICE_PLATFORM);
                    requestInfo.setDevice_ver(BaseApplication.getInstance().getVersionCode());
                    requestInfo.setDevice_id(DeviceUtil.getIMEI(BaseApplication.getInstance().getApplicationContext()));
                    requestInfo.setApp_ver(BaseApplication.getInstance().getVersionName());
                    requestInfo.setAccess_token(PreferenceHelper.getUserTokenData().access_token);
                    String jsonString = JsonUtil.toJson(requestInfo);
                    //Log.e(TAG,jsonString);
                    mWebView.postUrl(Constants.URL_PAGE,jsonString.getBytes());
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                EventBus.getDefault().post(new MessageEvent(MessageEvent.SET_TITLE_MESSAGE,title));

            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                EventBus.getDefault().post(new MessageEvent(MessageEvent.SET_BACK_BUTTON_MESSAGE));
            }
        });
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
        });

        if(!BussinessUtil.isNetWorkConnected(this.getActivity())) {
            mWebView.setVisibility(View.GONE);
            mIvNotNetwork.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        //Log.e("MessageFragment",event.getEventType());
        if (event.getEventType().equals(MessageEvent.NETWORK_OK)) {
            mWebView.setVisibility(View.VISIBLE);
            mIvNotNetwork.setVisibility(View.GONE);
        } else if (event.getEventType().equals(MessageEvent.NETWORK_FAIL)) {
            mWebView.setVisibility(View.GONE);
            mIvNotNetwork.setVisibility(View.VISIBLE);
        } else if (event.getEventType().equals(MessageEvent.SCAN_MESSAGE)) {
          /*  String call = "javascript:AppScan(" + event.getMessage() + ")";
            mWebView.loadUrl(call);*/
        } else if(event.getEventType().equals(MessageEvent.BACK_MESSAGE)) {
            if(mWebView.canGoBack()) {
                mWebView.goBack();
            }
        } else if (event.getEventType().equals(MessageEvent.CLOSE_MESSAGE)) {
            while (mWebView.canGoBack()) {
                mWebView.goBack();
            }
        }
    }

    public void callJsScan(String msg){
        Log.e("MessageFragment","call Js scan");
        String call = "javascript:AppScan(" + msg + ")";
        mWebView.loadUrl(call);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCancelProgress() {

    }
}
