package com.notus.fit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.notus.fit.fragments.SignUpFragment;
import com.notus.fit.fragments.WelcomeFragment;
import com.notus.fit.utils.PrefManager;
import com.notus.fit.utils.PreferenceUtils;


public class StartActivity extends BasePagerActivity {
    protected SignUpFragment mSignUpFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PrefManager.with(this).save(PreferenceUtils.FIRST_TIME, true);
        mTitleList.add("Welcome");
        mTitleList.add("Sign In / Sign Up");
        WelcomeFragment welcomeFragment = new WelcomeFragment();
        mSignUpFragment = new SignUpFragment();
        mFragmentList.add(welcomeFragment);
        mFragmentList.add(mSignUpFragment);
        initPager();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public SignUpFragment getSignUpFragment() {
        return mSignUpFragment;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mSignUpFragment != null) {
            mSignUpFragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
