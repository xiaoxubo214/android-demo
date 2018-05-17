package com.by.wind.presenter;

import android.content.Context;

import com.by.wind.Constants;
import com.by.wind.component.net.ApiManager;
import com.by.wind.component.net.MyHashMap;
import com.by.wind.component.net.ObservableUtil;
import com.by.wind.component.net.ProgressSubscriber;
import com.by.wind.entity.LoginInfo;
import com.by.wind.entity.UserData;
import com.by.wind.util.PreferenceHelper;
import com.by.wind.util.ToastUtil;
import com.by.wind.view.IBaseView;
import com.wind.base.event.ActivityLifeCycleEvent;

import rx.Observable;
import rx.subjects.PublishSubject;

public class RegisterPresenter implements IBasePresenter.IRegisterPresenter{

    private IBaseView.IRegisterView iRegisterView;

    public RegisterPresenter(IBaseView.IRegisterView view) {
        this.iRegisterView = view;
    }

    @Override
    public void doForgetPwd(LoginInfo loginInfo, Context context, PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
        MyHashMap myHashMap = MyHashMap.newInstance();
        myHashMap.put(Constants.API_REQUEST_TYPE,Constants.API_MODIFY_PASSWORD);
        myHashMap.put(Constants.STR_PHONE, loginInfo.getUserName());
        myHashMap.put("password", loginInfo.getPassword());
        myHashMap.put("verification_code",loginInfo.getCode());
        Observable getCodeOb = ApiManager.getInstance().getApiService().api(myHashMap);
        ObservableUtil.getInstance().toSubscribe(getCodeOb, new ProgressSubscriber<UserData>(context) {
            @Override
            protected void _onNext(UserData userData) {
                iRegisterView.hideLoading();
                if (userData.result_code == Constants.RESULT_SUCCESS) {
                    //PreferenceHelper.saveUserToken(userData);
                    //PreferenceHelper.setIsLogin(true);
                    iRegisterView.doForgetResult(Constants.SUCCESS);
                }else {
                    iRegisterView.hideLoading();
                    ToastUtil.show(userData.result_msg);
                }
            }
            @Override
            protected void _onError(String message) {
                iRegisterView.hideLoading();
                if (Constants.isDebug) {
                    iRegisterView.doRegisterResult(Constants.TEST);
                }
                ToastUtil.show( message);
            }
        }, Constants.HAWK_KEY, ActivityLifeCycleEvent.DESTROY, lifecycleSubject, false, false);
    }

    @Override
    public void getCheckCode(String phone, final Context context, PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
        MyHashMap myHashMap = MyHashMap.newInstance();
        myHashMap.put(Constants.API_REQUEST_TYPE,Constants.API_GET_SMS);
        myHashMap.put(Constants.STR_PHONE, phone);
        Observable getCodeOb = ApiManager.getInstance().getApiService().api(myHashMap);
        ObservableUtil.getInstance().toSubscribe(getCodeOb, new ProgressSubscriber<UserData>(context) {
            @Override
            protected void _onNext(UserData userData) {
                if (userData.result_code == Constants.RESULT_SUCCESS) {
                    ToastUtil.show("验证码已发送！");
                    iRegisterView.getCheckCodeResult(Constants.SUCCESS);
                }else {
                    ToastUtil.show(userData.result_msg);
                }
            }
            @Override
            protected void _onError(String message) {
                if (Constants.isDebug) {
                        iRegisterView.getCheckCodeResult(Constants.TEST);
                    }
                ToastUtil.show( message);
            }
        }, Constants.HAWK_KEY, ActivityLifeCycleEvent.DESTROY, lifecycleSubject, false, false);
    }

    @Override
    public void doRegister(LoginInfo loginInfo, Context context, PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
        MyHashMap myHashMap = MyHashMap.newInstance();
        myHashMap.put(Constants.API_REQUEST_TYPE,Constants.API_REGISTER);
        myHashMap.put(Constants.STR_PHONE, loginInfo.getUserName());
        myHashMap.put("password", loginInfo.getPassword());
        myHashMap.put("verification_code",loginInfo.getCode());
        Observable getCodeOb = ApiManager.getInstance().getApiService().api(myHashMap);
        ObservableUtil.getInstance().toSubscribe(getCodeOb, new ProgressSubscriber<UserData>(context) {
            @Override
            protected void _onNext(UserData userData) {
                iRegisterView.hideLoading();
                if (userData.result_code == Constants.RESULT_SUCCESS) {
                    //PreferenceHelper.saveUserToken(userData);
                    //PreferenceHelper.setIsLogin(true);git
                    iRegisterView.doForgetResult(Constants.SUCCESS);
                }else {
                    iRegisterView.hideLoading();
                    ToastUtil.show(userData.result_msg);
                }
            }
            @Override
            protected void _onError(String message) {
                if (Constants.isDebug) {
                    iRegisterView.doRegisterResult(Constants.TEST);
                }
                ToastUtil.show( message);
            }
        }, Constants.HAWK_KEY, ActivityLifeCycleEvent.DESTROY, lifecycleSubject, false, false);
    }
}
