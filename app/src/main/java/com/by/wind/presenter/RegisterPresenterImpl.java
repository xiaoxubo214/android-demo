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

public class RegisterPresenterImpl implements IBasePresenter.IRegisterPresenter{

    private IBaseView.IRegisterView iRegisterView;

    public RegisterPresenterImpl(IBaseView.IRegisterView view) {
        this.iRegisterView = view;
    }

    @Override
    public void doForgetPwd(Context context, PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
        Observable getCodeOb = ApiManager.getInstance().getApiService().forget();
        ObservableUtil.getInstance().toSubscribe(getCodeOb, new ProgressSubscriber<String>(context) {
            @Override
            protected void _onNext(String verifyCode) {
                iRegisterView.doForgetPwd(Constants.SUCCESS);
            }
            @Override
            protected void _onError(String message) {
                iRegisterView.doForgetPwd(Constants.TEST);
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
                iRegisterView.getCheckCode(Constants.SUCCESS);
            }
            @Override
            protected void _onError(String message) {
                iRegisterView.getCheckCode(Constants.TEST);
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        }, Constants.HAWK_KEY, ActivityLifeCycleEvent.DESTROY, lifecycleSubject, false, false);
    }

    @Override
    public void doRegister(Context context, PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {

    }
}
