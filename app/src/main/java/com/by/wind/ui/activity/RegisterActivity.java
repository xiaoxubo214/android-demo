package com.by.wind.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.by.wind.R;
import com.gc.materialdesign.views.ButtonRectangle;
import com.wind.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.etUserNum)
    EditText etUserNum;
    @BindView(R.id.etVerifyCode)
    EditText etVerifyCode;
    @BindView(R.id.tvVerifyCode)
    TextView tvVerifyCode;
    @BindView(R.id.etNewPwd)
    EditText etNewPwd;
    @BindView(R.id.etConfirmPwd)
    EditText etConfirmPwd;
    @BindView(R.id.viewflipper)
    ViewFlipper viewflipper;
    @BindView(R.id.return_btn)
    ButtonRectangle returnBtn;
    @BindView(R.id.submit_btn)
    ButtonRectangle submitBtn;

    public static void open(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.return_btn, R.id.submit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_btn:
                finish();
                break;
            case R.id.submit_btn:
                break;
        }
    }
}
