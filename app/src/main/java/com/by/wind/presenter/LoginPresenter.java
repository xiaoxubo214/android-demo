package com.by.wind.presenter;

import com.by.wind.base.BaseMvpPresenter;
import com.by.wind.base.IBasePresenter;
import com.by.wind.component.net.CallBack;
import com.by.wind.model.UserModel;
import com.by.wind.view.LoginView;

/**
 * Created by DELL on 2018/3/27.
 */

public class LoginPresenter extends BaseMvpPresenter<LoginView> implements IBasePresenter.ILoginPresenter {

    private UserModel userModel;
    private LoginView loginView;

    public LoginPresenter(UserModel userModel,LoginView loginView) {
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
