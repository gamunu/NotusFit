package com.notus.fit.activities;

import android.os.Bundle;

import com.notus.fit.R;
import com.notus.fit.fragments.HistoryListFragment;
import com.notus.fit.utils.CustomUtils;

public class ActivityHistory extends DrawerActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("History");
        CustomUtils.addFragmentToContainer(new HistoryListFragment(), R.id.container, this);
    }
}
