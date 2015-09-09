package com.notus.fit.network.services;

import android.app.IntentService;
import android.content.Intent;

import retrofit.RestAdapter;

public class CreateUserService extends IntentService {

    private static final String TAG = CreateUserService.class.getSimpleName();
    RestAdapter restAdapter;

    public CreateUserService() {
        super(CreateUserService.class.getSimpleName());
    }

    protected void onHandleIntent(Intent intent) {
    }

}
