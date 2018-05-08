package com.by.wind.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.by.wind.Constants;
import com.by.wind.R;
import com.by.wind.entity.UserModel;
import com.by.wind.presenter.IBasePresenter;
import com.by.wind.presenter.LoginPresenter;
import com.by.wind.util.StringUtil;
import com.by.wind.util.ToastUtil;
import com.by.wind.view.IBaseView;
import com.wind.base.BaseActivity;
import com.wind.base.loading.LoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Wind on 2017/11/17.
 */

public class LoginActivity extends BaseActivity implements IBaseView.ILoginView{

    @BindView(R.id.username_et)
    EditText mUsernameEt;
    @BindView(R.id.password_et)
    EditText mPasswordEt;
    @BindView(R.id.submit_btn)
    Button mSubmitBtn;

    LoadingDialog mLoadingDialog;

    IBasePresenter.ILoginPresenter loginPresenter;

    public static void open(Context context) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setClass(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        //SystemClock.sleep(5000);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mLoadingDialog = new LoadingDialog(this,true);
        loginPresenter = new LoginPresenter(this,this);
    }

    @OnClick({R.id.submit_btn,R.id.register_tv,R.id.forget_tv})
    public void onViewClicked(View view)  {
        if (view.getId() == R.id.submit_btn) {
            login(mUsernameEt.getText().toString(),mPasswordEt.getText().toString());
        } else if (view.getId() == R.id.register_tv) {
            RegisterActivity.open(this,Constants.START_ACTIVITY_REGISTER);
        } else if (view.getId() == R.id.forget_tv) {
            RegisterActivity.open(this,Constants.START_ACTIVITY_FORGET);
        } else {
          Log.e(TAG,"Error");
        }
    }

    private void login(String username, String password) {
        if(username == null || TextUtils.isEmpty(username)) {
            ToastUtil.show("电话号码不能为空");
            return;
        }
         if (!StringUtil.isMobile(username)){
             ToastUtil.show("请输入正确的手机号码");
             return;
         }

        if(password == null || TextUtils.isEmpty(password)) {
            ToastUtil.show("密码不能为空");
            return;
        }

        loginPresenter.login(new UserModel(username,password),this,lifecycleSubject);
    }

    @Override
    public void showResult(int result) {
        Log.e(TAG,"RESULT");
        if (Constants.SUCCESS == result) {
            MainActivity.open(this);
        } else {
            MainActivity.open(this);
        }
    }

    @Override
    public void showLoading(String msg) {
        if (mLoadingDialog != null) {
            mLoadingDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void showError(String err) {

    }
}
