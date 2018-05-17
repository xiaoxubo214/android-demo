package com.by.wind.presenter;

import android.content.Context;

import com.by.wind.Constants;
import com.by.wind.component.net.ApiManager;
import com.by.wind.component.net.MyHashMap;
import com.by.wind.component.net.ObservableUtil;
import com.by.wind.component.net.ProgressSubscriber;
import com.by.wind.entity.UserInfo;
import com.by.wind.util.PreferenceHelper;
import com.by.wind.view.IBaseView;
import com.wind.base.event.ActivityLifeCycleEvent;
import com.wind.base.mvp.BaseMvpPresenter;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by wind on 2018/3/27.
 */

public class UserInfoPresenter extends BaseMvpPresenter<IBaseView.IUserInfoView> implements IBasePresenter.IUserInfoPresenter {

    private IBaseView.IUserInfoView mLoginView;

    public UserInfoPresenter(IBaseView.IUserInfoView userInfoView, Context context) {
        this.mLoginView= userInfoView;
    }

    @Override
    public void getUserInfo(String phone, Context context, PublishSubject<ActivityLifeCycleEvent> publishSubject) {
        MyHashMap myHashMap = MyHashMap.newInstance();
        myHashMap.put(Constants.API_REQUEST_TYPE,Constants.API_LOGIN);

        Observable observable = ApiManager.getInstance().getApiService().api(myHashMap);

        ObservableUtil.getInstance().toSubscribe(observable, new ProgressSubscriber <UserInfo>(context) {

            @Override
            protected void _onNext(UserInfo userInfo) {
                UserInfo tempUserInfo = PreferenceHelper.getUserInfo();
                if (tempUserInfo == null) {
                    return;
                }
                if (!(tempUserInfo.getUserPhone().equals(userInfo.getUserPhone())&&
                        tempUserInfo.getAvatar().equals(userInfo.getAvatar())&&
                        tempUserInfo.getUserName().equals(userInfo.getUserName()))) {
                    PreferenceHelper.saveUserInfo(userInfo);
                    mLoginView.showResult(Constants.SUCCESS);
                }
            }

            @Override
            protected void _onError(String message) {
                if(Constants.isDebug) {
                    mLoginView.showResult(Constants.TEST);
                }
            }
        }, Constants.HAWK_KEY, ActivityLifeCycleEvent.DESTROY, publishSubject, false, false);
    }
}
