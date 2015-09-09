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

public abstract class DrawerPagerActivity extends DrawerActivity implements MaterialTabListener {
    protected static String LOG_TAG = DrawerPagerActivity.class.getSimpleName();
    protected BaseViewPagerAdapter adapter;
    protected List<Fragment> fragmentList;
    protected ViewPager pager;
    protected MaterialTabHost tabHost;
    protected List<CharSequence> titleList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.tabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        this.pager = (ViewPager) findViewById(R.id.pager);
        this.fragmentList = new ArrayList();
        this.titleList = new ArrayList();
    }

    public void onTabSelected(MaterialTab materialTab) {
        this.pager.setCurrentItem(materialTab.getPosition());
    }

    public void onTabReselected(MaterialTab materialTab) {
    }

    public void onTabUnselected(MaterialTab materialTab) {
    }

    public void initPager() {
        this.adapter = new BaseViewPagerAdapter(getSupportFragmentManager(), this.fragmentList, this.titleList);
        this.pager.setAdapter(this.adapter);
        this.pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                DrawerPagerActivity.this.tabHost.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        for (int i = 0; i < this.adapter.getCount(); i++) {
            this.tabHost.addTab(this.tabHost.newTab().setText(this.adapter.getPageTitle(i)).setTabListener(this));
        }
    }

    public int getLayoutResource() {
        return R.layout.activity_drawer_pager;
    }
}