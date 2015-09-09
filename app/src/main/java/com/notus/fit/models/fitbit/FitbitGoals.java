package com.notus.fit.models.fitbit;

import org.parceler.Parcel;

/**
 * Created by VBALAUD on 9/3/2015.
 */
@Parcel
public class FitbitGoals {
    int activeMinutes;
    int caloriesOut;
    float distance;
    int steps;

    public int getActiveMinutes() {
        return this.activeMinutes;
    }

    public void setActiveMinutes(int activeMinutes) {
        this.activeMinutes = activeMinutes;
    }

    public int getCaloriesOut() {
        return this.caloriesOut;
    }

    public void setCaloriesOut(int caloriesOut) {
        this.caloriesOut = caloriesOut;
    }

    public float getDistance() {
        return this.distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getSteps() {
        return this.steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public String toString() {
        return "FitbitGoals{activeMinutes=" + this.activeMinutes + ", caloriesOut=" + this.caloriesOut + ", distance=" + this.distance + ", steps=" + this.steps + '}';
    }
}

