package com.notus.fit.models.fitbit;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by VBALAUD on 9/3/2015.
 */
@Parcel
public class FitbitWeekSteps {
    List<FitbitDailyStep> fitbitDailySteps;

    public List<FitbitDailyStep> getFitbitDailySteps() {
        return this.fitbitDailySteps;
    }

    public void setFitbitDailySteps(List<FitbitDailyStep> fitbitDailySteps) {
        this.fitbitDailySteps = fitbitDailySteps;
    }
}
