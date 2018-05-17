package com.by.wind.presenter;

import android.content.Context;

import com.by.wind.entity.LoginInfo;
import com.wind.base.event.ActivityLifeCycleEvent;

import rx.subjects.PublishSubject;

/**
 * Created by Wind on 2017/11/15.
 */

public interface IBasePresenter {

    interface ILoginPresenter {
        void login(LoginInfo loginInfo, Context context, PublishSubject<ActivityLifeCycleEvent> publishSubject);
    }

    interface IRegisterPresenter {
        void doForgetPwd(LoginInfo loginInfo,Context context, PublishSubject<ActivityLifeCycleEvent> lifecycleSubject);

        void getCheckCode(String phone, Context context, PublishSubject<ActivityLifeCycleEvent> lifecycleSubject);

        void doRegister(LoginInfo loginInfo, Context context, PublishSubject<ActivityLifeCycleEvent> lifecycleSubject);

    }

    interface IUserInfoPresenter {
        void getUserInfo( Context context, PublishSubject<ActivityLifeCycleEvent> publishSubject);
    }



}
