package com.by.wind.demo.util.img;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.by.wind.demo.R;
import com.by.wind.demo.util.DensityUtil;
import com.by.wind.demo.view.BaseActivity;

public class PicDisplayActivity extends BaseActivity {

    private ImageView imageView;
    private int mScreenHeight;
    private int mScreenWidth;
    public static void open(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, PicDisplayActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_display);
        imageView = (ImageView) findViewById(R.id.iv);
        mScreenHeight = DensityUtil.getScreenHeight(this);
        mScreenWidth = DensityUtil.getScreenWidth(this);
        GlideImageLoader glideImageLoader = new GlideImageLoader();
        glideImageLoader.displayImage(this,"http://192.168.1.154:8080/manager/statics/imgs/qmsht.jpg",imageView);
    }
}
