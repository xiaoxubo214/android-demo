package com.by.wind.demo.util.img;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;
/**
 * Created by Wind on 2017/11/21.
 */

public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context.getApplicationContext())
                .load(path)
                .into(imageView);
    }

    @Override
    public ImageView createImageView(Context context) {
        return super.createImageView(context);
    }
}
