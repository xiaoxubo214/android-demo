package com.by.wind.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.by.wind.R;
import com.by.wind.widget.MoblieEditText;
import com.gc.materialdesign.views.ButtonRectangle;
import com.wind.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgetActivity extends BaseActivity {


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
        intent.setClass(context, ForgetActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        ButterKnife.bind(this);
    }
}
