package com.notus.fit.network.weather.models;


import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("description")
    String description;
    @SerializedName("icon")
    String icon;
    @SerializedName("id")
    int id;
    @SerializedName("main")
    String main;

    public int getId() {
        return this.id;
    }

    public Weather setId(int id) {
        this.id = id;
        return this;
    }

    public String getMain() {
        return this.main;
    }

    public Weather setMain(String main) {
        this.main = main;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public Weather setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getIcon() {
        return this.icon;
    }

    public Weather setIcon(String icon) {
        this.icon = icon;
        return this;
    }
}