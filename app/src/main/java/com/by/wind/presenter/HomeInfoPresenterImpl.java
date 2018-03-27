package com.by.wind.presenter;

import com.by.wind.base.IBasePresenter;
import com.by.wind.view.HomeView;


/**
 * Created by Wind on 2017/12/8.
 */

public class HomeInfoPresenterImpl implements IBasePresenter.IHomePresenter {
    HomeView homeInfoView;
    public HomeInfoPresenterImpl(HomeView homeInfoView) {
        this.homeInfoView = homeInfoView;
    }
    @Override
    public void getHomeInfo() {
       // Observable getHome = MyApplication.getInstance().apiService.getBanner();

    }
}
