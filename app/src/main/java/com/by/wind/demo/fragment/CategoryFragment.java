package com.by.wind.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.by.wind.demo.R;
import com.by.wind.demo.view.BaseFragment;

/**
 *
 */
public class CategoryFragment extends BaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initAllView(Bundle savedInstanceState) {

    }

}
