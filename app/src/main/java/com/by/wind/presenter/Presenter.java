package com.by.wind.presenter;

import com.by.wind.widget.BaseMvpView;

public interface Presenter<V extends BaseMvpView> {

    void attachView(V mvpView);

    void detachView();
}
