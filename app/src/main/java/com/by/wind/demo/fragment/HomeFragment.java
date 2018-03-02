package com.by.wind.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.by.wind.demo.R;
import com.by.wind.demo.presenter.HomeInfoPresenterImpl;
import com.by.wind.demo.presenter.IBasePresenter;
import com.by.wind.demo.util.img.GlideImageLoader;
import com.by.wind.demo.util.img.PicDisplayActivity;
import com.by.wind.demo.view.BaseFragment;
import com.by.wind.demo.view.IBaseView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 */
public class HomeFragment extends BaseFragment implements IBaseView.HomeInfoView {

    @BindView(R.id.scan)
    ImageView mScan;
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.function_navigation)
    GridView mFunctionNavigation;
    Unbinder unbinder;
    List<String> mImages;
    List<String> mTitles;
    IBasePresenter.IHomePresenter iHomePresenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImages = new ArrayList<>();
        mImages.add("http://image.thepaper.cn/www/image/6/375/115.jpg");
        mImages.add("http://image.thepaper.cn/www/image/6/375/115.jpg");
        mImages.add("http://image.thepaper.cn/www/image/6/375/115.jpg");
        mImages.add("http://image.thepaper.cn/www/image/6/375/115.jpg");

        mTitles = new ArrayList<>();
        mTitles.add("aaa");
        mTitles.add("bbb");
        mTitles.add("ccc");
        mTitles.add("ddd");

        iHomePresenter = new HomeInfoPresenterImpl(this);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initAllView(Bundle savedInstanceState) {
        mScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
      initBanner();
    }

    private void initBanner() {
        //设置图片加载器
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(mImages);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.Default);
        mBanner.setBannerTitles(mTitles);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();

        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                PicDisplayActivity.open(getContext());
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setBannerResult() {

    }

    @Override
    public void setFunctionResult() {

    }
}
