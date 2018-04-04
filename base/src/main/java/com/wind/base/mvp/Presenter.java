package com.wind.base.mvp;

import com.wind.base.mvp.BaseMvpView;

public interface Presenter<V extends BaseMvpView> {

    void attachView(V mvpView);

    void detachView();
}
