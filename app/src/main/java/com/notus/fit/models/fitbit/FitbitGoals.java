package com.notus.fit.models.fitbit;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/3/2015 9:59 AM
 */
@Parcel
public class FitbitGoals {
    @SerializedName("activeMinutes")
    int activeMinutes;
    @SerializedName("caloriesOut")
    int caloriesOut;
    @SerializedName("distance")
    float distance;
    @SerializedName("steps")
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

