package com.notus.fit.models.fitbit;

import org.parceler.Parcel;

/**
 * Created by VBALAUD on 9/3/2015.
 */
@Parcel
public class FitbitDailyStep {
    String dateTime;
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
