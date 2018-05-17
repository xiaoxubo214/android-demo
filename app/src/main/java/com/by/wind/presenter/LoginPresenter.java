package com.by.wind.presenter;

import android.content.Context;
import android.util.Log;

import com.by.wind.Constants;
import com.by.wind.component.net.ApiManager;

import com.by.wind.component.net.MyHashMap;
import com.by.wind.component.net.ObservableUtil;
import com.by.wind.util.PreferenceHelper;
import com.by.wind.component.net.ProgressSubscriber;

import com.by.wind.entity.LoginInfo;
import com.by.wind.entity.UserData;
import com.by.wind.util.ToastUtil;
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

    public void login(LoginInfo loginInfo, Context context, PublishSubject<ActivityLifeCycleEvent> publishSubject) {
        MyHashMap myHashMap = MyHashMap.newInstance();
        myHashMap.put(Constants.API_REQUEST_TYPE,Constants.API_LOGIN);
        myHashMap.put(Constants.STR_PHONE, loginInfo.getUserName());
        myHashMap.put("password", loginInfo.getPassword());
        Observable observable = ApiManager.getInstance().getApiService().api(myHashMap);
        ObservableUtil.getInstance().toSubscribe(observable, new ProgressSubscriber <UserData>(context) {

            @Override
            protected void _onNext(UserData userData) {
                Log.e(TAG, userData.toString());
                mLoginView.hideLoading();
                if (userData.result_code.equals( Constants.RESULT_SUCCESS)) {
                    PreferenceHelper.saveUserTokenData(userData);
                    PreferenceHelper.setIsLogin(true);
                    mLoginView.showResult(Constants.SUCCESS);
                }else {
                    mLoginView.hideLoading();
                    ToastUtil.show(userData.result_msg);
                }
            }

            @Override
            protected void _onError(String message) {
                mLoginView.hideLoading();
                ToastUtil.show(message);
                if(Constants.isDebug) {
                    PreferenceHelper.setIsLogin(true);
                    mLoginView.showResult(Constants.TEST);
                }

            }
        }, Constants.HAWK_KEY, ActivityLifeCycleEvent.DESTROY, publishSubject, false, false);
    }
}
