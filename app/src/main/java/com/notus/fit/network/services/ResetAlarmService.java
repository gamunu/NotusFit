package com.notus.fit.network.services;

import android.app.IntentService;
import android.content.Intent;

import com.notus.fit.utils.PrefManager;
import com.notus.fit.utils.PreferenceUtils;

public class ResetAlarmService extends IntentService {

    public ResetAlarmService() {
        super("ResetAlarmService");
    }

    protected void onHandleIntent(Intent intent) {
        PrefManager.with(this).save(PreferenceUtils.OVERACHIEVER, false);
        PrefManager.with(this).save(PreferenceUtils.HALFWAY_DONE, false);
        PrefManager.with(this).save(PreferenceUtils.ALMOST_THERE, false);
        PrefManager.with(this).save(PreferenceUtils.GOAL_REACHED, false);
    }
}
