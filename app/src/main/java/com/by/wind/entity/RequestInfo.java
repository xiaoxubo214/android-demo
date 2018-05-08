package com.by.wind.entity;

public class RequestInfo {
    private String request_type;
    private String phone_h;
    private String device_type;
    private String device_ver;
    private String device_id;
    private String app_ver;
    private String access_token;

    public String getRequest_type() {
        return request_type;
    }

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }

    public String getPhone_h() {
        return phone_h;
    }

    public void setPhone_h(String phone_h) {
        this.phone_h = phone_h;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getDevice_ver() {
        return device_ver;
    }

    public void setDevice_ver(String device_ver) {
        this.device_ver = device_ver;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getApp_ver() {
        return app_ver;
    }

    public void setApp_ver(String app_ver) {
        this.app_ver = app_ver;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
