package com.notus.fit.network.fitbit;

import android.content.Context;
import android.util.Log;

import com.notus.fit.models.fitbit.FitbitActivity;
import com.notus.fit.models.fitbit.FitbitWeekSteps;
import com.notus.fit.models.fitbit.FitbitWrapperUser;
import com.notus.fit.network.Client;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import retrofit.client.OkClient;
import retrofit.http.GET;
import retrofit.http.Path;

public class FitbitClient {
    public static final String API_URL = "https://api.fitbit.com";
    public static final String FITBIT_PIN = "FITBIT_PIN";
    public static final String FITBIT_TOKEN = "FITBIT_TOKEN";
    public static final String FITBIT_TOKEN_RAW = "FITBIT_TOKEN_RAW";
    public static final String FITBIT_TOKEN_SECRET = "FITBIT_TOKEN_SECRET";
    public static final int GET_PIN_REQUEST = 101;
    static Executor executor = Executors.newCachedThreadPool();
    private static RestAdapter restAdapter;

    public interface Activities {
        @GET("/1/user/-/activities/date/{date}.json")
        void getFitbitActivity(@Path("date") String str, Callback<FitbitActivity> callback);

        @GET("/1/user/-/activities/steps/date/{date}/{period}.json")
        FitbitWeekSteps getTimeSeries(@Path("date") String str, @Path("period") String str2);
    }

    public interface GetFitbitUser {
        @GET("/1/user/-/profile.json")
        void getFitbitUser(Callback<FitbitWrapperUser> callback);
    }

    public static RestAdapter getBaseRestAdapter() {
        Builder builder = new Builder();
        builder.setEndpoint(API_URL);
        return builder.build();
    }

    public static RestAdapter getBaseRestAdapter(RequestInterceptor interceptor, Context context) {
        Builder builder = new Builder();
        builder.setExecutors(executor, executor);
        builder.setEndpoint(API_URL);
        builder.setRequestInterceptor(interceptor);
        builder.setClient(new OkClient(Client.getInstance(context)));
        builder.setLogLevel(RestAdapter.LogLevel.FULL).setLog(new RestAdapter.Log() {
            public void log(String msg) {
                Log.e("retrofit", msg);
            }
        });
        return builder.build();
    }

    public static String getUserRequestUrl() {
        return "https://api.fitbit.com/1/user/-/profile.json";
    }

    public static String getActivityURL(String date) {
        return "https://api.fitbit.com/1/user/-/activities/date/" + date + ".json";
    }

    public static String getUrlStepsSeries(String date, String period) {
        return "https://api.fitbit.com/1/user/-/activities/steps/date/" + date + "/" + period + ".json";
    }
}
