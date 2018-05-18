package com.by.wind.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
public class SaleFragment extends BaseFragment implements LoadingDialog.ProgressCancelListener {

    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.not_network_iv)
    ImageView mIvNotNetwork;
    Unbinder unbinder;
    //private LoadingDialog mLoadingDialog;

    public static SaleFragment newInstance(String content){
        SaleFragment f = new SaleFragment();
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

    @Override
    public int getContentViewId() {
        return R.layout.fragment_sale;
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
        //mWebView.loadUrl(getIntent().getStringExtra("url"));
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.post(new Runnable() {
            @Override
            public void run() {
                try {
                    RequestInfo requestInfo = new RequestInfo();
                    requestInfo.setRequest_type(Constants.PAGE_SALE);
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
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //mLoadingDialog.dismiss();
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
        if (event.getEventType().equals(MessageEvent.NETWORK_OK)) {
            mWebView.setVisibility(View.VISIBLE);
            mIvNotNetwork.setVisibility(View.GONE);
        } else if (event.getEventType().equals(MessageEvent.NETWORK_FAIL)) {
            mWebView.setVisibility(View.GONE);
            mIvNotNetwork.setVisibility(View.VISIBLE);
        }  else if(event.getEventType().equals(MessageEvent.TYPE_SCAN)){
            if (event.getMessage().equals(MessageEvent.SCAN_SALE)) {
                String call = "javascript:AppScan(" + event.getMessage() + ")";
                mWebView.loadUrl(call);
            }
        } else if(event.getEventType().equals(MessageEvent.TYPE_GO_BACK)) {
            if (event.getIntMessage() == MessageEvent.BACK_SALE) {
                if(mWebView.canGoBack()) {
                    Log.e(TAG,"canGoBack");
                    mWebView.goBack();
                }
            }

        }
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
