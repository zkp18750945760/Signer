package com.zhoukp.signer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zhoukp.signer.fragment.BaseFragment;

import java.util.ArrayList;

/**
 * @author zhoukp
 * @time 2018/1/31 13:56
 * @email 275557625@qq.com
 * @function
 */

public class ActivityPagerViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<BaseFragment> fragments;
    private ArrayList<String> titles;

    public ActivityPagerViewPagerAdapter(FragmentManager fm, ArrayList<BaseFragment> fragments, ArrayList<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
