package com.by.wind.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.by.wind.base.BaseFragment;
import com.by.wind.base.BaseFragment;

import java.util.ArrayList;


/**
 *
 */

public class FragmentPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<BaseFragment> fragments;

    public FragmentPagerAdapter(FragmentManager fm, ArrayList<BaseFragment> list) {
        super(fm);
        this.fragments = list;

    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
