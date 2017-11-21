package com.by.wind.demo.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Wind on 2017/11/15.
 */

public class NoScrollViewPager extends ViewPager {

    public NoScrollViewPager(Context paramContext) {
        this(paramContext, null);
    }

    public NoScrollViewPager(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        this.isPagingEnabled = true;
    }

    protected boolean canScroll(View paramView, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3) {
        return !(((paramView instanceof ViewPager)) && (paramView != this)) && super.canScroll(paramView, paramBoolean, paramInt1, paramInt2, paramInt3);
    }

    private boolean isPagingEnabled;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            if (isPagingEnabled) {
                return super.onTouchEvent(event);
            }
        }catch (IllegalArgumentException ignored){
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        try {
            if (isPagingEnabled) {
                return super.onInterceptTouchEvent(event);
            }
        }catch (IllegalArgumentException ignored){

        }
        return false;
    }

    public void setPagingEnabled(boolean b) {
        this.isPagingEnabled = b;
    }
}
