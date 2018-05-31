package com.wind.base.mvp;

public interface Presenter<V extends BaseMvpView> {

    void attachView(V mvpView);

    void detachView();
}
