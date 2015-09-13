package com.notus.fit.network.fitbit;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;

public class FitbitApi extends DefaultApi10a {
    public static final String API_KEY = "59fb6d223b7c44c198bc5e00fcc10bc3";
    public static final String API_SECRET = "f39863be071b4db5893671abbd3ddaa7";
    private static final String AUTHORIZATION_URL = "http://www.fitbit.com/oauth/authorize?oauth_token=%s";

    @Override
    public String getRequestTokenEndpoint() {
        return "http://api.fitbit.com/oauth/request_token";
    }

    @Override
    public String getAccessTokenEndpoint() {
        return "http://api.fitbit.com/oauth/access_token";
    }

    @Override
    public String getAuthorizationUrl(Token requestToken) {
        return String.format(AUTHORIZATION_URL, requestToken.getToken());
    }
}
