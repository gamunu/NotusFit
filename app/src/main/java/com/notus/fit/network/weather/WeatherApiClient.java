package com.notus.fit.network.weather;

import android.content.Context;

import com.notus.fit.network.Client;
import com.notus.fit.network.weather.models.WeatherResponse;

import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import retrofit.client.OkClient;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;


public class WeatherApiClient {
    public static final String API_ENDPOINT = "http://api.openweathermap.org";
    public static final String API_KEY = "af5f4d9a4b49d9d8804ea7ad122cb51e";

    public interface WeatherApi {
        @GET("/data/2.5/weather")
        Observable<WeatherResponse> getRxWeather(@Query("lat") float f, @Query("lon") float f2, @Query("units") String str, @Query("appid") String str2);

        @GET("/data/2.5/weather")
        WeatherResponse getWeather(@Query("lat") float f, @Query("lon") float f2, @Query("units") String str, @Query("appid") String str2);
    }

    public static RestAdapter getBaseRestAdapter(Context context) {
        Builder builder = new Builder();
        builder.setClient(new OkClient(Client.getInstance(context)));
        builder.setEndpoint(API_ENDPOINT);
        return builder.build();
    }
}
