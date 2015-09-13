package com.notus.fit.network.weather.models;


import com.google.gson.annotations.SerializedName;

public class MainWeather {

    @SerializedName("grnd_level")
    float groundLevel;
    @SerializedName("humidity")
    float humidity;
    @SerializedName("pressure")
    float pressure;
    @SerializedName("sea_level")
    float seaLevel;
    @SerializedName("temp_min")
    float temMin;
    @SerializedName("temp")
    float temp;
    @SerializedName("temp_max")
    float tempMax;

    public float getTemp() {
        return this.temp;
    }

    public MainWeather setTemp(float temp) {
        this.temp = temp;
        return this;
    }

    public float getTemMin() {
        return this.temMin;
    }

    public MainWeather setTemMin(float temMin) {
        this.temMin = temMin;
        return this;
    }

    public float getTempMax() {
        return this.tempMax;
    }

    public MainWeather setTempMax(float tempMax) {
        this.tempMax = tempMax;
        return this;
    }

    public float getPressure() {
        return this.pressure;
    }

    public MainWeather setPressure(float pressure) {
        this.pressure = pressure;
        return this;
    }

    public float getSeaLevel() {
        return this.seaLevel;
    }

    public MainWeather setSeaLevel(float seaLevel) {
        this.seaLevel = seaLevel;
        return this;
    }

    public float getGroundLevel() {
        return this.groundLevel;
    }

    public MainWeather setGroundLevel(float groundLevel) {
        this.groundLevel = groundLevel;
        return this;
    }

    public float getHumidity() {
        return this.humidity;
    }

    public MainWeather setHumidity(float humidity) {
        this.humidity = humidity;
        return this;
    }
}
