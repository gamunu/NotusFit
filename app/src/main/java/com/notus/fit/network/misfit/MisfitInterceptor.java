package com.notus.fit.network.misfit;

import android.content.Context;

import com.notus.fit.utils.PrefManager;

import retrofit.RequestInterceptor;

public class MisfitInterceptor
        implements RequestInterceptor {

    private Context context;

    public MisfitInterceptor(Context context1) {
        context = context1;
    }

    public void intercept(retrofit.RequestInterceptor.RequestFacade requestfacade) {
        requestfacade.addHeader("Accept", "application/json");
        requestfacade.addHeader("Authorization", (new StringBuilder()).append("Bearer ").append(PrefManager.with(context).getString("misfit_token", "")).toString());
    }
}
