package com.by.wind.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.util.Log;
import android.widget.ImageView;


import com.by.wind.R;
import com.by.wind.component.event.MessageEvent;
import com.by.wind.util.PreferenceHelper;
import com.wind.base.BaseActivity;
import com.wind.base.loading.LoadingDialog;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by christy on 17/4/27.
 */

public class SplashActivity extends BaseActivity {


    private final static int FINISHED = 100;
    private final static int COUNT_DOWN_TIME = 2500;
    LoadingDialog mLoadingDialog;


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == FINISHED) {
                if(mLoadingDialog != null && mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
                    mLoadingDialog = null;
                   MainActivity.open(SplashActivity.this);
                   SplashActivity.this.finish();
                    Log.e(TAG,"Do finished");
                }
            }
        }
    };


    public static void open(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SplashActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isTaskRoot()){finish();return;}
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        setFullScreen(null);
        initialView();
    }

    private void initialView() {
        mLoadingDialog = new LoadingDialog(this,false);
        if (mLoadingDialog != null && !mLoadingDialog.isShowing() ) {
            mLoadingDialog.show();
        }
        if (PreferenceHelper.isLogin() == true) {
            Log.e(TAG,"is login");
            handler.sendEmptyMessageDelayed(FINISHED,COUNT_DOWN_TIME);
            //new CountDownTimerUtils(mSeekBar, this.getActivity(), COUNTDOWN_TIME_MILLION, COUNTDOWN_INTERVAL).start();
        } else {
            LoginActivity.open(this);
        }
    }

}
