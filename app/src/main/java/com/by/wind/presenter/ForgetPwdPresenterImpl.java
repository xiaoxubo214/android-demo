package com.by.wind.presenter;

import android.content.Context;
import android.widget.Toast;

import com.by.wind.Constants;
import com.by.wind.component.net.ApiManager;
import com.by.wind.component.net.ObservableUtil;
import com.by.wind.component.net.ProgressSubscriber;
import com.by.wind.model.UserModel;
import com.by.wind.util.ToastUtil;
import com.by.wind.view.IBaseView;
import com.wind.base.event.ActivityLifeCycleEvent;

import rx.Observable;
import rx.subjects.PublishSubject;

public class ForgetPwdPresenterImpl implements IBasePresenter.IForgetPwdPresenter{

    private IBaseView.IForgetPwdView IForgetPwdView;
    private UserModel userModel;

    public ForgetPwdPresenterImpl(IBaseView.IForgetPwdView view) {
        this.IForgetPwdView = view;
        userModel = new UserModel();
    }

    @Override
    public void doForgetPwd(Context context, PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
        Observable getCodeOb = ApiManager.getInstance().getApiService().forget();
        ObservableUtil.getInstance().toSubscribe(getCodeOb, new ProgressSubscriber<String>(context) {
            @Override
            protected void _onNext(String verifyCode) {
                IForgetPwdView.doForgetPwd(Constants.SUCCESS);
            }
            @Override
            protected void _onError(String message) {
                IForgetPwdView.doForgetPwd(Constants.TEST);
                ToastUtil.show( message);
            }
        }, Constants.HAWK_KEY, ActivityLifeCycleEvent.DESTROY, lifecycleSubject, false, false);
    }

    @Override
    public void getCheckCode(final Context context, PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
        Observable getCodeOb = ApiManager.getInstance().getApiService().forget();
        ObservableUtil.getInstance().toSubscribe(getCodeOb, new ProgressSubscriber<String>(context) {
            @Override
            protected void _onNext(String verifyCode) {
                IForgetPwdView.getCheckCode(Constants.SUCCESS);
            }
            @Override
            protected void _onError(String message) {
                IForgetPwdView.getCheckCode(Constants.TEST);
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        }, Constants.HAWK_KEY, ActivityLifeCycleEvent.DESTROY, lifecycleSubject, false, false);
    }

    public void checkUserMobile(String userNum){

    }

    public void checkUserModel(String name , String pwd){

    }

}
