package com.by.wind;

/**
 * Created by Wind on 2017/12/8.
 */

public class Constants {

    public final static boolean isDebug = true;

    public final static String HOST = "http://www.baidu.com:8080";

    public static final String KEY_PROTECT_APP = "key_protect_app";

    public final static String RESPONSE_CACHE = "";
    public final static int RESPONSE_CACHE_SIZE = 1024;
    public final static int CONNECTION_TIMEOUT = 3 * 1000;
    public final static int READ_TIMEOUT = 3 * 1000;
    public static final long HTTP_CONNECT_TIMEOUT = 3 * 1000;
    public static final String HAWK_KEY = "demo_cache";
    public static final long HTTP_READ_TIMEOUT = 5 * 1000;


    public static final int SUCCESS = 1;
    public static final int FAIL = 2;
    public static final int ERROR = 3;
    public static final int TEST = 0;

    public static final int VALID_PHONE_NULL = 101;//请填写手机号码
    public static final int VALID_PWD_NULL = 102;//密码为空
    public static final int VALID_PWD_SHORT = 103;//密码小于6位数
    public static final int VALID_PHONE_ERROR = 104;//手机格式错误
    public static final int VALID_SUCCESS = 100;//验证通过
    public static final int VALID_VERIFY_CODE_NULL = 105;//验证码为空
    public static final int VALID_VERIFY_CODE_INVALID = 106;//无效验证码
}
