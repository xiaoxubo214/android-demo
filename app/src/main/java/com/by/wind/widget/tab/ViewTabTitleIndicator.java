package com.by.wind.widget.tab;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;

import com.by.wind.R;

import java.util.ArrayList;

/**
 * Created by christy ic_on 16/11/7.
 */

public class ViewTabTitleIndicator extends RelativeLayout implements TabTitleIndicator.OnTabClickListener {

    private LayoutInflater mInflater;
    private View relativelayout;

    private HorizontalScrollView mTabHorizontalscrollview;
    private TabTitleIndicator mTabTitleIndicator;
    private UnderlinePageIndicator mTabTilteLineIndicator;

    private OnPageSelectedListener mOnPageSelectedListener;

    private ViewPager mViewPager;

    private int mCurrentCheckedTitleLeft;//当前被选中的View距离左侧的距离
    private int mCurrentCheckedTitleRight;

    public ViewTabTitleIndicator(Context context) {
        this(context, null);
    }

    public ViewTabTitleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mInflater = LayoutInflater.from(context);
        relativelayout = mInflater.inflate(R.layout.view_tab_title_indicator, null);
        addView(relativelayout);
        initViews();
    }

    private void initViews() {
        mTabHorizontalscrollview = (HorizontalScrollView) findViewById(R.id.mTabHorizontalscrollview);
        mTabTitleIndicator = (TabTitleIndicator) findViewById(R.id.mTabTitleIndicator);
        mTabTitleIndicator.setOnTabClickListener(this);
        mTabTilteLineIndicator = (UnderlinePageIndicator) findViewById(R.id.mTabIndicatorLine);
        if(Build.VERSION.SDK_INT >= 9){
            mTabHorizontalscrollview.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
        if (mTabTilteLineIndicator != null) {
            mTabTilteLineIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    if(mOnPageSelectedListener != null){
                        mOnPageSelectedListener.onPageSelected(position);
                    }

                    updateTabTitleIndicator(position);
                    if(mTabTitleIndicator != null){
                        mCurrentCheckedTitleLeft = mTabTitleIndicator.getCheckedTitleLeft(position);
                        mCurrentCheckedTitleRight = mTabTitleIndicator.getCheckedTitleRight(position);
                        mTabHorizontalscrollview.smoothScrollTo((int)mCurrentCheckedTitleLeft - (int)getResources().getDimension(R.dimen.title_move_left), 0);
                        mTabTilteLineIndicator.moveToRight(mCurrentCheckedTitleLeft,mCurrentCheckedTitleRight);
                    }

                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {
                }

                @Override
                public void onPageScrollStateChanged(int arg0) {
                }
            });
        }
    }

    public void initializeData(ArrayList<Tab> tabs, ViewPager pager) {
        initializeData(tabs, pager, 0);
    }

    public void initializeData(ArrayList<Tab> tabs, ViewPager pager, int position) {
        if (mTabTitleIndicator != null && mTabTilteLineIndicator != null) {
            mViewPager = pager;
            mTabTitleIndicator.initializeData(tabs);
            mTabTilteLineIndicator.setViewPager(mViewPager);
            mTabTilteLineIndicator.setFades(false);
            int itemWidth = mTabTitleIndicator.getItemWidth();
            mTabTilteLineIndicator.moveToRight(position * itemWidth ,itemWidth * (position +1));
            mTabTilteLineIndicator.invalidate();
            updateTabTitleIndicator(position);
        }
    }

    public void clearAllTabTitle(){
        if(mTabTitleIndicator != null){
            mTabTitleIndicator.removeAllView();
        }
    }
    @Override
    public void onTabClick(int index) {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(index);
        }
    }

    public void setOnTabClickListener(TabTitleIndicator.OnTabClickListener listener){
        if (listener != null){
            mTabTitleIndicator.setOnTabClickListener(listener);
        }
    }

    public void updateTabTitleIndicator(int current) {
        if (mTabTitleIndicator != null) {
            mTabTitleIndicator.updateTabSelect(current);
        }
    }

    public void onMessageDataChanged(int index, int count) {
        if (mTabTitleIndicator != null) {
            mTabTitleIndicator.onMessageDataChanged(index, count);
        }
    }

    public void onFlagDataChanged(int index, int count) {
        if (mTabTitleIndicator != null) {
            mTabTitleIndicator.onFlagDataChanged(index, count);
        }
    }

    public void onDataChanged(int index, int messageCount, int flagCount) {
        if (mTabTitleIndicator != null) {
            mTabTitleIndicator.onDataChanged(index, messageCount, flagCount);
        }
    }

    public void setOnPageSelectedListener(OnPageSelectedListener listener){
        this.mOnPageSelectedListener = listener;
    }

    public interface OnPageSelectedListener {
        void onPageSelected(int position);
    }
}
