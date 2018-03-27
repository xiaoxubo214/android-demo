package com.by.wind.presenter;


import com.by.wind.widget.BaseMvpView;
import com.by.wind.widget.BaseMvpView;

public class BaseMvpPresenter<V extends BaseMvpView> implements Presenter<V> {

    private V mvpView;
    @Override
    public void attachView(V mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void detachView() {
        this.mvpView = null;
    }

    public V getMvpView() {
        return mvpView;
    }

    public boolean isAttachView() {
        return mvpView != null;
    }

    public static class MvpViewNotAttachException extends RuntimeException {
        public MvpViewNotAttachException() {
            super("not find attachView be call");
        }
    }
}
