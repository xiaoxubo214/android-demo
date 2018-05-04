package com.by.wind.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.by.wind.R;
import com.wind.base.BaseActivity;

public class RegisterActivity extends BaseActivity{

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
    }
}
