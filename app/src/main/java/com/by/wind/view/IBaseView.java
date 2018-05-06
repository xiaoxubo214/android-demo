package com.by.wind.view;

import com.wind.base.mvp.BaseMvpView;

public interface IBaseView {

    public interface IHomeView extends BaseMvpView {

        public void setBannerResult();

        public void setFunctionResult();

    }

    public interface ILoginView extends BaseMvpView {

        void showResult(int result);
    }


    interface IForgetPwdView extends BaseMvpView {
        void doForgetPwd(int retCode);

        void getCheckCode(int retCode);

        void validData(int validCode);
    }



}
