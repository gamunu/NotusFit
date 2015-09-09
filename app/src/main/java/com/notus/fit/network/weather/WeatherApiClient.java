package com.notus.fit.network.weather;

import android.content.Context;

import com.notus.fit.network.Client;
import com.notus.fit.network.weather.models.WeatherResponse;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import rx.Observable;

public class WeatherApiClient {
    public static final String API_ENDPOINT = "http://api.openweathermap.org";
    public static final String API_KEY = "af5f4d9a4b49d9d8804ea7ad122cb51e";

    public WeatherApiClient() {
    }

    public static RestAdapter getBaseRestAdapter(Context context) {
        retrofit.RestAdapter.Builder builder = new retrofit.RestAdapter.Builder();
        builder.setClient(new OkClient(Client.getInstance(context)));
        builder.setEndpoint("http://api.openweathermap.org");
        return builder.build();
    }

    public static interface WeatherApi {

        public abstract Observable getRxWeather(float f, float f1, String s, String s1);

        public abstract WeatherResponse getWeather(float f, float f1, String s, String s1);
    }
}
