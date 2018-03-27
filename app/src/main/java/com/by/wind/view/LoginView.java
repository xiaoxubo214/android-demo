package com.by.wind.view;

import com.by.wind.base.BaseMvpView;

public interface LoginView extends BaseMvpView {

    /**
     * 获得界面上用户名的值
     * @return
     */
    String getUsername();
    /**
     * 获得界面上密码的值
     * @return
     */
    String getPassword();
    /**
     * 显示登录的结果
     * @param result
     */
    void showResult(String result);
}
