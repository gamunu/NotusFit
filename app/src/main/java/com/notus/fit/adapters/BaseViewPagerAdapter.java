package com.notus.fit.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Gamunu Balagalla on 9/1/2015.
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
        return (Fragment) this.fragmentList.get(position);
    }

    public int getCount() {
        return this.fragmentList.size();
    }

    public CharSequence getPageTitle(int position) {
        return (CharSequence) this.titleList.get(position);
    }
}