package com.by.wind.component.net;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.by.wind.R;
import com.by.wind.entity.UserToken;
import com.by.wind.ui.activity.LoginActivity;
import com.by.wind.util.BussinessUtil;
import com.by.wind.util.FileUtil;
import com.wind.base.loading.LoadingDialog;

import retrofit2.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wind ic_on 17/2/23.
 */
public abstract class ProgressSubscriber<T> extends Subscriber<T> implements LoadingDialog.ProgressCancelListener {

    private LoadingDialog mLodingDialog;
    private Context mContext;
    private boolean mShowDialog = true;

    public ProgressSubscriber(Context context) {
        mContext = context;
        mLodingDialog = new LoadingDialog(context, this, true);
    }

    public ProgressSubscriber(Context context, boolean showDialog) {
        mContext = context;
        mLodingDialog = new LoadingDialog(context, this, true);
        mShowDialog = showDialog;
    }

    @Override
    public void onCompleted() {
        Log.i("ProgressSubscriber", "onCompleted");
        if (mShowDialog) {
            dismissProgressDialog();
        }

    }

    /**
     * 显示Dialog
     */
    public void showProgressDialog() {
        Log.i("ProgressSubscriber", "showProgressDialog");
        if (mShowDialog) {
            if (mLodingDialog != null && !mLodingDialog.isShowing()) {
                mLodingDialog.show();
            }
        }
    }

    /**
     * 隐藏Dialog
     */
    private void dismissProgressDialog() {
        Log.i("ProgressSubscriber", "dismissProgressDialog");
        if (mShowDialog) {
            if (mLodingDialog != null) {
                mLodingDialog.dismiss();
                mLodingDialog = null;
            }
        }
    }

    @Override
    public void onNext(T t) {
        //Log.i("ProgressSubscriber","onNext");
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        // Log.i("ProgressSubscriber","onError");
        if (!BussinessUtil.isNetWorkConnected(mContext)) { //这里自行替换判断网络的代码
            _onError(mContext.getResources().getString(R.string.network_exception));
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            //408请求超时 504网关超时 502坏的网关
            if (408 == httpException.code() || 504 == httpException.code()) {
                _onError(mContext.getResources().getString(R.string.network_timeout));
            } else if (500 == httpException.code()) {
                _onError("服务器未知异常");
            } else {
                _onError("请求失败，请稍后再试...");
            }
        } else if (e instanceof ApiException) {
            if (401 == ((ApiException) e).errorCode) {            //Token过期,刷新token
                //refreshToken();
                gotoLogin();
                _onError(e.getMessage());
            } else {
                _onError(e.getMessage());
            }
        } else {
            _onError("请求失败，请稍后再试...");
        }
        dismissProgressDialog();
    }

    /**
     * 刷新Token
     */
/*    private void refreshToken() {
        if (null == PreferenceHelper.getUserToken()) return;
        Observable o = ApiManager.getInstance().getApiService().getRefreshToken(PreferenceHelper.getUserToken().accessToken, PreferenceHelper.getUserToken().refreshToken, "PATCH");
        o.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<UserToken>() {
                    @Override
                    public void call(UserToken userToken) {
                        PreferenceHelper.saveUserToken(userToken);
//                        refreshTokenSuccess();                      //自动刷新数据回调方法
                        _onError("Token已刷新，请重新刷新数据！");       //手动刷新数据提示方法
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        gotoLogin();
                    }
                });
    }*/

    private void gotoLogin() {
        Toast.makeText(mContext, "Token已过期，请重新登录！", Toast.LENGTH_SHORT).show();
        FileUtil.clearCache();
        LoginActivity.open(mContext);
        if (mContext instanceof Activity) ((Activity) mContext).finish();
    }

    @Override
    public void onCancelProgress() {
        //Log.i("ProgressSubscriber","onCancelProgress");
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }

    protected void refreshTokenSuccess() {
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);
}
