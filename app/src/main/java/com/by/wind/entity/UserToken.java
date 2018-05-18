package com.by.wind.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wind on 17/4/24.
 */

public class UserToken implements Parcelable {

    public String request_type;
    public String result_code;
    public String result_msg;
    public String phone_h;
    public String access_token;


    protected UserToken(Parcel in) {
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

    public static final Creator<UserToken> CREATOR = new Creator<UserToken>() {
        @Override
        public UserToken createFromParcel(Parcel in) {
            return new UserToken(in);
        }

        @Override
        public UserToken[] newArray(int size) {
            return new UserToken[size];
        }
    };

    @Override
    public String toString() {
        return phone_h + "\t" +access_token;
    }
}
