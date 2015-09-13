package com.notus.fit.network.fitbit;

import android.content.Context;

import com.notus.fit.BuildConfig;
import com.notus.fit.utils.PrefManager;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import java.util.Map.Entry;

import retrofit.RequestInterceptor;


public class FitbitInterceptor implements RequestInterceptor {

    protected FitbitApi api;
    protected Context context;
    protected OAuthRequest oAuthRequest;
    protected OAuthService service;

    public FitbitInterceptor(Context context, String url) {
        this.context = context;
        this.service = new ServiceBuilder()
                .provider(FitbitApi.class)
                .apiKey(FitbitApi.API_KEY)
                .apiSecret(FitbitApi.API_SECRET).build();
        this.api = new FitbitApi();
        Token token = new Token(PrefManager.with(context)
                .getString(FitbitClient.FITBIT_TOKEN, BuildConfig.FLAVOR),
                PrefManager.with(context).getString(FitbitClient.FITBIT_TOKEN_SECRET, BuildConfig.FLAVOR),
                PrefManager.with(context).getString(FitbitClient.FITBIT_TOKEN_RAW, BuildConfig.FLAVOR));
        this.oAuthRequest = new OAuthRequest(Verb.GET, url);
        this.service.signRequest(token, this.oAuthRequest);
    }

    public void intercept(RequestFacade request) {
        for (Entry<String, String> entry : this.oAuthRequest.getHeaders().entrySet()) {
            request.addHeader(entry.getKey(), entry.getValue());
        }
    }
}
