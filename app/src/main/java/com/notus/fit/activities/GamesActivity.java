package com.notus.fit.activities;

import android.os.Bundle;

import com.notus.fit.R;
import com.notus.fit.fragments.GamesFragment;
import com.notus.fit.utils.CustomUtils;

public class GamesActivity extends BaseActivity {
    public static final int REQUEST_ACHIEVEMENTS = 4991;
    public static final int REQUEST_LEADERBOARD = 4990;

    protected void onCreate(Bundle b) {
        super.onCreate(b);
        enableBackNav();
        setTitle("Game Services");
        CustomUtils.addFragmentToContainer(new GamesFragment(), R.id.container, this);
    }
}
