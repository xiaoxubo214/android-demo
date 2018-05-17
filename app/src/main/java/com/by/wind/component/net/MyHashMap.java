package com.by.wind.component.net;

import com.by.wind.BaseApplication;
import com.by.wind.Constants;
import com.by.wind.entity.UserData;
import com.by.wind.util.DeviceUtil;
import com.by.wind.util.PreferenceHelper;

import java.util.HashMap;

public class MyHashMap extends HashMap<String, Object> {
    public static MyHashMap newInstance() {
        MyHashMap myHashMap = new MyHashMap();
        myHashMap.put("device_type", Constants.DEVICE_PLATFORM);
        myHashMap.put("device_ver", BaseApplication.getInstance().getVersionCode() + "");
        myHashMap.put("device_id", DeviceUtil.getIMEI(BaseApplication.getInstance().getApplicationContext()));
        myHashMap.put("app_ver", BaseApplication.getInstance().getVersionName());
        UserData userData = PreferenceHelper.getUserToken();
        if (userData != null) {
            myHashMap.put(Constants.STR_PHONE, userData.phone_h);
            myHashMap.put(Constants.STR_TOKEN, userData.access_token);
        }
        return myHashMap;
    }
}
