package com.by.wind.util.img;

import android.content.Context;
import android.widget.ImageView;

import java.io.Serializable;

public interface ImageLoader extends Serializable {

    void displayImage(Context activity, String path, ImageView imageView);

    void displayImage(Context activity, String path, ImageView imageView, int defautResId);

    void displayCropImage(Context activity, String path, ImageView imageView, int defautResId);

    void displayImageUrl(Context activity, String path, ImageView imageView);

    void clearMemoryCache();
}
