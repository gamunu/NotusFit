package com.notus.fit.network;

import android.content.Context;

import com.notus.fit.utils.PrefManager;

import retrofit.RequestInterceptor;

public class TokenInterceptor implements RequestInterceptor {

    private Context context;

    public TokenInterceptor(Context context1) {
        context = context1;
    }

    public void intercept(RequestInterceptor.RequestFacade requestfacade) {
        requestfacade.addHeader("Accept", "application/json");
        requestfacade.addHeader("Authorization", (new StringBuilder()).append("Token ").append(PrefManager.with(context).getString("token_fithub", "")).toString());
    }
}
