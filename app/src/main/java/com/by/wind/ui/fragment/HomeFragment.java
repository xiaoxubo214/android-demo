package com.by.wind.ui.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.by.wind.R;
import com.by.wind.presenter.HomeInfoPresenterImpl;
import com.by.wind.presenter.IBasePresenter;
import com.by.wind.util.ToastUtil;
import com.by.wind.util.img.GlideImageLoader;
import com.by.wind.util.img.PicDisplayActivity;
import com.by.wind.view.IBaseView;
import com.wind.base.BaseFragment;
import com.wind.base.loading.LoadingDialog;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 */
public class HomeFragment extends BaseFragment implements IBaseView.IHomeView {

    @BindView(R.id.home_scan)
    ImageView mHomeScan;
    @BindView(R.id.home_search)
    EditText mHomeSearch;
    @BindView(R.id.home_message)
    ImageView mHomeMessage;
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.function_navigation)
    GridView mFunctionNavigation;
    Unbinder unbinder;
    List<String> mBannerImages;
    List<String> mBannerTitles;
    //List<Integer> mChannelImages;
    //List<String> mChannelTitles;
    IBasePresenter.IHomePresenter iHomePresenter;
    private LoadingDialog mLoadingDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBannerImages = new ArrayList<>();
        mBannerImages.add("https://m.360buyimg.com/babel/jfs/t18280/204/579586904/94976/2a2657e3/5a967a37N78d63824.jpg");
        mBannerImages.add("https://img1.360buyimg.com/da/jfs/t15523/12/2160773828/102088/eea30b29/5a96174aN6eb5cc3a.jpg");
        mBannerImages.add("https://m.360buyimg.com/babel/jfs/t16822/128/451750371/94562/acf4eb13/5a8290dfNa74a28b2.jpg");
        mBannerImages.add("https://img1.360buyimg.com/da/jfs/t18586/99/615650791/70152/f419a931/5a97c945Nf088ee44.jpg");
        mBannerTitles = new ArrayList<>();
        mBannerTitles.add("aaa");
        mBannerTitles.add("bbb");
        mBannerTitles.add("ccc");
        mBannerTitles.add("ddd");

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
        mHomeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mHomeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mHomeMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
      initBanner();
      initFunctionNavigation();
    }

    private void initFunctionNavigation() {
        int icno[] = {
                R.drawable.channel_hotcategory_liangyou,
                R.drawable.channel_hotcategory_meizhuang,
                R.drawable.channel_hotcategory_muyin,
                R.drawable.channel_hotcategory_qingjie,
                R.drawable.channel_hotcategory_yinliao,
                R.drawable.channel_hotcategory_yinliao,
                R.drawable.channel_hotcategory_qingjie,
                R.drawable.channel_hotcategory_muyin,
                R.drawable.channel_hotcategory_meizhuang,
                R.drawable.channel_hotcategory_liangyou,
                };
        //图标下的文字
        String name[]={"时钟","信号","宝箱","秒钟","大象","时钟","信号","宝箱","秒钟","大象"};
        List dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i <icno.length; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("img", icno[i]);
            map.put("text",name[i]);
            dataList.add(map);
        }

        String[] from={"img","text"};

        int[] to={R.id.img,R.id.text};

        SimpleAdapter adapter = new SimpleAdapter(context, dataList, R.layout.home_gridview_item, from, to);

        mFunctionNavigation.setAdapter(adapter);

        mFunctionNavigation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                //builder.setTitle("提示").setMessage(dataList.get(arg2).get("text").toString()).create().show();
            }
        });
    }


    private void initBanner() {
        //设置图片加载器
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(mBannerImages);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.Default);
        mBanner.setBannerTitles(mBannerTitles);
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

    @Override
    public void showLoading(String msg) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(context,false);
        }
        mLoadingDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void showError(String err) {
        ToastUtil.showToast(err);
    }
}
