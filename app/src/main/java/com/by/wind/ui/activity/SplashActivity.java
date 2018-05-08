package com.by.wind.ui.activity;

import android.app.Activity;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.by.wind.R;
import com.wind.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Wind on 2017/11/17.
 */

public class SplashActivity extends BaseActivity {

    @BindView(R.id.root_ll)
    LinearLayout mRootLayout;
    @BindView(R.id.SeekBar)
    SeekBar mSeekBar;
    private final int COUNTDOWN_TIME_MILLION = 2000;
    private final int COUNTDOWN_INTERVAL = 19;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) {
            finish();
            return;
        }
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initialView();
    }

    private void initialView() {
        new CountDownTimerUtils(mSeekBar,this,COUNTDOWN_TIME_MILLION,COUNTDOWN_INTERVAL).start();
    }


    public class CountDownTimerUtils extends CountDownTimer {
        private SeekBar seekBar;
        private Context context;
        private int count = 0;

        public CountDownTimerUtils(SeekBar seekBar,Context context
                , long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            this.seekBar = seekBar;
            this.context = context;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            seekBar.setProgress( count++);
            Log.e(TAG,"count:" +count);

        }

        @Override
        public void onFinish() {
            MainActivity.open(context);
        }
    }
}
