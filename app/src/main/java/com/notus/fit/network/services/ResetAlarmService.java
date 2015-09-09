package com.notus.fit.network.services;

import android.app.IntentService;
import android.content.Intent;

import com.notus.fit.utils.PrefManager;

public class ResetAlarmService extends IntentService {

    public ResetAlarmService() {
        super("ResetAlarmService");
    }

    protected void onHandleIntent(Intent intent) {
        PrefManager.with(this).save("overachiever", false);
        PrefManager.with(this).save("halfway_done", false);
        PrefManager.with(this).save("almost_there", false);
        PrefManager.with(this).save("goal_reached", false);
    }
}
