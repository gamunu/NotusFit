package com.notus.fit.network.moves;

import android.content.Context;
import android.net.Uri.Builder;
import android.os.SystemClock;

import com.facebook.internal.ServerProtocol;
import com.notus.fit.network.Client;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public class MoveApiClient {
    public static final String API_ENDPOINT = "https://api.moves-app.com";
    public static final String CLIENT_ID = "6tEBQpU0QpHUfB5x2oI0krZwyCoO0yId";
    public static final String CLIENT_SECRET = "DtAMXDP_5K3lDliABL3aABKTJx1MYqySiw7S5U54pPJspHQ1ofuzawOtmr1OGIY6";
    public static final String MOVES_FIRST_DATE = "movesFirstDate";
    public static final String REDIRECT_URI = "http://localhost";
    public static final int REQUEST_AUTHORIZE = 3998;
    static Executor executor = Executors.newCachedThreadPool();

    public interface MovesConnector {
        @POST("/oauth/v1/access_token")
        void authorize(@Query("grant_type") String str, @Query("code") String str2, @Query("client_id") String str3, @Query("client_secret") String str4, @Query("redirect_uri") String str5, Callback<MovesAccessToken> callback);

        @GET("/api/1.1/user/summary/daily")
        List<MovesActivity> getActivity(@Query("from") String str, @Query("to") String str2);

        @GET("/api/1.1/user/profile")
        void getUser(Callback<MovesUser> callback);
    }

    public static String getSelectedScopes() {
        return "activity";
    }

    public static Builder createAuthUri(String scheme, String authority, String path) {
        return new Builder().scheme(scheme).authority(authority).path(path).appendQueryParameter(ServerProtocol.DIALOG_PARAM_CLIENT_ID, CLIENT_ID).appendQueryParameter(ServerProtocol.DIALOG_PARAM_REDIRECT_URI, REDIRECT_URI).appendQueryParameter(ServerProtocol.DIALOG_PARAM_SCOPE, getSelectedScopes()).appendQueryParameter("state", String.valueOf(SystemClock.uptimeMillis()));
    }

    public static String extractCodeFromCallbackURL(String callbackURL) {
        StringBuilder code = new StringBuilder();
        boolean found = false;
        int i = 0;
        while (i < callbackURL.length() && callbackURL.charAt(i) != '&') {
            if (found) {
                code.append(callbackURL.charAt(i));
            }
            if (callbackURL.charAt(i) == '=') {
                found = true;
            }
            i++;
        }
        return code.toString();
    }

    public static RestAdapter getBaseRestAdapter() {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint(API_ENDPOINT);
        return builder.build();
    }

    public static RestAdapter getBaseRestAdapter(Context context) {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setExecutors(executor, executor);
        builder.setClient(new OkClient(Client.getInstance(context)));
        builder.setEndpoint(API_ENDPOINT);
        builder.setRequestInterceptor(new MovesInterceptor(context));
        return builder.build();
    }
}
