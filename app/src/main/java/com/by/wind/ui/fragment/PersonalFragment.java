package com.by.wind.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.by.wind.Constants;
import com.by.wind.R;
import com.by.wind.ui.activity.LoginActivity;
import com.by.wind.ui.activity.RegisterActivity;
import com.by.wind.ui.activity.WebViewActivity;
import com.by.wind.widget.PersonalItem;

import com.wind.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 */
public class PersonalFragment extends BaseFragment {

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
        return R.layout.fragment_personal;
    }

    @Override
    protected void initAllView(Bundle savedInstanceState) {
        mLlUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewActivity.open(getActivity());
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
                WebViewActivity.open(getActivity());
            }
        });
        mHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewActivity.open(getActivity());
            }
        });

        mAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewActivity.open(getActivity());
            }
        });


        mLoginOutLl.setOnClickListener(view -> LoginActivity.open(context));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
