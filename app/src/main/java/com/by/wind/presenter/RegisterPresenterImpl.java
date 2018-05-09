package com.by.wind.presenter;

import android.content.Context;
import android.widget.Toast;

import com.by.wind.Constants;
import com.by.wind.component.net.ApiManager;
import com.by.wind.component.net.ObservableUtil;
import com.by.wind.component.net.ProgressSubscriber;
import com.by.wind.entity.LoginInfo;
import com.by.wind.entity.UserToken;
import com.by.wind.util.PreferenceHelper;
import com.by.wind.util.ToastUtil;
import com.by.wind.view.IBaseView;
import com.wind.base.event.ActivityLifeCycleEvent;

import rx.Observable;
import rx.subjects.PublishSubject;

public class RegisterPresenterImpl implements IBasePresenter.IRegisterPresenter{

    private IBaseView.IRegisterView iRegisterView;

    public RegisterPresenterImpl(IBaseView.IRegisterView view) {
        this.iRegisterView = view;
    }

    @Override
    public void doForgetPwd(LoginInfo loginInfo, Context context, PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
        Observable getCodeOb = ApiManager.getInstance().getApiService().doForget(Constants.API_LOGIN,loginInfo.getUserName(), loginInfo.getPassword());
        ObservableUtil.getInstance().toSubscribe(getCodeOb, new ProgressSubscriber<UserToken>(context) {
            @Override
            protected void _onNext(UserToken userToken) {
                iRegisterView.hideLoading();
                PreferenceHelper.saveUserToken(userToken);
                PreferenceHelper.setIsLogin(true);
                iRegisterView.doRegisterResult(Constants.SUCCESS);
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

    @Override
    public void getCheckCode(String phone, final Context context, PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
        Observable getCodeOb = ApiManager.getInstance().getApiService().getCode(Constants.API_GET_SMS, phone);
        ObservableUtil.getInstance().toSubscribe(getCodeOb, new ProgressSubscriber<String>(context) {
            @Override
            protected void _onNext(String verifyCode) {
                iRegisterView.getCheckCodeResult(Constants.SUCCESS);
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
        Observable getCodeOb = ApiManager.getInstance().getApiService().doRegister(Constants.API_LOGIN,loginInfo.getUserName(), loginInfo.getPassword());
        ObservableUtil.getInstance().toSubscribe(getCodeOb, new ProgressSubscriber<UserToken>(context) {
            @Override
            protected void _onNext(UserToken userToken) {
                iRegisterView.hideLoading();
                PreferenceHelper.saveUserToken(userToken);
                PreferenceHelper.setIsLogin(true);
                iRegisterView.doRegisterResult(Constants.SUCCESS);
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
