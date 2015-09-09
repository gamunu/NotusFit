package com.notus.fit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.notus.fit.fragments.SignUpFragment;
import com.notus.fit.fragments.WelcomeFragment;
import com.notus.fit.utils.PrefManager;
import com.notus.fit.utils.PreferenceUtils;


public class StartActivity extends BasePagerActivity {
    protected SignUpFragment signUpFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PrefManager.with(this).save(PreferenceUtils.FIRST_TIME, true);
        this.titleList.add("Welcome");
        this.titleList.add("Sign In / Sign Up");
        WelcomeFragment welcomeFragment = new WelcomeFragment();
        this.signUpFragment = new SignUpFragment();
        this.fragmentList.add(welcomeFragment);
        this.fragmentList.add(this.signUpFragment);
        initPager();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public SignUpFragment getSignUpFragment() {
        return this.signUpFragment;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.signUpFragment != null) {
            this.signUpFragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
