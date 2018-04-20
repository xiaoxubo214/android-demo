package com.by.wind.component.net;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;


import com.by.wind.BaseApplication;
import com.by.wind.R;
import com.by.wind.ui.activity.LoginActivity;
import com.by.wind.util.BussinessUtil;
import com.by.wind.util.StringUtil;
import com.by.wind.util.ToastUtil;
import com.wind.base.loading.LoadingDialog;

import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wind ic_on 17/2/23.
 */
public abstract class ProgressSubscriber<T> extends Subscriber<T> implements DialogInterface.OnCancelListener {

    private WeakReference<Activity> mContext;
    private LoadingDialog mLoadingDialog;
    private boolean mShowDialog = true;

    public ProgressSubscriber(LoadingDialog dialog) {
        this(dialog, true);
    }

    public ProgressSubscriber(Context context) {
    }

    public ProgressSubscriber(Activity context) {
        mContext = new WeakReference<>(context);
    }

    public ProgressSubscriber(LoadingDialog dialog, boolean showDialog) {
        mLoadingDialog = dialog;
        mShowDialog = showDialog;
        if (null != mLoadingDialog) mLoadingDialog.setOnCancelListener(this);
    }

    public ProgressSubscriber(Context context, boolean showDialog) {
        mShowDialog = showDialog;
        if (null != mLoadingDialog) mLoadingDialog.setOnCancelListener(this);
    }

    @Override
    public void onCompleted() {
        if (mShowDialog) {
            dismissProgressDialog();
        }

    }

    /**
     * 显示Dialog
     */
    public void showProgressDialog() {
        if (mShowDialog) {
            if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
                mLoadingDialog.show();
            }
        }
    }

    /**
     * 隐藏Dialog
     */
    private void dismissProgressDialog() {
        if (mShowDialog) {
            if (mLoadingDialog != null) {
                mLoadingDialog.dismiss();
                mLoadingDialog = null;
            }
        }
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (!BussinessUtil.isNetWorkConnected(BaseApplication.getInstance())) { //这里自行替换判断网络的代码
            _onError(StringUtil.getRes(R.string.network_exception));
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            if (408 == httpException.code() || 504 == httpException.code()) {
                _onError(StringUtil.getRes(R.string.network_timeout));
            } else if (500 == httpException.code()) {
                _onError(StringUtil.getRes(R.string.server_exception));
            } else {
                _onError(StringUtil.getRes(R.string.request_fail));
            }
        } else if (e instanceof ServerException) {
            if (401 == ((ServerException) e).errorCode) {            //Token过期,刷新token
                refreshToken();
                _onError(e.getMessage());
            } else {
                _onError(e.getMessage());
            }
        } else if (e instanceof SocketTimeoutException) {
            _onError(StringUtil.getRes(R.string.request_timeout));
        } else {
            _onError(StringUtil.getRes(R.string.request_fail));
        }
        dismissProgressDialog();
    }

    /**
     * 刷新Token
     */
    @SuppressLint("CheckResult")
    private void refreshToken() {
        if (null == PreferenceHelper.getUserToken()) return;
        ApiManager
                .getInstance()
                .getApiService()
                .getRefreshToken(PreferenceHelper.getUserToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userToken -> {
                    PreferenceHelper.saveUserToken(userToken);
                    _onError("Token已刷新，请重新刷新数据！");
                }, throwable -> {
                    ToastUtil.show("Token已过期，请重新登录！");
                    PreferenceHelper.saveUserToken(null);
                    if (null != mContext && null != mContext.get())
                        LoginActivity.open(mContext.get());
                    else
                        LoginActivity.open(BaseApplication.getInstance());

                });
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);
}
