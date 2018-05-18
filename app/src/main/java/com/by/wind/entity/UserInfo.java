package com.by.wind.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wind on 17/4/24.
 */

public class UserInfo implements Parcelable {

    public String request_type;
    public String result_code;
    public String result_msg;
    public String phone_h;
    public String head_image;
    public String member_name;


    protected UserInfo(Parcel in) {
        this.request_type = in.readString();
        this.result_code = in.readString();
        this.result_msg = in.readString();
        this.phone_h = in.readString();
        this.head_image = in.readString();
        this.member_name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(request_type);
        dest.writeString(result_code);
        dest.writeString(result_msg);
        dest.writeString(phone_h);
        dest.writeString(head_image);
        dest.writeString(member_name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    @Override
    public String toString() {
        return phone_h + "\t" + member_name + "\t" + head_image;
    }
}
