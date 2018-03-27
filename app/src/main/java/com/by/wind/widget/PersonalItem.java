package com.by.wind.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.by.wind.R;
import com.gc.materialdesign.views.LayoutRipple;

/**
 * Created by Wind on 2017/11/20.
 */

public class PersonalItem extends LayoutRipple {
    private TextView mContentTv;
    private ImageView mRightIconIv;
    private View mBottomLine;
    View view;

    private String tv_text;
    private int tv_text_size;
    private int tv_text_color;
    private int iv_drawable_left_id;
    private int iv_drawable_right_id;
    private boolean line_is;
    private boolean is_enabled_center;


    public PersonalItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomPersonalItemLayout);
        tv_text = typedArray.getString(R.styleable.CustomPersonalItemLayout_tv_text);
        tv_text_size = typedArray.getDimensionPixelSize(R.styleable.CustomPersonalItemLayout_tv_text_size, getResources().getDimensionPixelSize(R.dimen.sp_14));
        tv_text_color = typedArray.getColor(R.styleable.CustomPersonalItemLayout_tv_text_color, getResources().getColor(R.color.black));
        iv_drawable_left_id = typedArray.getResourceId(R.styleable.CustomPersonalItemLayout_tv_drawable_left, R.drawable.icon_setting);
        iv_drawable_right_id = typedArray.getResourceId(R.styleable.CustomPersonalItemLayout_iv_drawable_right, 0);
        is_enabled_center = typedArray.getBoolean(R.styleable.CustomPersonalItemLayout_is_enabled_center, false);
        line_is = typedArray.getBoolean(R.styleable.CustomPersonalItemLayout_line_is, true);
        init(context);
        typedArray.recycle();
    }
    private void init(final Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_personal_item_layout,null);
        mContentTv = (TextView) view.findViewById(R.id.content_tv);
        //mContentTv.setCompoundDrawables(ContextCompat.getDrawable(context,iv_drawable_left_id),null,null,null);
        mRightIconIv = (ImageView) view.findViewById(R.id.right_icon_iv);
        mBottomLine = view.findViewById(R.id.line_item_bottom);
        mContentTv.setText(tv_text);
        mContentTv.setTextColor(tv_text_color);
        if (is_enabled_center) mContentTv.setGravity(Gravity.CENTER);
        mRightIconIv.setImageDrawable(ContextCompat.getDrawable(context,iv_drawable_right_id));
        if (!line_is) mBottomLine.setVisibility(GONE);

        addView(view);

    }
}
