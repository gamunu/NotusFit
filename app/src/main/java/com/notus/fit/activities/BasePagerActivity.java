package com.notus.fit.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.notus.fit.R;
import com.notus.fit.adapters.BaseViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public abstract class BasePagerActivity extends BaseActivity
        implements MaterialTabListener {

    protected static String LOG_TAG = BasePagerActivity.class.getSimpleName();
    protected BaseViewPagerAdapter mAdapter;
    protected List<Fragment> mFragmentList;
    protected ViewPager mPager;
    protected MaterialTabHost mTabHost;
    protected List<CharSequence> mTitleList;

    public BasePagerActivity() {
    }

    public int getLayoutResource() {
        return R.layout.activity_base_pager;
    }

    public void initPager() {
        this.mAdapter = new BaseViewPagerAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        this.mPager.setAdapter(this.mAdapter);
        this.mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageSelected(int position) {
                mTabHost.setSelectedNavigationItem(position);
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageScrollStateChanged(int state) {
            }

        });
        for (int i = 0; i < this.mAdapter.getCount(); i++) {
            this.mTabHost.addTab(this.mTabHost.newTab().setText(this.mAdapter.getPageTitle(i)).setTabListener(this));
        }

    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mTabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        mPager = (ViewPager) findViewById(R.id.pager);
        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();
    }

    public void onTabReselected(MaterialTab materialtab) {
    }

    public void onTabSelected(MaterialTab materialtab) {
        mPager.setCurrentItem(materialtab.getPosition());
    }

    public void onTabUnselected(MaterialTab materialtab) {
    }

    public void setPagerNumber(int i) {
        mPager.setCurrentItem(i);
        mTabHost.setSelectedNavigationItem(i);
    }

}
