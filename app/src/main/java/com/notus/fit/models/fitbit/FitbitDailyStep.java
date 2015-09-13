package com.notus.fit.models.fitbit;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/3/2015 9:59 AM
 */
@Parcel
public class FitbitDailyStep {
    @SerializedName("dateTime")
    String dateTime;
    @SerializedName("value")
    String value;

    public String getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        return "FitbitDailyStep{dateTime='" + this.dateTime + '\'' + ", value='" + this.value + '\'' + '}';
    }
}
