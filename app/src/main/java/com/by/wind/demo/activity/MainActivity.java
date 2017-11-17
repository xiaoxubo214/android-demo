package com.by.wind.demo.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.by.wind.demo.R;
import com.by.wind.demo.adapter.FragmentPagerAdapter;
import com.by.wind.demo.fragment.FourFragment;
import com.by.wind.demo.fragment.OneFragment;
import com.by.wind.demo.fragment.ThreeFragment;
import com.by.wind.demo.fragment.TwoFragment;
import com.by.wind.demo.view.BaseFragment;
import com.by.wind.demo.view.NoScrollViewPager;
import com.by.wind.demo.view.TitleActivity;
import com.by.wind.demo.view.tab.Tab;
import com.by.wind.demo.view.tab.TabIndicator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends TitleActivity implements TabIndicator.OnTabClickListener {

    @BindView(R.id.viewpager)
    NoScrollViewPager viewpager;
    @BindView(R.id.mMainIndicator)
    TabIndicator mMainIndicator;
    @BindView(R.id.llMainLayout)
    LinearLayout llMainLayout;
    @BindView(R.id.ivCommonImg)
    ImageView ivCommonImg;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;
    private OneFragment mOneFragment;
    private TwoFragment mTwoFragment;
    private ThreeFragment mThreeFragment;
    private FourFragment mFourFragment;
    private ArrayList<BaseFragment> mFragmentList;
    private FragmentPagerAdapter fragmentPagerAdapter;

    private int mCurIndex;
    private static final int ONE_TYPE_TAB = 0;
    private static final int TWO_TYPE_TAB = 1;
    private static final int THREE_TYPE_TAB = 2;
    private static final int FOUR_TYPE_TAB = 3;

    public static void open(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initializeViews();
    }



    protected void initializeViews() {
        mOneFragment = new OneFragment();
        mTwoFragment = new TwoFragment();
        mThreeFragment = new ThreeFragment();
        mFourFragment = new FourFragment();

        mFragmentList = new ArrayList<>();
        mFragmentList.add(mOneFragment);
        mFragmentList.add(mTwoFragment);
        mFragmentList.add(mThreeFragment);
        mFragmentList.add(mFourFragment);

        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        viewpager.setAdapter(fragmentPagerAdapter);
        viewpager.setCurrentItem(mCurIndex);
        viewpager.setOffscreenPageLimit(4);
        viewpager.setPagingEnabled(false);
        viewpager.addOnPageChangeListener(new PageListener());
        ArrayList<Tab> tabs = new ArrayList<>();
        tabs.add(new Tab(R.drawable.def_main_tab_home_selector, getResources().getString(R.string.one), null));
        tabs.add(new Tab(R.drawable.def_main_tab_category_selector, getResources().getString(R.string.two), null));
        tabs.add(new Tab(R.color.transparent, "", false, null));
        tabs.add(new Tab(R.drawable.def_main_tab_cart_selector, getResources().getString(R.string.two), null));
        tabs.add(new Tab(R.drawable.def_main_tab_personal_selector, getResources().getString(R.string.two), null));
        mMainIndicator.initializeData(tabs);
        mMainIndicator.setOnTabClickListener(this);
        mMainIndicator.setCurrentTab(mCurIndex);
    }

    @Override
    public void onTabClick(int index) {
        if (index == 0) {
            viewpager.setCurrentItem(ONE_TYPE_TAB, false);
        } else if (index == 1) {
            //ToastUtil.showToast("该功能正努力开通中，暂未开放");
            viewpager.setCurrentItem(TWO_TYPE_TAB, false);
        } else if (index == 3) {
            viewpager.setCurrentItem(THREE_TYPE_TAB, false);
        } else if (index == 4) {
            viewpager.setCurrentItem(THREE_TYPE_TAB, false);

        }
    }

    @OnClick({R.id.viewpager, R.id.llMainLayout, R.id.ivCommonImg, R.id.activity_main})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.viewpager:
                break;
            case R.id.llMainLayout:
                break;
            case R.id.ivCommonImg:
                break;
            case R.id.activity_main:
                break;
        }
    }

    public class PageListener implements ViewPager.OnPageChangeListener {
        public PageListener() {
        }

        public void onPageScrollStateChanged(int paramInt) {
        }

        public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2) {
        }

        public void onPageSelected(int position) {
            mCurIndex = position;
        }
    }

}
