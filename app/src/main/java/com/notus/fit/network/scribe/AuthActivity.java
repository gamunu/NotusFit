package com.notus.fit.network.scribe;

import android.os.Bundle;

import com.notus.fit.R;
import com.notus.fit.activities.BaseActivity;
import com.notus.fit.utils.CustomUtils;


public class AuthActivity extends BaseActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomUtils.addFragmentToContainer(new AuthFragment(), R.id.container, this);
    }
}
