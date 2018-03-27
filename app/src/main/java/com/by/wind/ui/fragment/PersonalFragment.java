package com.by.wind.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.by.wind.R;
import com.by.wind.ui.activity.LoginActivity;
import com.by.wind.base.BaseFragment;
import com.by.wind.widget.PersonalItem;
import com.by.wind.ui.activity.LoginActivity;
import com.by.wind.widget.PersonalItem;
import com.gc.materialdesign.views.ButtonRectangle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 */
public class PersonalFragment extends BaseFragment {

    @BindView(R.id.user_avatar_iv)
    ImageView mUserAvatarIv;
    @BindView(R.id.user_name_tv)
    TextView mUserNameTv;
    @BindView(R.id.user_rl)
    RelativeLayout mUserRl;
    @BindView(R.id.login_out_btn)
    ButtonRectangle mLoginOutBtn;
    Unbinder unbinder;
    @BindView(R.id.pl_item_setting)
    PersonalItem mPlItemSetting;

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
        mUserRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mPlItemSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mLoginOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.open(context);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
