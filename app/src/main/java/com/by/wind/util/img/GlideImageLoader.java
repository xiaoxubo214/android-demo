package com.by.wind.util.img;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
/**
 * Created by Wind on 2017/11/21.
 */

public class GlideImageLoader implements ImageLoader {

    @Override
    public void displayImage(Context activity, String path, ImageView imageView) {
        Glide.with(activity.getApplicationContext())
                .load(path)
                .into(imageView);
    }

    @Override
    public void displayImage(Context activity, String path, ImageView imageView, int defautResId) {

    }

    @Override
    public void displayCropImage(Context activity, String path, ImageView imageView, int defautResId) {

    }

    @Override
    public void displayImageUrl(Context activity, String path, ImageView imageView) {

    }

    @Override
    public void clearMemoryCache() {

    }
}
