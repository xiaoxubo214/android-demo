package com.by.wind;

import android.content.Context;
import android.content.Intent;

import com.by.wind.ui.activity.SplashActivity;
import com.umeng.message.meizu.UmengMeizuPushReceiver;

public class MeizuReceiver extends UmengMeizuPushReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Intent startIntent = new Intent(context,SplashActivity.class);
        startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(startIntent);
    }
}
