package com.notus.fit.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.notus.fit.R;
import com.notus.fit.adapters.BaseViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public abstract class DrawerPagerActivity extends DrawerActivity implements MaterialTabListener {
    protected static String LOG_TAG = DrawerPagerActivity.class.getSimpleName();
    protected BaseViewPagerAdapter mAdapter;
    protected List<Fragment> mFragmentList;
    @Bind(R.id.pager)
    protected ViewPager mPager;
    @Bind(R.id.materialTabHost)
    protected MaterialTabHost mTabHost;
    protected List<CharSequence> mTitleList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();
    }

    public void onTabSelected(MaterialTab materialTab) {
        mPager.setCurrentItem(materialTab.getPosition());
    }

    public void onTabReselected(MaterialTab materialTab) {
    }

    public void onTabUnselected(MaterialTab materialTab) {
    }

    public void initPager() {
        mAdapter = new BaseViewPagerAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        mPager.setAdapter(mAdapter);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabHost.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        for (int i = 0; i < mAdapter.getCount(); i++) {
            mTabHost.addTab(mTabHost.newTab().setText(mAdapter.getPageTitle(i)).setTabListener(this));
        }
    }

    public int getLayoutResource() {
        return R.layout.activity_drawer_pager;
    }
}