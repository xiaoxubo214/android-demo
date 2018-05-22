package com.by.wind;

/**
 * Created by Wind on 2017/12/8.
 */

public class Constants {

    public static final String APPLICATION_ID = "com.by.wind";

    public final static boolean isDebug = false;

    public final static String HOST = "https://www.yhees.com/plat_interface/app_interface/";//服务器

    public final static String URL_API = HOST + "AppApi.ashx";//接口地址

    public final static String URL_PAGE = HOST + "AppPage.aspx";//Web页面地址

    public final static String API_REQUEST_TYPE = "request_type";

    public final static String API_GET_SMS = "get_verification_sms";//验证码

    public final static String API_REGISTER = "reg_account";//注册

    public final static String API_MODIFY_PASSWORD = "modify_password";//注册

    public final static String API_LOGIN = "check_login";//登录

    public final static String API_GET_USER_INFO = "get_member_info";//获取用户信息

    public final static String PAGE_MESSAGE = "goto_url_msg";//我的消息

    public final static String PAGE_TEAM = "goto_url_group";//团队

    public final static String PAGE_SHOP = "goto_url_biz";//商户通

    public final static String PAGE_SALE = "goto_url_sale";//销售

    public final static String PAGE_USER = "goto_url_member";//个人

    public final static String PAGE_COMMON = "goto_url_option";//通用

    public final static String PAGE_HELP = "goto_url_help";//帮助与反馈

    public final static String PAGE_ABOUT = "goto_url_about";//关于我们


    public final static String STR_PHONE = "phone_h";
    public final static String STR_TOKEN = "access_token";

    public static final String KEY_PROTECT_APP = "key_protect_app";

    public static final String RESULT_SUCCESS = "SUCCESS";


    public final static String RESPONSE_CACHE = "";
    public final static int RESPONSE_CACHE_SIZE = 1024;
    public final static int CONNECTION_TIMEOUT = 3 * 1000;
    public final static int READ_TIMEOUT = 3 * 1000;
    public static final long HTTP_CONNECT_TIMEOUT = 3 * 1000;
    public static final String HAWK_KEY = "demo_cache";
    public static final long HTTP_READ_TIMEOUT = 5 * 1000;

    public final static String RESULT_CODE_OK = "ok";

    public final static String START_ACTIVITY_REGISTER = "register";

    public final static String START_ACTIVITY_FORGET = "forget";


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

    public final static String DEVICE_PLATFORM = "android";

    public final static String UM_CHANNEL = "umeng";
    public final static String UM_APP_KEY = "5aea866bb27b0a04d00000f0";
    public final static String UM_MESSAGE_SECRET = "4b21d8593842ee628be7ce6f86eba6ef";

    public final static String TRY_USER = "88888888888";
    public final static String TRY_TOKEN = "4E38BC3B222AFF7271A051BAD1FDBF98";
}
