package com.wind.base.mvp;


public class BaseMvpPresenter<V extends BaseMvpView> implements Presenter<V> {

    public final static String TAG = "BaseMvpPresenter";

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
