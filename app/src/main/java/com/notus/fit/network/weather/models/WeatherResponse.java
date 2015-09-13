package com.notus.fit.network.weather.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {

    @SerializedName("base")
    String base;
    @SerializedName("cod")
    int cod;
    @SerializedName("dt")
    long dt;
    @SerializedName("id")
    int id;
    @SerializedName("main")
    MainWeather mainWeather;
    @SerializedName("name")
    String stillwater;
    @SerializedName("weather")
    List<Weather> weather;

    public List<Weather> getWeather() {
        return this.weather;
    }

    public WeatherResponse setWeather(List<Weather> weather) {
        this.weather = weather;
        return this;
    }

    public String getBase() {
        return this.base;
    }

    public WeatherResponse setBase(String base) {
        this.base = base;
        return this;
    }

    public MainWeather getMainWeather() {
        return this.mainWeather;
    }

    public WeatherResponse setMainWeather(MainWeather mainWeather) {
        this.mainWeather = mainWeather;
        return this;
    }

    public long getDt() {
        return this.dt;
    }

    public WeatherResponse setDt(long dt) {
        this.dt = dt;
        return this;
    }

    public int getId() {
        return this.id;
    }

    public WeatherResponse setId(int id) {
        this.id = id;
        return this;
    }

    public String getStillwater() {
        return this.stillwater;
    }

    public WeatherResponse setStillwater(String stillwater) {
        this.stillwater = stillwater;
        return this;
    }

    public int getCod() {
        return this.cod;
    }

    public WeatherResponse setCod(int cod) {
        this.cod = cod;
        return this;
    }
}
