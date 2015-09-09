package com.notus.fit.activities;

import android.os.Bundle;
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
    protected BaseViewPagerAdapter adapter;
    protected List fragmentList;
    protected ViewPager pager;
    protected MaterialTabHost tabHost;
    protected List titleList;

    public BasePagerActivity() {
    }

    public int getLayoutResource() {
        return R.layout.activity_base_pager;
    }

    public void initPager() {
        this.adapter = new BaseViewPagerAdapter(getSupportFragmentManager(), this.fragmentList, this.titleList);
        this.pager.setAdapter(this.adapter);
        this.pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageSelected(int position) {
                BasePagerActivity.this.tabHost.setSelectedNavigationItem(position);
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageScrollStateChanged(int state) {
            }

        });
        for (int i = 0; i < this.adapter.getCount(); i++) {
            this.tabHost.addTab(this.tabHost.newTab().setText(this.adapter.getPageTitle(i)).setTabListener(this));
        }

    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        tabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        pager = (ViewPager) findViewById(R.id.pager);
        fragmentList = new ArrayList();
        titleList = new ArrayList();
    }

    public void onTabReselected(MaterialTab materialtab) {
    }

    public void onTabSelected(MaterialTab materialtab) {
        pager.setCurrentItem(materialtab.getPosition());
    }

    public void onTabUnselected(MaterialTab materialtab) {
    }

    public void setPagerNumber(int i) {
        pager.setCurrentItem(i);
        tabHost.setSelectedNavigationItem(i);
    }

}
