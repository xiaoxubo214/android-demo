package com.by.wind.widget.tab;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.by.wind.R;

/**
 * Created by wind on 16/11/7.
 */

public class VerticalLineView extends LinearLayout {

    public VerticalLineView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeView();
    }

    public VerticalLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView();
    }

    public VerticalLineView(Context context) {
        super(context);
        initializeView();
    }

    private void initializeView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_vertical_line, this);
    }
}
