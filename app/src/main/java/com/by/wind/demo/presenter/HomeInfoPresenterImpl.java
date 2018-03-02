package com.by.wind.demo.presenter;

import com.by.wind.demo.BaseApplication;
import com.by.wind.demo.view.IBaseView;

import io.reactivex.Observable;

/**
 * Created by Wind on 2017/12/8.
 */

public class HomeInfoPresenterImpl implements IBasePresenter.IHomePresenter {
    IBaseView.HomeInfoView homeInfoView;
    public HomeInfoPresenterImpl(IBaseView.HomeInfoView homeInfoView) {
        this.homeInfoView = homeInfoView;
    }
    @Override
    public void getHomeInfo() {
       // Observable getHome = MyApplication.getInstance().apiService.getBanner();

    }
}
