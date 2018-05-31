package com.by.wind.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.by.wind.Constants;
import com.by.wind.R;
import com.by.wind.entity.UserInfo;
import com.by.wind.presenter.IBasePresenter;
import com.by.wind.presenter.UserInfoPresenter;
import com.by.wind.util.PreferenceHelper;
import com.by.wind.ui.activity.LoginActivity;
import com.by.wind.ui.activity.RegisterActivity;
import com.by.wind.ui.activity.WebViewActivity;
import com.by.wind.util.ToastUtil;
import com.by.wind.util.img.ImagePicker;
import com.by.wind.view.IBaseView;
import com.by.wind.widget.PersonalItem;

import com.wind.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 */
public class MyFragment extends BaseFragment implements IBaseView.IUserInfoView{

    @BindView(R.id.user_avatar_iv)
    ImageView mUserAvatar;
    @BindView(R.id.user_name_tv)
    TextView mUserName;
    @BindView(R.id.user_phone_tv)
    TextView mUserPhone;
    @BindView(R.id.ll_user_info)
    LinearLayout mLlUserInfo;
    @BindView(R.id.pl_chnage_pwd)
    PersonalItem mChangePwd;
    @BindView(R.id.pl_common)
    PersonalItem mCommon;
    @BindView(R.id.pl_help)
    PersonalItem mHelp;
    @BindView(R.id.pl_about)
    PersonalItem mAbout;
    @BindView(R.id.login_out_btn)
    LinearLayout mLoginOutLl;
    Unbinder unbinder;

    IBasePresenter.IUserInfoPresenter mUserInfoPresenter;

    public static MyFragment newInstance(String content){
        MyFragment f = new MyFragment();
        if (content != null) {
            final Bundle args = new Bundle();
            args.putString("content", content);
            f.setArguments(args);
        }
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

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
        return R.layout.fragment_my;
    }

    @Override
    protected void initAllView(Bundle savedInstanceState) {
        setUserInfo();
        mLlUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewActivity.open(getActivity(),Constants.PAGE_USER,"用户信息");
            }
        });
        mChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.open(getActivity(), Constants.START_ACTIVITY_FORGET);
            }
        });
        mCommon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewActivity.open(getActivity(), Constants.PAGE_COMMON, "通用");
            }
        });
        mHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewActivity.open(getActivity(),Constants.PAGE_HELP, "帮助与反馈");
            }
        });

        mAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewActivity.open(getActivity(),Constants.PAGE_ABOUT, "关于YIHI商户通");
            }
        });


        mLoginOutLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.open(getActivity());
                PreferenceHelper.clearCache();
                getActivity().finish();
                ToastUtil.show("退出成功");
            }
        });
        mUserInfoPresenter = new UserInfoPresenter(this,getActivity());
        mUserInfoPresenter.getUserInfo(getContext(),lifecycleSubject);
    }
    private void setUserInfo() {

        UserInfo userInfo = PreferenceHelper.getUserInfo();
        if (userInfo != null) {
            if (userInfo.head_image != null && !userInfo.head_image.isEmpty()) {
                if (userInfo.head_image != null && mUserAvatar != null) {
                    ImagePicker.getInstance().getImageLoader().displayCropImage(getContext(), userInfo.head_image, mUserAvatar, R.drawable.my_default_img);
                }
            }
            mUserName.setText(userInfo.member_name);
            mUserPhone.setText(userInfo.phone_h);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showResult(int result) {
        setUserInfo();
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
}
