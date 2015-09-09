package com.notus.fit.dashclock;

import android.content.Intent;

import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;
import com.notus.fit.MainActivity;
import com.notus.fit.R;
import com.notus.fit.utils.PrefManager;

/**
 * Created by VBALAUD on 9/3/2015.
 */
public class FithubExtension extends DashClockExtension {
    protected void onUpdateData(int reason) {
        int todaySteps = PrefManager.with(this).getInt(getString(R.string.today_steps), 0);
        publishUpdate(new ExtensionData().visible(true).icon(R.drawable.ic_steps).status(todaySteps + " steps (" + (((Integer.parseInt(PrefManager.with(this).getString(getString(R.string.steps_goal), "10000")) / 100) * (todaySteps / 100)) / 100) + "% goal)").clickIntent(new Intent(this, MainActivity.class)));
    }
}
