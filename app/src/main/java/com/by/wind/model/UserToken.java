package com.by.wind.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wind on 17/4/24.
 */

public class UserToken implements Parcelable {

    public String accessToken;
    public String clientId;
    public long expireTime;
    public String refreshToken;

    protected UserToken(Parcel in) {
        accessToken = in.readString();
        clientId = in.readString();
        expireTime = in.readLong();
        refreshToken = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(accessToken);
        dest.writeString(clientId);
        dest.writeLong(expireTime);
        dest.writeString(refreshToken);
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
}
