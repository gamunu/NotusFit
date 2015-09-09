package com.notus.fit.activities;

import android.os.Bundle;

import com.notus.fit.R;
import com.notus.fit.fragments.UserListFragment;
import com.notus.fit.utils.CustomUtils;

public class FriendListActivity extends DrawerActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Friends");
        enableBackNav();
        CustomUtils.addFragmentToContainer(new UserListFragment(), R.id.container, this);
    }
}
