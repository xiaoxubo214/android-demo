package com.by.wind.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.by.wind.R;
import com.by.wind.util.PreferenceHelper;
import com.by.wind.component.event.MessageEvent;
import com.by.wind.ui.activity.LoginActivity;
import com.wind.base.BaseFragment;
import com.wind.base.loading.LoadingDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SplashFragment extends BaseFragment implements View.OnTouchListener {

    @BindView(R.id.root_ll)
    LinearLayout mRootLayout;
    private final static int FINISHED = 100;
    private final static int COUNT_DOWN_TIME = 2500;
    Unbinder unbinder;
    LoadingDialog mLoadingDialog;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == FINISHED) {
                if(mLoadingDialog != null && mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
                    mLoadingDialog = null;
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.SPLASH_FINISH));
                }
            }
        }
    };
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
        mLoadingDialog = new LoadingDialog(getActivity(),false);
        if (mLoadingDialog != null && !mLoadingDialog.isShowing() ) {
            mLoadingDialog.show();
        }
        if (PreferenceHelper.isLogin() == true) {
            handler.sendEmptyMessageDelayed(FINISHED,COUNT_DOWN_TIME);
            //new CountDownTimerUtils(mSeekBar, this.getActivity(), COUNTDOWN_TIME_MILLION, COUNTDOWN_INTERVAL).start();
        } else {
            LoginActivity.open(getActivity());
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }

/*    public class CountDownTimerUtils extends CountDownTimer {
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
    }*/
}
