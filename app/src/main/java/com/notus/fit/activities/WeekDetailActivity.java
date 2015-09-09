package com.notus.fit.activities;

import android.os.Bundle;

import com.notus.fit.R;
import com.notus.fit.fragments.WeekDetailFragment;
import com.notus.fit.models.api_models.WeekData;
import com.notus.fit.utils.CustomUtils;

import org.parceler.Parcels;

public class WeekDetailActivity extends BaseActivity {
    WeekData weekData;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableBackNav();
        if (getIntent().getExtras() != null) {
            this.weekData = (WeekData) Parcels.unwrap(getIntent().getParcelableExtra("week"));
            if (this.weekData != null) {
                setTitle("Week of " + CustomUtils.formatLongDate(this, this.weekData.getStartDate()));
                WeekDetailFragment weekDetailFragment = new WeekDetailFragment();
                weekDetailFragment.setArguments(getIntent().getExtras());
                CustomUtils.addFragmentToContainer(weekDetailFragment, R.id.container, this);
            }
        }
    }
}