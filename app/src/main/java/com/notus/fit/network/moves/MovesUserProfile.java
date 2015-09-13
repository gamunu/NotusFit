package com.notus.fit.network.moves;


import com.google.gson.annotations.SerializedName;

public class MovesUserProfile {
    @SerializedName("firstDate")
    String firstDate;

    public String getFirstDate() {
        return this.firstDate;
    }

    public void setFirstDate(String firstDate) {
        this.firstDate = firstDate;
    }

    public String toString() {
        return "MovesUserProfile{firstDate='" + this.firstDate + '\'' + '}';
    }
}
