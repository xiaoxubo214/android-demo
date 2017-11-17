package com.by.wind.demo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.by.wind.demo.R;
import com.by.wind.demo.view.BaseActivity;
import com.gc.materialdesign.views.ButtonRectangle;
import com.rengwuxian.materialedittext.MaterialEditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Wind on 2017/11/17.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.username_et)
    MaterialEditText mUsernameEt;
    @BindView(R.id.password_et)
    MaterialEditText mPasswordEt;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.submit_btn)
    public void onViewClicked() {
        Log.e(TAG,"test");
        MainActivity.open(this);
    }
}
