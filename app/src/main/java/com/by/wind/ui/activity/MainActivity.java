package com.by.wind.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.by.wind.R;
import com.by.wind.component.net.event.MessageEvent;
import com.by.wind.util.ToastUtil;
import com.by.wind.widget.FragmentPagerAdapter;
import com.by.wind.ui.fragment.CartFragment;
import com.by.wind.ui.fragment.CategoryFragment;
import com.by.wind.ui.fragment.DiscoverFragment;
import com.by.wind.ui.fragment.HomeFragment;
import com.by.wind.ui.fragment.PersonalFragment;
import com.by.wind.widget.NoScrollViewPager;
import com.by.wind.widget.tab.Tab;
import com.by.wind.widget.tab.TabIndicator;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.wind.base.BaseFragment;
import com.by.wind.widget.TitleActivity;

import org.greenrobot.eventbus.EventBus;

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

    private IntentFilter mIntentFilter;
    private NetworkChangeReceiver mNetworkChangeReceiver;


    private int mCurIndex ;
    private final static int HOME_TYPE_TAB = 0;
    private final static int CATEGORY_TYPE_TAB = 1;
    private final static int DISCOVER_TYPE_TAB = 2;
    private final static int CART_TYPE_TAB = 3;
    private final static int PERSONAL_TYPE_TAB = 4;

    public final static int REQUEST_CODE = 1;

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
        initTitle(mCurIndex);
    }

    @Override
    protected void bindViews() {
        ButterKnife.bind(this);
    }

    protected void initializeViews() {
        llCustomTitle.set_show_left_button(false);
        llCustomTitle.set_show_Right_button(true);
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
        tabs.add(new Tab(R.drawable.def_main_tab_message_selector, getResources().getString(R.string.message), null));
        tabs.add(new Tab(R.drawable.def_main_tab_team_selector, getResources().getString(R.string.team), null));
        tabs.add(new Tab(R.drawable.def_main_tab_ship_selector, getString(R.string.ship), null));
        tabs.add(new Tab(R.drawable.def_main_tab_sale_selector, getResources().getString(R.string.sale), null));
        tabs.add(new Tab(R.drawable.def_main_tab_my_selector, getResources().getString(R.string.personal), null));
        mMainIndicator.initializeData(tabs);
        mMainIndicator.setOnTabClickListener(this);
        mMainIndicator.setCurrentTab(mCurIndex);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerNetwork();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mNetworkChangeReceiver);
    }

    @Override
    public void onTabClick(int index) {
        Log.e(TAG,index + "");
        if (index == 0) {
            viewpager.setCurrentItem(HOME_TYPE_TAB, false);
            showTitleContent(getResources().getString(R.string.message));
        } else if (index == 1) {
            viewpager.setCurrentItem(CATEGORY_TYPE_TAB,false);
            showTitleContent(getResources().getString(R.string.team));
        } else if (index == 2) {
            viewpager.setCurrentItem(DISCOVER_TYPE_TAB, false);
            showTitleContent(getResources().getString(R.string.ship));
        } else if (index == 3) {
            viewpager.setCurrentItem(CART_TYPE_TAB, false);
            showTitleContent(getResources().getString(R.string.sale));
        }else if (index == 4) {
            viewpager.setCurrentItem(PERSONAL_TYPE_TAB, false);
            showTitleContent(getResources().getString(R.string.personal));

        }
    }

    private void initTitle(int position) {
    }
    @Override
    public void onRightClick() {
        super.onRightClick();
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    ToastUtil.show( "解析结果:" + result);
                    Log.e("MainActivity",result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtil.show( "解析二维码失败");
                    Log.e("MainActivity","解析二维码失败");
                }
            }
        }
    }


    private void registerNetwork() {
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mNetworkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(mNetworkChangeReceiver, mIntentFilter);
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


    class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            MessageEvent messageEvent;
            if (networkInfo != null && networkInfo.isAvailable()) {
                messageEvent = new MessageEvent(MessageEvent.NETWORK_OK);
            } else {
                messageEvent = new MessageEvent(MessageEvent.NETWORK_FAIL);
            }
            Log.e(TAG,"send message");
            EventBus.getDefault().post(messageEvent);
        }
    }

}
