package com.notus.fit.network.weather.models;

import java.util.List;

// Referenced classes of package com.notus.fit.network.weather.models:
//            MainWeather

public class WeatherResponse {

    String base;
    int cod;
    long dt;
    int id;
    MainWeather mainWeather;
    String stillwater;
    List weather;

    public WeatherResponse() {
    }

    public String getBase() {
        return base;
    }

    public WeatherResponse setBase(String s) {
        base = s;
        return this;
    }

    public int getCod() {
        return cod;
    }

    public WeatherResponse setCod(int i) {
        cod = i;
        return this;
    }

    public long getDt() {
        return dt;
    }

    public WeatherResponse setDt(long l) {
        dt = l;
        return this;
    }

    public int getId() {
        return id;
    }

    public WeatherResponse setId(int i) {
        id = i;
        return this;
    }

    public MainWeather getMainWeather() {
        return mainWeather;
    }

    public WeatherResponse setMainWeather(MainWeather mainweather) {
        mainWeather = mainweather;
        return this;
    }

    public String getStillwater() {
        return stillwater;
    }

    public WeatherResponse setStillwater(String s) {
        stillwater = s;
        return this;
    }

    public List getWeather() {
        return weather;
    }

    public WeatherResponse setWeather(List list) {
        weather = list;
        return this;
    }
}
