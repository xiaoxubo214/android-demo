package com.by.wind.presenter;

import com.by.wind.base.BaseMvpPresenter;
import com.by.wind.component.net.CallBack;
import com.by.wind.model.UserModel;
import com.by.wind.view.IBaseView;

/**
 * Created by wind on 2018/3/27.
 */

public class LoginPresenter extends BaseMvpPresenter<IBaseView.ILoginView> implements IBasePresenter.ILoginPresenter {

    private UserModel userModel;
    private IBaseView.ILoginView loginView;

    public LoginPresenter(UserModel userModel,IBaseView.ILoginView loginView) {
        this.userModel = userModel;
        this.loginView = loginView;
    }

    public void login() {
        loginView.showLoading("loading---");
        userModel.login(loginView.getUsername(),loginView.getPassword(), new CallBack(){

            @Override
            public void onSuccess() {
                loginView.hideLoading();
                loginView.showResult("success");
            }

            @Override
            public void onFail(String fail) {
                loginView.hideLoading();
                loginView.showError("error");
            }
        });
    }
}
