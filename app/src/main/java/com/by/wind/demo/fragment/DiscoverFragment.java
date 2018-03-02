package com.by.wind.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.by.wind.demo.R;
import com.by.wind.demo.view.BaseFragment;
import com.by.wind.demo.view.loading.LoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 */
public class DiscoverFragment extends BaseFragment implements LoadingDialog.ProgressCancelListener{

    @BindView(R.id.webView)
    WebView mWebView;
    Unbinder unbinder;
    private LoadingDialog mLoadingDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_discover;
    }

    @Override
    protected void initAllView(Bundle savedInstanceState) {
        mLoadingDialog = new LoadingDialog(context, this, true);
        mLoadingDialog.show();
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
        mWebView.loadUrl("http://www.baidu.com");
        Log.e(TAG,"initAllView");
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
                //mLoadingDialog.hide();
            }
        });
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
