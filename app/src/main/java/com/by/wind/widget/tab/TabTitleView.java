package com.by.wind.widget.tab;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.by.wind.R;


/**
 * Created by christy ic_on 16/11/7.
 */

public class TabTitleView extends RelativeLayout {

	private TextView mTabInfoLabel;
	private ImageView mTabIconImg;
	private ImageView mTabFlagIV;
	private TextView mTabCountTV;
	private View mTabLine;

	public TabTitleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initializeView();
	}

	public TabTitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initializeView();
	}

	public TabTitleView(Context context) {
		super(context);
		initializeView();
	}

	private void initializeView() {
		LayoutInflater.from(getContext()).inflate(R.layout.view_tab_title, this);
		mTabIconImg = (ImageView) findViewById(R.id.mTabIconImg);
		mTabInfoLabel = (TextView) findViewById(R.id.mTabInfoLabel);
		mTabFlagIV = (ImageView) findViewById(R.id.mTabFlagIV);
		mTabCountTV = (TextView) findViewById(R.id.mTabCountTV);
		mTabLine = findViewById(R.id.mTabIndicatorLine);
	}

	public void initializeData(Tab tab) {
		if(tab.getIconResId() > 0){
			mTabIconImg.setVisibility(View.VISIBLE);
			mTabIconImg.setImageResource(tab.getIconResId());
		}else{
			mTabIconImg.setVisibility(View.GONE);
		}

		if(!tab.getInfoText().isEmpty()){
			mTabInfoLabel.setVisibility(View.VISIBLE);
			mTabInfoLabel.setText(tab.getInfoText());
		}else{
			mTabInfoLabel.setVisibility(View.GONE);
		}


		updateCount(tab.getMessageCount(), tab.getFlagCount());
	}

	public void setTextSize(int resid){
		if(mTabInfoLabel != null){
			float f = getResources().getDimension(resid);
		    float density = getResources().getDisplayMetrics().density;
			mTabInfoLabel.setTextSize(f / density);
		}
	}
	public void notifyFlagDataChanged(int count) {
		updateCount(0, count);
	}
	
	public void notifyMessageDataChanged(int count) {
		updateCount(count, 0);
	}
	
	public void notifyDataChaged(int messageCount, int flagCount){
		updateCount(messageCount, flagCount);
	}

	public void updateTabText(String text){
		if(mTabInfoLabel != null){
			mTabInfoLabel.setText(text);
		}
	}
	private void updateCount(int messageCount, int flagCount) {
		if (messageCount > 0) {
			if(messageCount > 99){
				mTabCountTV.setText("99+");
			}else{
				mTabCountTV.setText(String.valueOf(messageCount));
			}
			mTabCountTV.setVisibility(View.VISIBLE);

			mTabFlagIV.setVisibility(View.GONE);
		} else {
			mTabCountTV.setText("");
			mTabCountTV.setVisibility(View.GONE);

			if (flagCount > 0) {
				mTabFlagIV.setVisibility(View.VISIBLE);
			} else {
				mTabFlagIV.setVisibility(View.GONE);
			}
		}
	}

}
