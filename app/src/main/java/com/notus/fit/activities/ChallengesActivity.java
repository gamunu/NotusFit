package com.notus.fit.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.notus.fit.R;
import com.notus.fit.fragments.ChallengesFragment;


public class ChallengesActivity extends DrawerPagerActivity {

    public ChallengesActivity() {
    }

    public int getLayoutResource() {
        return R.layout.challenges_activity;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Challenges");
        this.mTitleList.add("Active");
        this.mTitleList.add("My Challenges");
        this.mTitleList.add("Not Active");
        ChallengesFragment activeChallenges = new ChallengesFragment();
        Bundle b = new Bundle();
        b.putString("type", "active");
        activeChallenges.setArguments(b);
        ChallengesFragment myChallenges = new ChallengesFragment();
        Bundle b1 = new Bundle();
        b1.putString("type", "my_challenges");
        myChallenges.setArguments(b1);
        ChallengesFragment pastChallenges = new ChallengesFragment();
        Bundle b2 = new Bundle();
        b2.putString("type", "past_challenges");
        pastChallenges.setArguments(b2);
        this.mFragmentList.add(activeChallenges);
        this.mFragmentList.add(myChallenges);
        this.mFragmentList.add(pastChallenges);
        initPager();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_challlenges, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_info:
                showInfoDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setTabSelected(int i) {
        mTabHost.setSelectedNavigationItem(i);
        mPager.setCurrentItem(i);
    }

    public void showInfoDialog() {
        new Builder(this).customView(R.layout.dialog_challenge_info, true).positiveText("Dismiss").positiveColor(getResources().getColor(R.color.green_700)).build().show();
    }
}
