package com.by.wind.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.by.wind.R;
import com.by.wind.component.net.event.MessageEvent;
import com.by.wind.util.ToastUtil;
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
public class DiscoverFragment extends BaseFragment implements LoadingDialog.ProgressCancelListener {

    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.not_network_iv)
    ImageView mIvNotNetwork;
    Unbinder unbinder;
    //private LoadingDialog mLoadingDialog;

    @Override
    protected void lazyLoad() {

    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_discover;
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
        mWebView.loadUrl("http://www.tmall.com");
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                //
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //mLoadingDialog.dismiss();
            }
        });


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
        Log.e(TAG,"receive message");
        if (event.getEventType().equals(MessageEvent.NETWORK_OK)) {
            mWebView.setVisibility(View.VISIBLE);
            mIvNotNetwork.setVisibility(View.GONE);
        } else if (event.getEventType().equals(MessageEvent.NETWORK_FAIL)) {
            mWebView.setVisibility(View.GONE);
            mIvNotNetwork.setVisibility(View.VISIBLE);
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
