package com.by.wind.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.by.wind.R;
import com.by.wind.util.SharedPreferences;
import com.by.wind.base.BaseActivity;
import com.by.wind.util.SharedPreferences;

import java.lang.ref.WeakReference;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Wind on 2017/11/17.
 */

public class SplashActivity extends BaseActivity {

    @BindView(R.id.mImageView)
    ImageView mImageView;

    private SplashHandler mSplashHandler;

    private static final int STEP_COVER_WHAT = 1;
    private static final int STEP_NEXT_REQUEST = 2;

    private static final long DEFAULT_SHOWTIME = 500;


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
        mSplashHandler = new SplashHandler(this);
        mSplashHandler.sendEmptyMessageDelayed(STEP_COVER_WHAT, DEFAULT_SHOWTIME / 2);
    }

    private static class SplashHandler extends Handler {

        private final WeakReference<SplashActivity> mActivity;

        public SplashHandler(SplashActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mActivity.get() == null || mActivity.get().isFinishing()) {
                return;
            }

            switch (msg.what) {
                case STEP_NEXT_REQUEST:
                    mActivity.get().showNext();
                    break;
                case STEP_COVER_WHAT:
                    mActivity.get().showImageCoverAnimation();
                    break;
            }
        }
    }

    private void showImageCoverAnimation() {

        AlphaAnimation showAnimation = new AlphaAnimation(0.3f, 1f);
        showAnimation.setDuration(DEFAULT_SHOWTIME);
        showAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mSplashHandler.sendEmptyMessageDelayed(STEP_NEXT_REQUEST, DEFAULT_SHOWTIME);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mImageView.setVisibility(View.VISIBLE);
        mImageView.startAnimation(showAnimation);
    }

    private void showNext() {
        if (SharedPreferences.getInstance().getBoolean("isLogin", true)) {
            MainActivity.open(this);
        } else {
            LoginActivity.open(this);
        }
        finish();
    }
}
