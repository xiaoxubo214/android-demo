package com.by.wind.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.by.wind.R;
import com.by.wind.widget.MyEditText;
import com.gc.materialdesign.views.ButtonRectangle;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.wind.base.BaseActivity;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Wind on 2017/11/17.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.username_et)
    MyEditText mUsernameEt;
    @BindView(R.id.password_et)
    MyEditText mPasswordEt;
    @BindView(R.id.submit_btn)
    ButtonRectangle mSubmitBtn;

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
    }

    @OnClick({R.id.submit_btn,R.id.register_tv,R.id.forget_tv})
    public void onViewClicked(View view)  {
        if (view.getId() == R.id.submit_btn) {
            MainActivity.open(this);
        } else if (view.getId() == R.id.register_tv) {
            RegisterActivity.open(this);
        } else if (view.getId() == R.id.forget_tv) {
            ForgetActivity.open(this);
        } else {
          Log.e(TAG,"Error");
        }
    }
}
