package com.by.wind.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import com.by.wind.R;
import com.gc.materialdesign.views.ButtonRectangle;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.wind.base.BaseActivity;
import com.wind.base.permission.HiPermission;
import com.wind.base.permission.PermissionCallback;
import com.wind.base.permission.PermissionItem;

import java.util.ArrayList;
import java.util.List;

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
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        SystemClock.sleep(5000);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.submit_btn)
    public void onViewClicked() {
        Log.e(TAG,"onClick start");
        List<PermissionItem> permissionItems = new ArrayList<PermissionItem>();
        permissionItems.add(new PermissionItem(Manifest.permission.CAMERA, "照相机", R.drawable.permission_ic_camera));
        permissionItems.add(new PermissionItem(Manifest.permission.ACCESS_FINE_LOCATION, "定位", R.drawable.permission_ic_location));
        HiPermission.create(this).permissions(permissionItems).checkMutiPermission(new PermissionCallback() {
            @Override
            public void onClose() {
                Log.e(TAG,"on close");
            }

            @Override
            public void onFinish() {
                Log.e(TAG,"onFinish");
            }

            @Override
            public void onDeny(String permission, int position) {
                Log.e(TAG,"onDeny");
            }

            @Override
            public void onGuarantee(String permission, int position) {
                Log.e(TAG,"onGuarantee");
            }
        });
        //MainActivity.open(this);
    }
}
