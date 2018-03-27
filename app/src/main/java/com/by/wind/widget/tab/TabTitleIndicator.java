package com.by.wind.widget.tab;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.by.wind.R;

import java.util.ArrayList;


public class TabTitleIndicator extends LinearLayout implements OnClickListener {
	private int mTabSize;
	private int mTabIndex = -1;
	private OnTabClickListener listener;
	private LayoutParams mTabParams;
	private final static int ID_PREFIX = 200000;
	private static final int MAX_TAB_SIZE = 5;
	private Context mContext;
	private int mItemWidth;

	public TabTitleIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initializeView();
	}

	public TabTitleIndicator(Context context) {
		super(context);
		mContext = context;
		initializeView();
	}

	public void setOnTabClickListener(OnTabClickListener listener) {
		this.listener = listener;
	}

	public void initializeData(ArrayList<Tab> tabs) {
/*		if (!BussinessUtil.isValidate(tabs)) {
			throw new IllegalArgumentException("the tabs should not be 0");
		}*/
		mTabSize = tabs.size();
		TabTitleView tab = null;
        int screenWidth = getScreenWidth();// 屏幕的宽度
        if(mTabSize > MAX_TAB_SIZE){
        	mItemWidth = (screenWidth / MAX_TAB_SIZE);
        }else{
        	mItemWidth = (screenWidth / mTabSize);
        }
        mTabParams = new LayoutParams(mItemWidth, LayoutParams.WRAP_CONTENT);
        
		for (int i = 0; i < mTabSize; i++) {
			Tab t = tabs.get(i);
			tab = new TabTitleView(getContext());
			tab.setId(ID_PREFIX + i);
			if (t.isCanClickable()) {
				tab.setOnClickListener(this);
			}
			tab.initializeData(t);
			tab.setLayoutParams(mTabParams);
			addView(tab);
		}
	}

	public int getItemWidth(){
		return mItemWidth;
	}
	
	public void removeAllView(){
		this.removeAllViews();
	}
	
	public void onMessageDataChanged(int index, int count) {
		TabTitleView tabView = (TabTitleView) (findViewById(ID_PREFIX + index));
		if(tabView != null){
			tabView.notifyMessageDataChanged(count);
		}
	}

	public void onFlagDataChanged(int index, int count) {
		TabTitleView tabView = (TabTitleView) (findViewById(ID_PREFIX + index));
		if(tabView != null){
			tabView.notifyFlagDataChanged(count);
		}
	}

	public void onDataChanged(int index, int messageCount, int flagCount) {
		TabTitleView tabView = (TabTitleView) (findViewById(ID_PREFIX + index));
		if(tabView != null){
			tabView.notifyDataChaged(messageCount, flagCount);
		}
	}

	public void onTabTitleChanged(int index,String tabText){
		TabTitleView tabView = (TabTitleView) (findViewById(ID_PREFIX + index));
		if(tabView != null){
			tabView.updateTabText(tabText);
		}
	}
	private void initializeView() {
		setOrientation(LinearLayout.HORIZONTAL);
	}

	
	@Override
	public void onClick(View v) {
		int index = v.getId() - ID_PREFIX;
		if (listener != null && mTabIndex != index) {
			v.setSelected(true);
			if (mTabIndex != -1) {
				View old = findViewById(ID_PREFIX + mTabIndex);
				old.setSelected(false);
			}
			mTabIndex = index;
			listener.onTabClick(v.getId() - ID_PREFIX);
		}
	}

	public void updateTabSelect(int index) {
		try {
			if (mTabSize > 0 && mTabSize > index) {
				for (int i = 0; i < mTabSize; i++) {
					if(getChildAt(i) instanceof TabTitleView){
						TabTitleView tabView = (TabTitleView)getChildAt(i);
						if(tabView != null){
							if(index == i){
								tabView.setTextSize(R.dimen.sp_16);
								tabView.setSelected(true);
							}else{
								tabView.setTextSize(R.dimen.sp_14);
								tabView.setSelected(false);
							}
						}
					}else{
						View iView = getChildAt(i);
						if(iView != null){
							if(index == i){
								iView.setSelected(true);
							}else{
								iView.setSelected(false);
							}
						}
					}	
				}
			}
			
			mTabIndex = index;
		} catch (Exception e) {
		}
	}
	
	public int getCheckedTitleLeft(int index) {
		try {
			if (mTabSize > 0 && mTabSize > index) {
				View iView = getChildAt(index);
				return iView.getLeft();
			}
		} catch (Exception e) {
			return 0;
		}
		return 0;
	}
	
	public int getCheckedTitleRight(int index){
		try {
			if (mTabSize > 0 && mTabSize > index) {
				View iView = getChildAt(index);
				return iView.getRight();
			}
		} catch (Exception e) {
			return 0;
		}
		return 0;
	}

	public interface OnTabClickListener {
		void onTabClick(int index);
	}

	public void setCurrentTab(int i) {
		if (i == mTabIndex) {
			return;
		}
		View view = findViewById(ID_PREFIX + i);
		onClick(view);
	}

	/**
	 * 得到手机屏幕的宽度
	 * 
	 * @return
	 */
	private int getScreenWidth() {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) mContext).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		return dm.widthPixels;
	}
	
}
