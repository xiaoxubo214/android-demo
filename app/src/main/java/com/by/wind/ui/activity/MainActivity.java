package com.by.wind.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.by.wind.R;
import com.by.wind.widget.FragmentPagerAdapter;
import com.by.wind.ui.fragment.CartFragment;
import com.by.wind.ui.fragment.CategoryFragment;
import com.by.wind.ui.fragment.DiscoverFragment;
import com.by.wind.ui.fragment.HomeFragment;
import com.by.wind.ui.fragment.PersonalFragment;
import com.by.wind.widget.NoScrollViewPager;
import com.by.wind.widget.tab.Tab;
import com.by.wind.widget.tab.TabIndicator;
import com.wind.base.BaseFragment;
import com.by.wind.widget.TitleActivity;

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
    private HomeFragment mHomeFragment;
    private CategoryFragment mCategoryFragment;
    private DiscoverFragment mDiscoverFragment;
    private CartFragment mCartFragment;
    private PersonalFragment mPersonalFragment;
    private ArrayList<BaseFragment> mFragmentList;
    private FragmentPagerAdapter fragmentPagerAdapter;

    private int mCurIndex;
    private static final int HOME_TYPE_TAB = 0;
    private static final int CATEGORY_TYPE_TAB = 1;
    private static final int DISCOVER_TYPE_TAB = 2;
    private static final int CART_TYPE_TAB = 3;
    private static final int PERSONAL_TYPE_TAB = 4;

    public static void open(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initializeData() {
        initializeViews();
    }

    @Override
    protected void bindViews() {
        ButterKnife.bind(this);
    }

    protected void initializeViews() {
        mHomeFragment = new HomeFragment();
        mCategoryFragment = new CategoryFragment();
        mDiscoverFragment = new DiscoverFragment();
        mCartFragment = new CartFragment();
        mPersonalFragment = new PersonalFragment();

        mFragmentList = new ArrayList<>();
        mFragmentList.add(mHomeFragment);
        mFragmentList.add(mCategoryFragment);
        mFragmentList.add(mDiscoverFragment);
        mFragmentList.add(mCartFragment);
        mFragmentList.add(mPersonalFragment);

        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        viewpager.setAdapter(fragmentPagerAdapter);
        viewpager.setCurrentItem(mCurIndex);
        viewpager.setOffscreenPageLimit(5);
        viewpager.setPagingEnabled(false);
        viewpager.addOnPageChangeListener(new PageListener());
        ArrayList<Tab> tabs = new ArrayList<>();
        tabs.add(new Tab(R.drawable.def_main_tab_message_selector, getResources().getString(R.string.home), null));
        tabs.add(new Tab(R.drawable.def_main_tab_team_selector, getResources().getString(R.string.category), null));
        tabs.add(new Tab(R.drawable.def_main_tab_ship_selector, getString(R.string.discover), null));
        tabs.add(new Tab(R.drawable.def_main_tab_sale_selector, getResources().getString(R.string.cart), null));
        tabs.add(new Tab(R.drawable.def_main_tab_my_selector, getResources().getString(R.string.personal), null));
        mMainIndicator.initializeData(tabs);
        mMainIndicator.setOnTabClickListener(this);
        mMainIndicator.setCurrentTab(mCurIndex);
    }

    @Override
    public void onTabClick(int index) {
        if (index == 0) {
            viewpager.setCurrentItem(HOME_TYPE_TAB, false);
        } else if (index == 1) {
            viewpager.setCurrentItem(CATEGORY_TYPE_TAB,false);
        } else if (index == 2) {
            viewpager.setCurrentItem(DISCOVER_TYPE_TAB, false);
        } else if (index == 3) {
            viewpager.setCurrentItem(CART_TYPE_TAB, false);
        }else if (index == 4) {
            viewpager.setCurrentItem(PERSONAL_TYPE_TAB, false);

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
