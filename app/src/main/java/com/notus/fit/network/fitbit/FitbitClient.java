package com.notus.fit.network.fitbit;

import android.content.Context;

import com.notus.fit.models.fitbit.FitbitWeekSteps;
import com.notus.fit.network.Client;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class FitbitClient {
    public static final String API_URL = "https://api.fitbit.com";
    public static final String FITBIT_PIN = "FITBIT_PIN";
    public static final String FITBIT_TOKEN = "FITBIT_TOKEN";
    public static final String FITBIT_TOKEN_RAW = "FITBIT_TOKEN_RAW";
    public static final String FITBIT_TOKEN_SECRET = "FITBIT_TOKEN_SECRET";
    public static final String FITBIT_URL_GET_ACTIVITY = "/1/user/-/activities/date/";
    public static final String FITBIT_URL_GET_USER = "/1/user/-/profile.json";
    public static final int GET_PIN_REQUEST = 101;
    static Executor executor = Executors.newCachedThreadPool();
    private static RestAdapter restAdapter;

    public FitbitClient() {
    }

    public static String getActivityURL(String s) {
        return (new StringBuilder()).append("https://api.fitbit.com/1/user/-/activities/date/").append(s).append(".json").toString();
    }

    public static RestAdapter getBaseRestAdapter() {
        retrofit.RestAdapter.Builder builder = new retrofit.RestAdapter.Builder();
        builder.setEndpoint("https://api.fitbit.com");
        return builder.build();
    }

    public static RestAdapter getBaseRestAdapter(RequestInterceptor requestinterceptor, Context context) {
        retrofit.RestAdapter.Builder builder = new retrofit.RestAdapter.Builder();
        builder.setExecutors(executor, executor);
        builder.setEndpoint("https://api.fitbit.com");
        builder.setRequestInterceptor(requestinterceptor);
        builder.setClient(new OkClient(Client.getInstance(context)));
        return builder.build();
    }

    public static String getUrlStepsSeries(String s, String s1) {
        return (new StringBuilder()).append("https://api.fitbit.com/1/user/-/activities/steps/date/").append(s).append("/").append(s1).append(".json").toString();
    }

    public static String getUserRequestUrl() {
        return "https://api.fitbit.com/1/user/-/profile.json";
    }

    public static interface Activities {

        public abstract void getFitbitActivity(String s, Callback callback);

        public abstract FitbitWeekSteps getTimeSeries(String s, String s1);
    }

    public static interface GetFitbitUser {

        public abstract void getFitbitUser(Callback callback);
    }

}
