package com.notus.fit.network.fitbit;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;

public class FitbitApi extends DefaultApi10a {

    public static final String API_KEY = "59fb6d223b7c44c198bc5e00fcc10bc3";
    public static final String API_SECRET = "f39863be071b4db5893671abbd3ddaa7";
    public static final String AUTHORIZATION_URL = "https://www.fitbit.com/oauth/authorize?oauth_token=%s";

    public FitbitApi() {
    }

    public String getAccessTokenEndpoint() {
        return "https://api.fitbit.com/oauth/access_token";
    }

    public String getAuthorizationUrl(Token token) {
        return String.format("https://www.fitbit.com/oauth/authorize?oauth_token=%s", new Object[]{
                token.getToken()
        });
    }

    public String getRequestTokenEndpoint() {
        return "https://api.fitbit.com/oauth/request_token";
    }
}
