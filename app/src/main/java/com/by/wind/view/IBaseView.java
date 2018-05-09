package com.by.wind.view;

import com.wind.base.mvp.BaseMvpView;

public interface IBaseView {

    public interface ILoginView extends BaseMvpView {

        void showResult(int result);
    }


    interface IRegisterView extends BaseMvpView {
        void doForgetResult(int retCode);

        void getCheckCodeResult(int retCode);

        void doRegisterResult(int retCode);
    }


    public interface IUserInfoView extends BaseMvpView {

        void showResult(int result);
    }



}
