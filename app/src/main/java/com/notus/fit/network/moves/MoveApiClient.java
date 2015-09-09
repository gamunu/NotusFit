package com.notus.fit.network.moves;

import android.content.Context;
import android.os.SystemClock;

import com.notus.fit.network.Client;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

// Referenced classes of package com.notus.fit.network.moves:
//            MovesInterceptor

public class MoveApiClient {
    public static final String API_ENDPOINT = "https://api.moves-app.com";
    public static final String CLIENT_ID = "6tEBQpU0QpHUfB5x2oI0krZwyCoO0yId";
    public static final String CLIENT_SECRET = "DtAMXDP_5K3lDliABL3aABKTJx1MYqySiw7S5U54pPJspHQ1ofuzawOtmr1OGIY6";
    public static final String MOVES_FIRST_DATE = "movesFirstDate";
    public static final String REDIRECT_URI = "http://localhost";
    public static final int REQUEST_AUTHORIZE = 3998;
    static Executor executor = Executors.newCachedThreadPool();

    public MoveApiClient() {
    }

    public static android.net.Uri.Builder createAuthUri(String s, String s1, String s2) {
        return (new android.net.Uri.Builder()).scheme(s).authority(s1).path(s2).appendQueryParameter("client_id", "6tEBQpU0QpHUfB5x2oI0krZwyCoO0yId").appendQueryParameter("redirect_uri", "http://localhost").appendQueryParameter("scope", getSelectedScopes()).appendQueryParameter("state", String.valueOf(SystemClock.uptimeMillis()));
    }

    public static String extractCodeFromCallbackURL(String s) {
        StringBuilder stringbuilder = new StringBuilder();
        boolean flag = false;
        int i = 0;
        do {
            if (i >= s.length() || s.charAt(i) == '&') {
                return stringbuilder.toString();
            }
            if (flag) {
                stringbuilder.append(s.charAt(i));
            }
            if (s.charAt(i) == '=') {
                flag = true;
            }
            i++;
        } while (true);
    }

    public static RestAdapter getBaseRestAdapter() {
        retrofit.RestAdapter.Builder builder = new retrofit.RestAdapter.Builder();
        builder.setEndpoint("https://api.moves-app.com");
        return builder.build();
    }

    public static RestAdapter getBaseRestAdapter(Context context) {
        retrofit.RestAdapter.Builder builder = new retrofit.RestAdapter.Builder();
        builder.setExecutors(executor, executor);
        builder.setClient(new OkClient(Client.getInstance(context)));
        builder.setEndpoint("https://api.moves-app.com");
        builder.setRequestInterceptor(new MovesInterceptor(context));
        return builder.build();
    }

    public static String getSelectedScopes() {
        return "activity";
    }

    public static interface MovesConnector {

        public abstract void authorize(String s, String s1, String s2, String s3, String s4, Callback callback);

        public abstract List getActivity(String s, String s1);

        public abstract void getUser(Callback callback);
    }

}
