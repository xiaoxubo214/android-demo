package com.by.wind.presenter;

import android.content.Context;

import com.by.wind.component.net.CallBack;
import com.by.wind.model.UserModel;
import com.by.wind.view.IBaseView;
import com.wind.base.mvp.BaseMvpPresenter;
import com.wind.base.loading.LoadingDialog;

/**
 * Created by wind on 2018/3/27.
 */

public class LoginPresenter extends BaseMvpPresenter<IBaseView.ILoginView> implements IBasePresenter.ILoginPresenter {

    private UserModel mUserModel;
    private IBaseView.ILoginView mLoginView;
    private LoadingDialog mLoading;
    private Context mContext;

    public LoginPresenter(UserModel userModel, IBaseView.ILoginView loginView, Context context) {
        this.mUserModel = userModel;
        this.mLoginView= loginView;
        mContext =context;
    }

    public void login() {
        //Observable observable = BaseApplication.getInstance().apiService.login();
        mUserModel.login(mLoginView.getUsername(),mLoginView.getPassword(), new CallBack(){

            @Override
            public void onSuccess() {
                mLoginView.hideLoading();
                mLoginView.showResult("success");
            }

            @Override
            public void onFail(String fail) {
                mLoginView.hideLoading();
                mLoginView.showError("error");
            }
        });
    }
}
