package com.by.wind.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wind on 17/4/24.
 */

public class UserData implements Parcelable {

    public String request_type;
    public String result_code;
    public String result_msg;
    public String phone_h;
    public String access_token;

    protected UserData(Parcel in) {
        this.request_type = in.readString();
        this.result_code = in.readString();
        this.result_msg = in.readString();
        this.phone_h = in.readString();
        this.access_token = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(request_type);
        dest.writeString(result_code);
        dest.writeString(result_msg);
        dest.writeString(phone_h);
        dest.writeString(access_token);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };

    @Override
    public String toString() {
        return phone_h + "\t" +access_token;
    }
}
