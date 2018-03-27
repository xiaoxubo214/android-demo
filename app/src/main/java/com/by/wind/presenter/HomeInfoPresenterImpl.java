package com.by.wind.presenter;


import com.by.wind.view.IBaseView;

/**
 * Created by Wind on 2017/12/8.
 */

public class HomeInfoPresenterImpl implements IBasePresenter.IHomePresenter {
    IBaseView.IHomeView homeInfoView;
    public HomeInfoPresenterImpl(IBaseView.IHomeView homeInfoView) {
        this.homeInfoView = homeInfoView;
    }
    @Override
    public void getHomeInfo() {
       // Observable getHome = MyApplication.getInstance().apiService.getBanner();

    }
}
