package com.notus.fit.models.fitbit;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/3/2015 9:59 AM
 */
@Parcel
public class FitbitDistance {
    @SerializedName("distance")
    float distance;
    @SerializedName("activity")
    String total;


    public String getTotal() {
        return this.total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public float getDistance() {
        return this.distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String toString() {
        return "FitbitDistance{total='" + this.total + '\'' + ", distance=" + this.distance + '}';
    }
}
