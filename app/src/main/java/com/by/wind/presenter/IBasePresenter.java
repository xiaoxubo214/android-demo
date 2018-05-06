package com.by.wind.presenter;

import android.content.Context;

import com.by.wind.model.UserModel;
import com.wind.base.event.ActivityLifeCycleEvent;

import rx.subjects.PublishSubject;

/**
 * Created by Wind on 2017/11/15.
 */

public interface IBasePresenter {

    interface IHomePresenter {
        void getHomeInfo();
    }

    interface ILoginPresenter {
        void login(UserModel userModel, Context context, PublishSubject<ActivityLifeCycleEvent> publishSubject);
    }

    interface IForgetPwdPresenter {
        void doForgetPwd(Context context, PublishSubject<ActivityLifeCycleEvent> lifecycleSubject);

        void getCheckCode(Context context, PublishSubject<ActivityLifeCycleEvent> lifecycleSubject);
    }
}
