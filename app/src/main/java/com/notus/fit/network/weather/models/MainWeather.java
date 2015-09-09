package com.notus.fit.network.weather.models;


public class MainWeather {

    float groundLevel;
    float humidity;
    float pressure;
    float seaLevel;
    float temMin;
    float temp;
    float tempMax;

    public MainWeather() {
    }

    public float getGroundLevel() {
        return groundLevel;
    }

    public MainWeather setGroundLevel(float f) {
        groundLevel = f;
        return this;
    }

    public float getHumidity() {
        return humidity;
    }

    public MainWeather setHumidity(float f) {
        humidity = f;
        return this;
    }

    public float getPressure() {
        return pressure;
    }

    public MainWeather setPressure(float f) {
        pressure = f;
        return this;
    }

    public float getSeaLevel() {
        return seaLevel;
    }

    public MainWeather setSeaLevel(float f) {
        seaLevel = f;
        return this;
    }

    public float getTemMin() {
        return temMin;
    }

    public MainWeather setTemMin(float f) {
        temMin = f;
        return this;
    }

    public float getTemp() {
        return temp;
    }

    public MainWeather setTemp(float f) {
        temp = f;
        return this;
    }

    public float getTempMax() {
        return tempMax;
    }

    public MainWeather setTempMax(float f) {
        tempMax = f;
        return this;
    }
}
