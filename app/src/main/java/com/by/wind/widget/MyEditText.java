package com.by.wind.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.by.wind.R;

@SuppressLint("AppCompatCustomView")
public class MyEditText extends EditText {
    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackground(getResources().getDrawable(R.drawable.edit_text));
    }
}
