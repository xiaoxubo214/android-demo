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
 * Created by wind ic_on 16/11/7.
 */

public class TabView extends RelativeLayout {

    private TextView tvTabInfoLabel;
    private ImageView ivTabIconImg;
    private ImageView ivTabFlag;
    private TextView tvTabCount;

    public TabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeView();
    }

    public TabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView();
    }

    public TabView(Context context) {
        super(context);
        initializeView();
    }

    private void initializeView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_tab, this);
        ivTabIconImg = (ImageView) findViewById(R.id.ivTabIconImg);
        tvTabInfoLabel = (TextView) findViewById(R.id.tvTabInfoLabel);
        ivTabFlag = (ImageView) findViewById(R.id.ivTabFlag);
        tvTabCount = (TextView) findViewById(R.id.tvTabCount);
    }

    public void initializeData(Tab tab) {
        ivTabIconImg.setImageResource(tab.getIconResId());
        if (!tab.getInfoText().isEmpty()) {
            tvTabInfoLabel.setVisibility(View.VISIBLE);
            tvTabInfoLabel.setText(tab.getInfoText());
        } else {
            tvTabInfoLabel.setVisibility(View.GONE);
        }
        updateCount(tab.getMessageCount(), tab.getFlagCount());
    }

    public void notifyFlagDataChanged(int count) {
        updateCount(0, count);
    }

    public void notifyMessageDataChanged(int count) {
        updateCount(count, 0);
    }

    public void notifyDataChaged(int messageCount, int flagCount) {
        updateCount(messageCount, flagCount);
    }

    private void updateCount(int messageCount, int flagCount) {
        if (messageCount > 0) {
            if (messageCount > 99) {
                tvTabCount.setText("99+");
            } else {
                tvTabCount.setText(String.valueOf(messageCount));
            }
            tvTabCount.setVisibility(View.VISIBLE);

            ivTabFlag.setVisibility(View.GONE);
        } else {
            tvTabCount.setText("");
            tvTabCount.setVisibility(View.GONE);
            if (flagCount > 0) {
                ivTabFlag.setVisibility(View.VISIBLE);
            } else {
                ivTabFlag.setVisibility(View.GONE);
            }
        }
    }

}
