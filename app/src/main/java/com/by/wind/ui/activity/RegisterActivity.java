package com.by.wind.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.by.wind.Constants;
import com.by.wind.R;
import com.by.wind.presenter.RegisterPresenterImpl;
import com.by.wind.util.StringUtil;
import com.by.wind.util.ToastUtil;
import com.by.wind.view.IBaseView;
import com.wind.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 */

public class RegisterActivity extends BaseActivity implements IBaseView.IRegisterView {


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
    Button returnBtn;
    @BindView(R.id.submit_btn)
    Button submitBtn;
    RegisterPresenterImpl mRegisterPresenterImpl;

    public static String ACTION_TYPE = "";

    private String code = "0000";

    public static void open(Context context,String startType) {
        if (startType.equals(Constants.START_ACTIVITY_REGISTER)) {
            ACTION_TYPE = Constants.START_ACTIVITY_REGISTER;
        } else {
            ACTION_TYPE = Constants.START_ACTIVITY_FORGET;
        }
        Intent intent = new Intent();
        intent.setClass(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        ButterKnife.bind(this);
        setFullScreen(null);
        initialView();
    }

    private void initialView() {
        mRegisterPresenterImpl = new RegisterPresenterImpl(this);
    }

    @OnClick({R.id.tvVerifyCode, R.id.return_btn, R.id.submit_btn})
    public void OnBindClick(View view) {
        switch (view.getId()) {
            case R.id.tvVerifyCode:
                if (StringUtil.isMobile(etUserNum.getText().toString())) {
                    mRegisterPresenterImpl.getCheckCode(etUserNum.getText().toString(), this, lifecycleSubject);
                }
                break;
            case R.id.submit_btn:
                if (!StringUtil.isMobile(etUserNum.getText().toString())) {
                    ToastUtil.show("请输入正确的手机号码");
                    return;
                }
                if (etVerifyCode.getText().toString() == null || etVerifyCode.getText().toString().length() < 4) {
                    ToastUtil.show("验证码长度大于等于4");
                    return;
                }
                if ( !etVerifyCode.getText().toString().equals(code)) {
                    ToastUtil.show("验证码错误");
                    return;
                }

                if (etNewPwd.getText().toString().length()< 6 || etConfirmPwd.getText().toString().length()< 6 ) {
                    ToastUtil.show("密码长度必须大于等于 6");
                    return;
                }
                if (!etNewPwd.getText().toString().equals(etConfirmPwd.getText().toString())) {
                    ToastUtil.show("2 次输入密码不一致");
                    return;
                }
                if (ACTION_TYPE == Constants.START_ACTIVITY_REGISTER) {
                    mRegisterPresenterImpl.doRegister(this,lifecycleSubject);
                } else {
                    mRegisterPresenterImpl.doForgetPwd(this, lifecycleSubject);
                }
                break;
            case R.id.return_btn:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void doForgetPwd(int retCode) {
        if (retCode == 200 || Constants.isDebug == true) {
            ToastUtil.showToast("密码修改成功");
            finish();
        }

    }

    @Override
    public void getCheckCode(int retCode) {
        if (retCode == 200 || Constants.isDebug == true) {
            new CountDownTimerUtils(tvVerifyCode,30 * 1000, 1000).start();
            etNewPwd.requestFocus();
        }
    }

    @Override
    public void doRegister(int retCode) {

    }


    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String err) {

    }

    public class CountDownTimerUtils extends CountDownTimer {
        private TextView mTextView;

        /**
         * @param textView          The TextView
         *
         *
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receiver
         *                          {@link #onTick(long)} callbacks.
         */
        public CountDownTimerUtils(TextView textView, long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            this.mTextView = textView;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mTextView.setClickable(false); //设置不可点击
            mTextView.setText(millisUntilFinished / 1000 + "秒后可重新发送");  //设置倒计时时间
            mTextView.setBackgroundResource(R.color.btn_red); //设置按钮为灰色，这时是不能点击的

            /**
             * 超链接 URLSpan
             * 文字背景颜色 BackgroundColorSpan
             * 文字颜色 ForegroundColorSpan
             * 字体大小 AbsoluteSizeSpan
             * 粗体、斜体 StyleSpan
             * 删除线 StrikethroughSpan
             * 下划线 UnderlineSpan
             * 图片 ImageSpan
             * http://blog.csdn.net/ah200614435/article/details/7914459
             */
            SpannableString spannableString = new SpannableString(mTextView.getText().toString());  //获取按钮上的文字
            ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
            /**
             * public void setSpan(Object what, int start, int end, int flags) {
             * 主要是start跟end，start是起始位置,无论中英文，都算一个。
             * 从0开始计算起。end是结束位置，所以处理的文字，包含开始位置，但不包含结束位置。
             */
            spannableString.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//将倒计时的时间设置为红色
            mTextView.setText(spannableString);
        }

        @Override
        public void onFinish() {
            mTextView.setText("重新获取验证码");
            mTextView.setClickable(true);//重新获得点击
            mTextView.setBackgroundResource(R.color.white);  //还原背景色
        }
    }
}
