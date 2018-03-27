package com.by.wind.base;

public interface Presenter<V extends BaseMvpView> {

    void attachView(V mvpView);

    void detachView();
}
