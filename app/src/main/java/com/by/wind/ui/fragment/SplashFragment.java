package com.by.wind.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.by.wind.R;
import com.by.wind.util.PreferenceHelper;
import com.by.wind.component.net.event.MessageEvent;
import com.by.wind.ui.activity.LoginActivity;
import com.wind.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SplashFragment extends BaseFragment {

    @BindView(R.id.root_ll)
    LinearLayout mRootLayout;
    @BindView(R.id.SeekBar)
    SeekBar mSeekBar;
    private final int COUNTDOWN_TIME_MILLION = 2000;
    private final int COUNTDOWN_INTERVAL = 19;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_splash;
    }

    @Override
    protected void initAllView(Bundle savedInstanceState) {
        if (PreferenceHelper.isLogin() == true) {
            new CountDownTimerUtils(mSeekBar, this.getActivity(), COUNTDOWN_TIME_MILLION, COUNTDOWN_INTERVAL).start();
        } else {
            LoginActivity.open(getActivity());
        }
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

        }

        @Override
        public void onFinish() {
            Log.e("SplashFragment", "send message");
            EventBus.getDefault().post(new MessageEvent(MessageEvent.SPLASH_FINISH));
        }
    }
}
