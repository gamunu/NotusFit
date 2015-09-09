package com.jawbone.upplatformsdk.api;

import com.parse.signpost.OAuth;

import retrofit.RequestInterceptor;

public class ApiHeaders implements RequestInterceptor {
    private String accessToken;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void clearAccessToken() {
        this.accessToken = null;
    }

    public void intercept(RequestFacade request) {
        if (this.accessToken != null) {
            request.addHeader(OAuth.HTTP_AUTHORIZATION_HEADER, "Bearer " + this.accessToken);
            request.addHeader("Accept", "application/json");
        }
    }
}
