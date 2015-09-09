package com.notus.fit.network;

import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class Client {

    static int CACHE_SIZE = 20971520;
    private static OkHttpClient client;

    public Client() {
    }

    public static OkHttpClient getInstance(Context context) {
        if (client == null) {
            client = new OkHttpClient();
            try {
                client.setCache(new Cache(context.getCacheDir(), (long) CACHE_SIZE));
            } catch (Exception e) {
                Log.d(Client.class.getSimpleName(), "Unable to set http cache", e);
            }
            client.setReadTimeout(60, TimeUnit.SECONDS);
            client.setConnectTimeout(60, TimeUnit.SECONDS);
        }
        return client;
    }

}
