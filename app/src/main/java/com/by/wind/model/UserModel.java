package com.by.wind.model;

import android.os.Handler;
import android.os.Looper;

import com.by.wind.component.net.CallBack;


/**
 * Created by wind on 2018/3/27.
 */

public class UserModel {

    private String id;
    private String UserName;
    private String Password;
    private String tel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
