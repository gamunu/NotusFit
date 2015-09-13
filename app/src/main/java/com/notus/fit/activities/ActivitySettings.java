package com.notus.fit.activities;

import android.os.Bundle;

import com.notus.fit.R;
import com.notus.fit.fragments.SettingsFragment;
import com.notus.fit.utils.CustomUtils;


public class ActivitySettings extends BaseActivity {
    public int getLayoutResource() {
        return R.layout.activity_settings;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomUtils.addFragmentToContainer(new SettingsFragment(), R.id.container, this);
        enableBackNav();
        setTitle("Settings");
    }
}
