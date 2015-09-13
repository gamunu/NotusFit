package com.notus.fit.activities;

import android.os.Bundle;

import com.notus.fit.R;
import com.notus.fit.fragments.AccountFragment;
import com.notus.fit.utils.CustomUtils;

public class AccountActivity extends BaseActivity {
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        CustomUtils.addFragmentToContainer(new AccountFragment(), R.id.container, this);
        setTitle("My Account");
        enableBackNav();
    }
}
