package com.notus.fit.network.weather.models;


public class Weather {

    String description;
    String icon;
    int id;
    String main;

    public Weather() {
    }

    public String getDescription() {
        return description;
    }

    public Weather setDescription(String s) {
        description = s;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public Weather setIcon(String s) {
        icon = s;
        return this;
    }

    public int getId() {
        return id;
    }

    public Weather setId(int i) {
        id = i;
        return this;
    }

    public String getMain() {
        return main;
    }

    public Weather setMain(String s) {
        main = s;
        return this;
    }
}
