package com.notus.fit.network.services;

import android.app.IntentService;
import android.content.Intent;

public class UpdateFriendRequestService extends IntentService {
    private static final String LOG_TAG = UpdateFriendRequestService.class.getSimpleName();

    public UpdateFriendRequestService() {
        super(UpdateFriendRequestService.class.getSimpleName());
    }

    protected void onHandleIntent(Intent intent) {
    }
}
