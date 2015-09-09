package com.notus.fit.models.fitbit;

import org.parceler.Parcel;

/**
 * Created by VBALAUD on 9/3/2015.
 */
@Parcel
public class FitbitDistance {
    float distance;
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
