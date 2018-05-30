package com.by.wind.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;


import com.by.wind.Constants;
import com.by.wind.R;
import com.by.wind.component.event.MessageEvent;
import com.by.wind.component.service.NotificationBroadcast;
import com.by.wind.ui.fragment.MessageFragment;
import com.by.wind.ui.fragment.MyFragment;
import com.by.wind.ui.fragment.SaleFragment;
import com.by.wind.ui.fragment.ShopFragment;
import com.by.wind.ui.fragment.TeamFragment;
import com.by.wind.util.CustomTitleBar;
import com.by.wind.util.ToastUtil;
import com.by.wind.widget.FragmentPagerAdapter;
import com.by.wind.widget.NoScrollViewPager;
import com.by.wind.widget.tab.Tab;
import com.by.wind.widget.tab.TabIndicator;
import com.umeng.message.PushAgent;
import com.by.wind.component.lib_zxing.activity.CaptureActivity;
import com.by.wind.component.lib_zxing.activity.CodeUtils;
import com.wind.base.BaseFragment;
import com.by.wind.widget.TitleActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends TitleActivity implements TabIndicator.OnTabClickListener  {

    private final static String TAG = "MainActivity";

/*    @BindView(R.id.fl_splash)
    FrameLayout flSplash;*/
    @BindView(R.id.rl_content)
    RelativeLayout mContent;
    @BindView(R.id.viewpager)
    NoScrollViewPager viewpager;
    @BindView(R.id.mMainIndicator)
    TabIndicator mMainIndicator;

    private MessageFragment mMessageFragment;
    private TeamFragment mTeamFragment;
    private ShopFragment mShopFragment;
    private SaleFragment mSaleFragment;
    private MyFragment mMyFragment;
    private ArrayList<BaseFragment> mFragmentList;
    private FragmentPagerAdapter fragmentPagerAdapter;

    private IntentFilter mIntentFilter;
    private NetworkChangeReceiver mNetworkChangeReceiver;


    private int mCurIndex ;
    private final static int MESSAGE_TYPE_TAB = 0;
    private final static int TEAM_TYPE_TAB = 1;
    private final static int SHOP_TYPE_TAB = 2;
    private final static int SALE_TYPE_TAB = 3;
    private final static int MY_TYPE_TAB = 4;

    private String mWebTitleMessage = "";
    private String mWebTitleTeam = "";
    private String mWebTitleShop = "";
    private String mWebTitleSale = "";

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
        PushAgent.getInstance(this).onAppStart();
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

/*        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        SplashFragment splashFragment= new SplashFragment();
        transaction.replace(R.id.fl_splash, splashFragment);
        transaction.commit();*/

        setBackButton(false);
        llCustomTitle.setVisibility(View.VISIBLE);
        llCustomTitle.setIvLeftButtonVisible(false);
        llCustomTitle.setTvLeftButton("返回");
        llCustomTitle.set_show_Right_button(true);
        mMessageFragment = MessageFragment.newInstance(null);
        mTeamFragment = TeamFragment.newInstance(null);
        mShopFragment = ShopFragment.newInstance(null);
        mSaleFragment = SaleFragment.newInstance(null);
        mMyFragment = MyFragment.newInstance(null);

        mFragmentList = new ArrayList<>();
        mFragmentList.add(mMessageFragment);
        mFragmentList.add(mTeamFragment);
        mFragmentList.add(mShopFragment);
        mFragmentList.add(mSaleFragment);
        mFragmentList.add(mMyFragment);

        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        viewpager.setAdapter(fragmentPagerAdapter);
        viewpager.setCurrentItem(mCurIndex);
        viewpager.setOffscreenPageLimit(5);
        viewpager.setPagingEnabled(false);
        viewpager.addOnPageChangeListener(new PageListener());
        ArrayList<Tab> tabs = new ArrayList<>();
        tabs.add(new Tab(R.drawable.def_main_tab_message_selector, getResources().getString(R.string.message), null));
        tabs.add(new Tab(R.drawable.def_main_tab_team_selector, getResources().getString(R.string.team), null));
        tabs.add(new Tab(R.drawable.def_main_tab_shop_selector, getString(R.string.shop), null));
        tabs.add(new Tab(R.drawable.def_main_tab_sale_selector, getResources().getString(R.string.sale), null));
        tabs.add(new Tab(R.drawable.def_main_tab_my_selector, getResources().getString(R.string.personal), null));
        mMainIndicator.initializeData(tabs);
        mMainIndicator.setOnTabClickListener(this);
        mMainIndicator.setCurrentTab(mCurIndex);

        llCustomTitle.setOnTitleClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        registerNetwork();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mNetworkChangeReceiver);
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String messageType = intent.getStringExtra(NotificationBroadcast.EXTRA_KEY_MSG);
        if (messageType != null) {
            mMainIndicator.setCurrentTab(0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

        //控制标题
       if (MessageEvent.SET_TITLE_MESSAGE.equals(event.getEventType())) {
          mWebTitleMessage = event.getMessage();
           showTitleContent(event.getMessage());
       } else if (MessageEvent.SET_TITLE_TEAM.equals(event.getEventType())) {
           mWebTitleTeam = event.getMessage();
           showTitleContent(event.getMessage());
       } else if (MessageEvent.SET_TITLE_SHOP.equals(event.getEventType())) {
           mWebTitleShop = event.getMessage();
           showTitleContent(event.getMessage());
       } else if (MessageEvent.SET_TITLE_SALE.equals(event.getEventType())) {
           mWebTitleSale = event.getMessage();
           showTitleContent(event.getMessage());
       }

        //控制返回菜单和 关闭按钮
        //Log.e("MainActivity",event.getEventType());
        if (MessageEvent.SET_BACK_BUTTON_MESSAGE.equals(event.getEventType()) && (mCurIndex == 0 || mCurIndex == -1)) {
            //Log.e("MainActivity","1111111");
            if(mMessageFragment.getWebView().canGoBack()) {
                //Log.e("MainActivity","can go back");
            }else {
                //Log.e("main","can't go back");
            }
            if (mMessageFragment.getWebView() != null &&mMessageFragment.getWebView().canGoBack()){
                //Log.e("MainActivity","change button display");
                setBackButton(true);
            } else  {
                //Log.e("MainActivity","change button no display");
                setBackButton(false);
            }
        } else if (MessageEvent.SET_BACK_BUTTON_TEAM.equals(event.getEventType()) && mCurIndex == 1) {
            if (mTeamFragment.getWebView() != null && mTeamFragment.getWebView().canGoBack()) {
                setBackButton(true);
            } else  {
                setBackButton(false);
            }
        } else if (MessageEvent.SET_BACK_BUTTON_SHOP.equals(event.getEventType()) && mCurIndex == 2) {
            if (mShopFragment.getWebView() != null && mShopFragment.getWebView().canGoBack()) {
                setBackButton(true);
            } else  {
                setBackButton(false);
            }
        } else if (MessageEvent.SET_BACK_BUTTON_SALE.equals(event.getEventType()) && mCurIndex == 3) {
            if (mSaleFragment.getWebView() != null && mSaleFragment.getWebView().canGoBack()) {
                setBackButton(true);
            } else  {
                setBackButton(false);
            }
        }else if (mCurIndex == 4) {
            setBackButton(false);
        }

    }


    private void setBackButton(boolean display) {
        llCustomTitle.setIvLeftButtonVisible(display);
        llCustomTitle.set_show_left_button(display);
        llCustomTitle.setShow_close_text(display);
    }

    @Override
    public void onTabClick(int index) {
        //Log.e(TAG,index + "");
        if (index == 0) {
            viewpager.setCurrentItem(MESSAGE_TYPE_TAB, false);
            if (mMessageFragment.getWebView() != null &&mMessageFragment.getWebView().canGoBack()){

                showTitleContent(mWebTitleMessage);
                setBackButton(true);
            } else  {
                showTitleContent(getResources().getString(R.string.message));
                setBackButton(false);
            }
        } else if (index == 1) {
            viewpager.setCurrentItem(TEAM_TYPE_TAB,false);
            if (mTeamFragment.getWebView() != null && mTeamFragment.getWebView().canGoBack()) {
                showTitleContent(mWebTitleTeam);
                setBackButton(true);
            } else  {
                showTitleContent(getResources().getString(R.string.team));
                setBackButton(false);
            }
        } else if (index == 2) {
            viewpager.setCurrentItem(SHOP_TYPE_TAB, false);
            if (mShopFragment.getWebView() != null && mShopFragment.getWebView().canGoBack()) {
                showTitleContent(mWebTitleShop);
                setBackButton(true);
            } else  {
                showTitleContent(getResources().getString(R.string.shop));
                setBackButton(false);
            }
        } else if (index == 3) {
            viewpager.setCurrentItem(SALE_TYPE_TAB, false);
            if (mSaleFragment.getWebView() != null && mSaleFragment.getWebView().canGoBack()) {
                showTitleContent(mWebTitleSale);
                setBackButton(true);
            } else  {
                showTitleContent(getResources().getString(R.string.sale));
                setBackButton(false);
            }
        }else if (index == 4) {
            viewpager.setCurrentItem(MY_TYPE_TAB, false);
            showTitleContent(getResources().getString(R.string.personal));

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        String backPage = "";
        if ( keyCode == KeyEvent.KEYCODE_BACK ) {
            if ((mCurIndex == -1 || mCurIndex == 0) && (mMessageFragment.getWebView().canGoBack())){
                backPage = MessageEvent.BACK_MESSAGE;
            } else if (( mCurIndex == 1) && (mTeamFragment.getWebView().canGoBack())) {
                backPage = MessageEvent.BACK_TEAM;
            } else if (( mCurIndex == 2) && (mShopFragment.getWebView().canGoBack())) {
                backPage = MessageEvent.BACK_SHOP;
            } else if (( mCurIndex == 3) && (mSaleFragment.getWebView().canGoBack())) {
                backPage = MessageEvent.BACK_SALE;
            }
            if (!backPage.isEmpty()) {
            EventBus.getDefault().post(new MessageEvent(backPage));
            return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initTitle(int position) {
    }

    @Override
    public void onLeftClick() {
        String backPage = "";
        if ((mCurIndex == -1 || mCurIndex == 0) && (mMessageFragment.getWebView().canGoBack())){
            backPage = MessageEvent.BACK_MESSAGE;
        } else if (( mCurIndex == 1) && (mTeamFragment.getWebView().canGoBack())) {
            backPage = MessageEvent.BACK_TEAM;
        } else if (( mCurIndex == 2) && (mShopFragment.getWebView().canGoBack())) {
            backPage = MessageEvent.BACK_SHOP;
        } else if (( mCurIndex == 3) && (mSaleFragment.getWebView().canGoBack())) {
            backPage = MessageEvent.BACK_SALE;
        }
        if (backPage.isEmpty()) {
            onBackPressed();
        } else {
            EventBus.getDefault().post(new MessageEvent(backPage));
        }
    }

    @Override
    public void onRightClick() {
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onCenterClick() {

    }

    @Override
    public void onCloseClick() {
        Log.e(TAG,"On close");
        String backPage = "";

        if ((mCurIndex == -1 || mCurIndex == 0) && (mMessageFragment.getWebView().canGoBack())){
            backPage = MessageEvent.CLOSE_MESSAGE;
        } else if (( mCurIndex == 1) && (mTeamFragment.getWebView().canGoBack())) {
            backPage = MessageEvent.CLOSE_TEAM;
        } else if (( mCurIndex == 2) && (mShopFragment.getWebView().canGoBack())) {
            backPage = MessageEvent.CLOSE_SHOP;
        } else if (( mCurIndex == 3) && (mSaleFragment.getWebView().canGoBack())) {
            backPage = MessageEvent.CLOSE_SALE;
        }
        if (!backPage.isEmpty()) {
            EventBus.getDefault().post(new MessageEvent(backPage));
        }
        Log.e(TAG,"onc close click");
        new Handler().postDelayed(new Runnable(){
            public void run() {
                setBackButton(false);
            }
        }, 200);

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
                    //ToastUtil.show( "解析结果:" + result);
                    String scanPage = "";
                    //Log.e(TAG,"mCurIndex: " + mCurIndex);
                    if (mCurIndex == -1 || mCurIndex == 0){
                        //scanPage = MessageEvent.SCAN_MESSAGE;
                        mMessageFragment.callJsScan(result);
                    } else if ( mCurIndex == 1 ) {
                        //scanPage = MessageEvent.SCAN_TEAM;
                        mTeamFragment.callJsScan(result);
                    } else if ( mCurIndex == 2 ) {
                        //scanPage = MessageEvent.SCAN_SHOP;
                        mShopFragment.callJsScan(result);
                    } else if ( mCurIndex == 3 ) {
                        //scanPage = MessageEvent.SCAN_SALE;
                        mSaleFragment.callJsScan(result);
                    }
              /*      Log.e("MainActivity","sendScan"+ scanPage +result);
                    EventBus.getDefault().post(new MessageEvent(scanPage,result));
                    Log.e("MainActivity","send finish");*/
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtil.show( "解析二维码失败");
                    //Log.e("MainActivity","解析二维码失败");
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
            String messageType = "";
            if (networkInfo != null && networkInfo.isAvailable()) {
                messageType = MessageEvent.NETWORK_OK;
            } else {
                messageType = MessageEvent.NETWORK_FAIL;
            }
            //Log.e(TAG,"send message");
            EventBus.getDefault().post(new MessageEvent(messageType));
        }
    }

}
