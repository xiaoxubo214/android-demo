package com.by.wind.entity;


/**
 * Created by wind on 2018/3/27.
 */

public class LoginInfo {

    //private String id;
    private String userName;
    private String password;
    private String code;
    //private String tel;

    public LoginInfo() {

    }

    public LoginInfo(String userName, String password){
        this.userName = userName;
        this.password = password;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
