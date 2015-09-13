package com.notus.fit.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/1/2015 1:13 PM
 */
public class BaseViewPagerAdapter extends FragmentStatePagerAdapter {

    List<Fragment> fragmentList;
    List<CharSequence> titleList;

    public BaseViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<CharSequence> titleList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }

    private BaseViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    public Fragment getItem(int position) {
        return this.fragmentList.get(position);
    }

    public int getCount() {
        return this.fragmentList.size();
    }

    public CharSequence getPageTitle(int position) {
        return this.titleList.get(position);
    }
}