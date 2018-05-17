package com.by.wind.util.img;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;


public class ImageItem implements Parcelable {

    public long id;
    public String name;       //图片的名字
    public String url;        //图片的路径
    public String path;       //图片本地路径
    public long size;         //图片的大小
    public int width;         //图片的宽度
    public int height;        //图片的高度
    public String mimeType;   //图片的类型
    public long addTime;      //图片的创建时间

    public ImageItem() {
    }


    protected ImageItem(Parcel in) {
        id = in.readLong();
        name = in.readString();
        url = in.readString();
        size = in.readLong();
        width = in.readInt();
        height = in.readInt();
        mimeType = in.readString();
        addTime = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(url);
        dest.writeLong(size);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeString(mimeType);
        dest.writeLong(addTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImageItem> CREATOR = new Creator<ImageItem>() {
        @Override
        public ImageItem createFromParcel(Parcel in) {
            return new ImageItem(in);
        }

        @Override
        public ImageItem[] newArray(int size) {
            return new ImageItem[size];
        }
    };

    /**
     * 图片的路径和创建时间相同就认为是同一张图片
     */
    @Override
    public boolean equals(Object o) {
        try {
            ImageItem other = (ImageItem) o;
            return TextUtils.equals(this.url, other.url) && this.addTime == other.addTime;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }
}
