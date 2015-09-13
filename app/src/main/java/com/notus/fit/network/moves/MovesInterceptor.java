package com.notus.fit.network.moves;

import android.content.Context;

import com.notus.fit.BuildConfig;
import com.notus.fit.models.api_models.User;
import com.notus.fit.utils.PrefManager;

import retrofit.RequestInterceptor;

public class MovesInterceptor implements RequestInterceptor {
    private Context context;

    public MovesInterceptor(Context context) {
        this.context = context;
    }

    public void intercept(RequestFacade request) {
        request.addHeader("Accept", "application/json");
        request.addHeader("Authorization", "Bearer " + PrefManager.with(this.context).getString(User.MOVES_TOKEN, BuildConfig.FLAVOR));
    }
}