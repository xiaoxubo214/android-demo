package com.by.wind.component.net;

import android.text.TextUtils;

/**
 * Created by christy ic_on 17/2/23.
 */
public class ApiException extends RuntimeException {

    public static final int MSG_UNKNOW_ERROR = -1;//未知错误
    public static final int MSG_API_NOT_FOUND = -2;//未知API
    public static final int MSG_NULL_VALUE = 201;//Null 错误
    public static final int MSG_AUTH_FAIL = 400;//400
    public static final int MSG_VALID_TOKEN = 401;//Token无效
    public static final int MSG_LOGIN_FAIL = 500;//用户登陆失败
    public static final int MSG_PHONE_NOT_FOUND = 501;//未知电话
    public static final int MSG_NAME_CONFILICT = 502;//用户名称冲突
    public static final int MSG_PHONE_CONFILICT = 503;//电话号码冲突
    public static final int MSG_ERR_SEND_CODE = 506;//验证码发送失败
    public static final int MSG_TIMEOUT_SMS_CODE = 511;//短信超过时间
    public static final int MSG_SMS_CODE_NOT_MATCH = 512;//短信码错误
    public static final int MSG_PWD_ERROR = 513;//密码错误
    public static final int MSG_PWD_FIND_FAIL = 510;//找回密码错误
    public static final int MSG_PWD_MODIFY_FAIL = 504;//密码修改错误
    public static final int MSG_USER_NOT_FOUND = 515;//用户不存在
    public static final int MSG_USER_NAME_MODIFY_FAIL = 520;//用户名不能修改
    public static final int MSG_ERROR_PHONE_EXIST = 505;//手机已注册
    public static final int MSG_COURSE_NO_SIGN = 200001;    //该课程已经销课无法签到
    public static final int MSG_ERROR_TIME_MULTi = 200002;  //老师排课时间存在冲突！

    private static String message;
    public static int errorCode;

    public ApiException(String resultCode, String message) {
        this(getApiExceptionMessage(!TextUtils.isEmpty(resultCode) ? Integer.valueOf(resultCode) : -2, message));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        message = msg;
    }

    private static String getApiExceptionMessage(int code, String msg) {
        errorCode = code;
        switch (code) {
            case MSG_UNKNOW_ERROR:
                message = "未知错误";
                break;
            case MSG_API_NOT_FOUND:
                message = "未知API";
                break;
            case MSG_NULL_VALUE:
                message = "Null错误";
                break;
            case MSG_AUTH_FAIL:
                message = msg;
                break;
            case MSG_LOGIN_FAIL:
                message = "用户登陆失败";
                break;
            case MSG_PHONE_NOT_FOUND:
                message = "未知电话";
                break;
            case MSG_NAME_CONFILICT:
                message = "用户名称冲突";
                break;
            case MSG_PHONE_CONFILICT:
                message = "电话号码冲突";
                break;
            case MSG_TIMEOUT_SMS_CODE:
                message = "短信超过时间";
                break;
            case MSG_SMS_CODE_NOT_MATCH:
                message = "短信码错误";
                break;
            case MSG_PWD_FIND_FAIL:
                message = "找回密码错误";
                break;
            case MSG_PWD_MODIFY_FAIL:
                message = "密码修改错误";
                break;
            case MSG_USER_NOT_FOUND:
                message = "用户不存在";
                break;
            case MSG_USER_NAME_MODIFY_FAIL:
                message = "用户名不能修改";
                break;
            case MSG_ERR_SEND_CODE:
                message = "验证码发送失败";
                break;
            case MSG_ERROR_PHONE_EXIST:
                message = "手机已注册";
                break;
            case MSG_PWD_ERROR:
                message = "密码错误";
                break;
            case MSG_COURSE_NO_SIGN:
                message = "该课程已经销课无法签到!";
                break;
            case MSG_ERROR_TIME_MULTi:
                message = "老师排课时间存在冲突！";
                break;
            case MSG_VALID_TOKEN:
                message = "Token无效！";
                break;
            default:
                message = msg;
        }
        return message;
    }
}
