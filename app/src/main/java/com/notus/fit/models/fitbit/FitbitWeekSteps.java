package com.notus.fit.models.fitbit;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/3/2015 10:00 AM
 */
@Parcel
public class FitbitWeekSteps {
    @SerializedName("activities-steps")
    List<FitbitDailyStep> fitbitDailySteps;

    public List<FitbitDailyStep> getFitbitDailySteps() {
        return this.fitbitDailySteps;
    }

    public void setFitbitDailySteps(List<FitbitDailyStep> fitbitDailySteps) {
        this.fitbitDailySteps = fitbitDailySteps;
    }
}
