package com.by.wind.presenter;

import android.content.Context;
import android.util.Log;

import com.by.wind.Constants;
import com.by.wind.component.net.ApiManager;
import com.by.wind.component.net.MyHashMap;
import com.by.wind.component.net.ObservableUtil;
import com.by.wind.component.net.ProgressSubscriber;
import com.by.wind.entity.UserInfo;
import com.by.wind.entity.UserToken;
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
    public void getUserInfo( Context context, PublishSubject<ActivityLifeCycleEvent> publishSubject) {
        MyHashMap myHashMap = MyHashMap.newInstance();
        myHashMap.put(Constants.API_REQUEST_TYPE,Constants.API_GET_USER_INFO);

        Observable observable = ApiManager.getInstance().getApiService().getUserInfo(myHashMap);

        ObservableUtil.getInstance().toSubscribe(observable, new ProgressSubscriber <UserInfo>(context) {

            @Override
            protected void _onNext(UserInfo userData) {
                if (userData.result_code.equals( Constants.RESULT_SUCCESS)) {
                    Log.e(TAG,"success");
                    UserInfo tempUserInfo = PreferenceHelper.getUserInfo();
                    if (tempUserInfo == null) {
                        PreferenceHelper.saveUserInfo(userData);
                    } else {
                        if (!(tempUserInfo.phone_h.equals(userData.phone_h) &&
                                tempUserInfo.head_image.equals(userData.head_image) &&
                                tempUserInfo.member_name.equals(userData.member_name))) {
                            PreferenceHelper.saveUserInfo(userData);
                        }
                    }
                    mLoginView.showResult(Constants.SUCCESS);
                }
            }

            @Override
            protected void _onError(String message) {
                Log.e(TAG,"error");
                if(Constants.isDebug) {
                    mLoginView.showResult(Constants.TEST);
                }
            }
        }, Constants.HAWK_KEY, ActivityLifeCycleEvent.DESTROY, publishSubject, false, false);
    }
}
