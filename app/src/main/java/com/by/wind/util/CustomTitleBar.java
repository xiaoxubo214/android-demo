package com.by.wind.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.by.wind.R;
import butterknife.BindView;
import butterknife.ButterKnife;


public class CustomTitleBar extends LinearLayout {

    @BindView(R.id.ivLeftButton)
    ImageView ivLeftButton;
    @BindView(R.id.tvLeftButton)
    TextView tvLeftButton;
    @BindView(R.id.tvClose)
    TextView tvCloseText;
    @BindView(R.id.llLeftLayout)
    LinearLayout llLeftLayout;
    @BindView(R.id.tvCenterLeftButton)
    TextView tvCenterLeftButton;
    @BindView(R.id.tvCenterRightButton)
    TextView tvCenterRightButton;
    @BindView(R.id.llCenterLayout)
    LinearLayout llCenterLayout;
    @BindView(R.id.ivRightButton)
    ImageView ivRightButton;
    @BindView(R.id.tvRightButton)
    TextView tvRightButton;
    @BindView(R.id.llRightLayout)
    LinearLayout llRightLayout;
    @BindView(R.id.rlTitleLayout)
    RelativeLayout rlTitleLayout;
    @BindView(R.id.bottom_line_view)
    View bottom_line_view;

    private LayoutInflater mInflater;
    private View linearLayout;

    private int title_background_color;
    private String title_text;
    private int title_textColor;
    private int title_textSize;
    private int left_button_imageId;
    private String left_button_text;
    private int left_button_textColor;
    private int left_button_textSize;
    private boolean show_left_button;
    private int right_button_imageId;
    private String right_button_text;
    private int right_button_textColor;
    private int right_button_textSize;
    private boolean show_right_button;
    private boolean show_left_center_button;
    private boolean show_right_center_button;
    private boolean show_bottom_line_view;
    private String right_center_title_text;
    private static final String TAG = CustomTitleBar.class.getSimpleName();

    /**
     * 标题的点击事件
     */
    private TitleOnClickListener titleOnClickListener;

    public CustomTitleBar(Context context) {
        super(context);
        init(context);
    }

    public CustomTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleBar);

        /**标题相关*/
        //title_background_color = typeArray.getColor(R.styleable.CustomTitleBar_title_background, Color.WHITE);
        title_text = typeArray.getString(R.styleable.CustomTitleBar_title_text);
        title_textColor = typeArray.getColor(R.styleable.CustomTitleBar_title_textColor, Color.BLACK);
        title_textSize = typeArray.getDimensionPixelSize(R.styleable.CustomTitleBar_title_textSize, 20);

        /**返回按钮相关*/
        left_button_imageId = typeArray.getResourceId(R.styleable.CustomTitleBar_left_button_image, R.drawable.icon_return_back);
        left_button_text = typeArray.getString(R.styleable.CustomTitleBar_left_button_text);
        left_button_textColor = typeArray.getColor(R.styleable.CustomTitleBar_left_button_textColor, Color.BLACK);
        left_button_textSize = typeArray.getDimensionPixelSize(R.styleable.CustomTitleBar_left_button_textSize, 20);
        show_left_button = typeArray.getBoolean(R.styleable.CustomTitleBar_show_left_button, true);

        /**右边保存按钮相关*/
        right_button_imageId = typeArray.getResourceId(R.styleable.CustomTitleBar_right_button_image, 0);
        right_button_text = typeArray.getString(R.styleable.CustomTitleBar_right_button_text);
        right_button_textColor = typeArray.getColor(R.styleable.CustomTitleBar_right_button_textColor, Color.WHITE);
        right_button_textSize = typeArray.getDimensionPixelSize(R.styleable.CustomTitleBar_right_button_textSize, 20);
        show_right_button = typeArray.getBoolean(R.styleable.CustomTitleBar_show_right_button, false);

        /** 中间相关按钮*/
        show_left_center_button = typeArray.getBoolean(R.styleable.CustomTitleBar_show_left_center_button,true);
        show_right_center_button = typeArray.getBoolean(R.styleable.CustomTitleBar_show_right_center_button,false);
        right_center_title_text = typeArray.getString(R.styleable.CustomTitleBar_right_center_title_text);

        show_bottom_line_view = typeArray.getBoolean(R.styleable.CustomTitleBar_show_bottom_line_view,true);

        init(context);
        typeArray.recycle();
    }

    private void init(Context context) {
        mInflater = LayoutInflater.from(context);
        linearLayout = mInflater.inflate(R.layout.view_title_bar, null);
        addView(linearLayout);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        setTitle_background_color(title_background_color);
        /**
         * 设置左边标题
         */
        setLeft_button_textColor(left_button_textColor);
        setTvLeftButton(left_button_text);
        setIvLeftButtonVisible(show_left_button);

        /**
         * 设置右边标题
         */
        setRight_button_text(right_button_text);
        setRight_button_textColor(right_button_textColor);
        set_show_Right_button(show_right_button);

        /**
         * 设置中间标题
         */
        setShow_left_center_button(show_left_center_button);
        setShow_right_center_button(show_right_center_button);
        setTvCenterLeftButton(title_text);
        setTvCenterRightButton(right_center_title_text);

        setBottomLineViewVisible(show_bottom_line_view);

        llLeftLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titleOnClickListener != null) {
                    titleOnClickListener.onLeftClick();
                }
            }
        });

        llRightLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titleOnClickListener != null) {
                    titleOnClickListener.onRightClick();
                }
            }
        });

        tvCenterLeftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleOnClickListener != null) {
                    titleOnClickListener.onCenterClick();
                }
            }
        });
        tvCloseText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                titleOnClickListener.onCloseClick();
            }
        });
    }

    /**
     * 获取左边的返回按钮
     *
     * @return Button
     */
    public TextView getTvLeftButton() {
        return tvLeftButton;
    }

    /**
     * 获取标题栏的跟布局
     *
     * @return LinearLayout
     */
    public RelativeLayout getRlTitleLayout() {
        return rlTitleLayout;
    }

    /**
     * 中间标题栏左边样式
     *
     * @return TextView
     */
    public TextView getTvCenterLeftButton() {
        return tvCenterLeftButton;
    }

    /**
     * 中间标题栏右边样式
     *
     * @return TextView
     */
    public TextView getTvCenterRightButton() {
        return tvCenterRightButton;
    }

    /**
     * 获取右边的保存按钮
     *
     * @return Button
     */
    public TextView getTvRightButton() {
        return tvRightButton;
    }

    /**
     * 设置返回按钮的资源图片id
     *
     * @param left_button_imageId 资源图片id
     */
    public void setIvLeftButton(int left_button_imageId) {
        ivLeftButton.setImageResource(left_button_imageId);
    }

    public void setIvLeftButtonVisible(boolean showLeftButton){
        ivLeftButton.setVisibility(showLeftButton ? VISIBLE : GONE);
    }

    /**
     * 设置返回按钮的文字
     *
     * @param left_button_text
     */
    public void setTvLeftButton(String left_button_text) {
        tvLeftButton.setText(left_button_text);
    }

    /**
     * 设置返回按钮的文字颜色
     *
     * @param left_button_textColor
     */
    public void setLeft_button_textColor(int left_button_textColor) {
        tvLeftButton.setTextColor(left_button_textColor);
    }

    /**
     * 设置返回按钮的文字大小
     *
     * @param left_button_textSize
     */
    public void setLeft_button_textSize(int left_button_textSize) {
        tvLeftButton.setTextSize(left_button_textSize);
    }

    /**
     * 设置是否显示返回按钮
     *
     * @param show_left_button
     */
    public void set_show_left_button(boolean show_left_button) {
        tvLeftButton.setVisibility(show_left_button ? VISIBLE : GONE);
    }

    /**
     * 设置是否显示右边按钮
     *
     * @param show_right_button
     */
    public void set_show_Right_button(boolean show_right_button) {
        tvRightButton.setVisibility(show_right_button ? VISIBLE : GONE);
    }


    /**
     * 设置右边按钮的资源图片id
     *
     * @param right_button_imageId 资源图片id
     */
    public void setIvRightButton(int right_button_imageId) {
        ivRightButton.setImageResource(right_button_imageId);
    }

    public void setIvRightButtonVisible(boolean showLeftButton){
        ivRightButton.setVisibility(showLeftButton ? VISIBLE : GONE);
    }


    /**
     * 设置右边保存按钮的资源图片
     *
     * @param right_button_imageId
     */
    public void setRight_button_imageId(int right_button_imageId) {
        ivRightButton.setVisibility(View.VISIBLE);
        ivRightButton.setBackgroundResource(right_button_imageId);
    }

    public ImageView getIvRightButton(){
        return ivRightButton;
    }
    public TextView getRightBotton(){
        return tvRightButton;
    }

    /**
     * 设置右边的保存按钮的文字
     *
     * @param right_button_text
     */
    public void setRight_button_text(String right_button_text) {
        tvRightButton.setText(right_button_text);
    }

    /**
     * 设置右边保存按钮的文字颜色
     *
     * @param right_button_textColor
     */
    public void setRight_button_textColor(int right_button_textColor) {
        tvRightButton.setTextColor(right_button_textColor);
    }

    /**
     * 设置右边保存按钮的文字大小
     *
     * @param right_button_textSize
     */
    public void setRight_button_textSize(int right_button_textSize) {
        tvRightButton.setTextSize(right_button_textSize);
    }

    /**
     * 设置是显示右边保存按钮
     *
     * @param show_right_button
     */
    public void setShow_right_button(boolean show_right_button) {
        tvRightButton.setVisibility(show_right_button ? VISIBLE : INVISIBLE);
    }

    /**
     * 设置是显示中间左边保存按钮
     *
     * @param show_left_center_button
     */
    public void setShow_left_center_button(boolean show_left_center_button) {
        tvCenterLeftButton.setVisibility(show_left_center_button ? VISIBLE : GONE);
    }

    /**
     * 设置是显示中间右边保存按钮
     *
     * @param show_right_center_button
     */
    public void setShow_right_center_button(boolean show_right_center_button) {
        tvCenterRightButton.setVisibility(show_right_center_button ? VISIBLE : GONE);
    }

    /**
     * 设置标题背景的颜色
     *
     * @param title_background_color
     */
    public void setTitle_background_color(int title_background_color) {
        rlTitleLayout.setBackgroundColor(title_background_color);
    }

    /**
     * 设置标题中间左边文字
     *
     * @param title_text
     */
    public void setTvCenterLeftButton(String title_text) {
        tvCenterLeftButton.setWidth(380);
        tvCenterLeftButton.setText(title_text);
    }

    public void setTvCenterRightIcon(Drawable drawable){
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
        tvCenterLeftButton.setCompoundDrawables(null,null,drawable,null);
    }

    /**
     *
     * @param visible
     */
    public void setTvCenterLeftButtonVisible(int visible) {
        tvCenterLeftButton.setVisibility(visible);
    }

    /**
     * 设置标题中间右边文字
     *
     * @param title_text
     */
    public void setTvCenterRightButton(String title_text) {
        tvCenterRightButton.setText(title_text);
    }

    /**
     *
     * @param visible
     */
    public void setTvCenterRightButtonVisible(int visible) {
        tvCenterRightButton.setVisibility(visible);
    }

    /**
     * 设置标题中间的文字颜色
     *
     * @param title_textColor
     */
    public void set_title_textColor(int title_textColor) {
        tvCenterLeftButton.setTextColor(title_textColor);
        tvCenterRightButton.setTextColor(title_textColor);
    }

    /**
     * 设置中间标题左边的背景
     *
     */
    public void setTvCenterLeftButtonBg(int resourceId){
        tvCenterLeftButton.setBackgroundResource(resourceId);
    }

    /**
     * 设置中间标题右边的背景
     *
     */
    public void setTvCenterRightButtonBg(int resourceId){
        tvCenterRightButton.setBackgroundResource(resourceId);
    }

    /**
     * 设置标题的左边文字大小
     *
     * @param title_textSize
     */
    public void setTvCenterLeftButtonTextSize(int title_textSize) {
        tvCenterLeftButton.setTextSize(title_textSize);
    }


    /**
     * 设置标题的右边文字大小
     *
     * @param title_textSize
     */
    public void setTvCenterRightButtonTextSize(int title_textSize) {
        tvCenterRightButton.setTextSize(title_textSize);
    }

    public void setBottomLineViewVisible(boolean visible){
        bottom_line_view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
    /**
     * 设置标题的点击监听
     *
     * @param titleOnClickListener
     */
    public void setOnTitleClickListener(TitleOnClickListener titleOnClickListener) {
        this.titleOnClickListener = titleOnClickListener;
    }


    /**
     * 设置显示关闭按钮
     *
     * @param showCloseText
     */
    public void setShow_close_text(boolean showCloseText) {
        tvCloseText.setVisibility(showCloseText ? VISIBLE : GONE);
    }

    /**
     * 监听标题点击接口
     */
    public interface TitleOnClickListener {
        /**
         * 返回按钮的点击事件
         */
        void onLeftClick();

        /**
         * 保存按钮的点击事件
         */
        void onRightClick();

        /**
         * 标题栏中间点击事件
         */
        void onCenterClick();

        /**
         * 关闭按钮事件
         */
        void onCloseClick();

    }
}
