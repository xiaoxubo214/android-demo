package com.by.wind.presenter;

import android.content.Context;

import com.by.wind.Constants;
import com.by.wind.component.net.ApiManager;

import com.by.wind.component.net.ObservableUtil;
import com.by.wind.component.net.PreferenceHelper;
import com.by.wind.component.net.ProgressSubscriber;

import com.by.wind.entity.UserModel;
import com.by.wind.entity.UserToken;
import com.by.wind.view.IBaseView;
import com.wind.base.event.ActivityLifeCycleEvent;
import com.wind.base.mvp.BaseMvpPresenter;


import rx.Observable;
import rx.subjects.PublishSubject;
/**
 * Created by wind on 2018/3/27.
 */

public class LoginPresenter extends BaseMvpPresenter<IBaseView.ILoginView> implements IBasePresenter.ILoginPresenter {

    private IBaseView.ILoginView mLoginView;

    public LoginPresenter( IBaseView.ILoginView loginView, Context context) {
        this.mLoginView= loginView;
    }

    public void login(UserModel userModel, Context context, PublishSubject<ActivityLifeCycleEvent> publishSubject) {
        Observable observable = ApiManager.getInstance().getApiService().login(userModel.getUserName(),userModel.getPassword());

        ObservableUtil.getInstance().toSubscribe(observable, new ProgressSubscriber <UserToken>(context) {

            @Override
            protected void _onNext(UserToken userToken) {
                mLoginView.hideLoading();
                PreferenceHelper.saveUserToken(userToken);
                PreferenceHelper.setIsLogin(true);
                mLoginView.showResult(Constants.SUCCESS);
            }

            @Override
            protected void _onError(String message) {
                mLoginView.hideLoading();
                mLoginView.showResult(Constants.TEST);
            }
        }, Constants.HAWK_KEY, ActivityLifeCycleEvent.DESTROY, publishSubject, false, false);
    }
}
