package com.by.wind.component.net;

import com.google.gson.annotations.SerializedName;

/**
 * Created by christy ic_on 16/12/23.
 */

public class RetrofitResult<T> {


    //用来模仿resultCode和resultMessage
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;
    //用来模仿Data
    @SerializedName("data")
    private T data;

   // private String checkcode;
   public String getCode() {
       return code;
   }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }

    public T getData() {
        return data;
    }

    public void setSubjects(T data) {
        this.data = data;
    }
}
