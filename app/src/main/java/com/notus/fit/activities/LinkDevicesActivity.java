package com.notus.fit.activities;

import android.os.Bundle;

import com.facebook.internal.NativeProtocol;
import com.notus.fit.R;
import com.notus.fit.fragments.AddAccountsFragment;
import com.notus.fit.utils.CustomUtils;

public class LinkDevicesActivity extends BaseActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AddAccountsFragment fragment = new AddAccountsFragment();
        Bundle b = new Bundle();
        boolean link = getIntent().getExtras() != null && getIntent().getExtras().getBoolean(NativeProtocol.METHOD_ARGS_LINK);
        b.putBoolean(NativeProtocol.METHOD_ARGS_LINK, link);
        fragment.setArguments(b);
        CustomUtils.addFragmentToContainer(fragment, R.id.container, this);
        if (!link) {
            enableBackNav();
        }
        getSupportActionBar().setTitle("Connect Accounts");
    }
}
