package com.by.wind.component.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.umeng.message.UmengMessageService;

import org.android.agoo.common.AgooConstants;

public class UmengNotificationService extends UmengMessageService {
    @Override
    public void onMessage(Context context, Intent intent) {
        Log.i("NotificationService", "onMessage");
        String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        Intent intent1 = new Intent();
        intent1.setClass(context, MyNotificationService.class);
        intent1.putExtra("Msg", message);
        context.startService(intent1);
    }
}
