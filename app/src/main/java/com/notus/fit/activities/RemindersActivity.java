package com.notus.fit.activities;

import android.os.Bundle;

import com.notus.fit.R;
import com.notus.fit.fragments.RemindersFragment;
import com.notus.fit.utils.CustomUtils;

public class RemindersActivity extends BaseActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomUtils.addFragmentToContainer(new RemindersFragment(), R.id.container, this);
        setTitle("Reminders");
        enableBackNav();
    }
}
