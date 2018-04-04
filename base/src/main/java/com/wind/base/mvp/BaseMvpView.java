package com.wind.base.mvp;


public interface BaseMvpView {

    void showLoading(String msg);

    void hideLoading();

    void showError(String err);

}
