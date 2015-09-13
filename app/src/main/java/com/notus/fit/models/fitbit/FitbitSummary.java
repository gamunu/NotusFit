package com.notus.fit.models.fitbit;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/3/2015 10:00 AM
 */
@Parcel
public class FitbitSummary {
    @SerializedName("activityCalories")
    int activityCalories;
    @SerializedName("activityScore")
    int activityScore;
    @SerializedName("caloriesBMR")
    int caloriesBMR;
    @SerializedName("caloriesOut")
    int caloriesOut;
    @SerializedName("fairlyActiveMinutes")
    int fairlyActiveMinutes;
    @SerializedName("lightlyActiveMinutes")
    int lightlyActiveMinutes;
    @SerializedName("marginalCalories")
    int marginalCalories;
    @SerializedName("sedentaryMinutes")
    int sedentaryMinutes;
    @SerializedName("steps")
    int steps;
    @SerializedName("veryActiveMinutes")
    int veryActiveMinutes;

    public int getActivityScore() {
        return this.activityScore;
    }

    public void setActivityScore(int activityScore) {
        this.activityScore = activityScore;
    }

    public int getActivityCalories() {
        return this.activityCalories;
    }

    public void setActivityCalories(int activityCalories) {
        this.activityCalories = activityCalories;
    }

    public int getCaloriesBMR() {
        return this.caloriesBMR;
    }

    public void setCaloriesBMR(int caloriesBMR) {
        this.caloriesBMR = caloriesBMR;
    }

    public int getCaloriesOut() {
        return this.caloriesOut;
    }

    public void setCaloriesOut(int caloriesOut) {
        this.caloriesOut = caloriesOut;
    }

    public int getFairlyActiveMinutes() {
        return this.fairlyActiveMinutes;
    }

    public void setFairlyActiveMinutes(int fairlyActiveMinutes) {
        this.fairlyActiveMinutes = fairlyActiveMinutes;
    }

    public int getLightlyActiveMinutes() {
        return this.lightlyActiveMinutes;
    }

    public void setLightlyActiveMinutes(int lightlyActiveMinutes) {
        this.lightlyActiveMinutes = lightlyActiveMinutes;
    }

    public int getMarginalCalories() {
        return this.marginalCalories;
    }

    public void setMarginalCalories(int marginalCalories) {
        this.marginalCalories = marginalCalories;
    }

    public int getSedentaryMinutes() {
        return this.sedentaryMinutes;
    }

    public void setSedentaryMinutes(int sedentaryMinutes) {
        this.sedentaryMinutes = sedentaryMinutes;
    }

    public int getSteps() {
        return this.steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getVeryActiveMinutes() {
        return this.veryActiveMinutes;
    }

    public void setVeryActiveMinutes(int veryActiveMinutes) {
        this.veryActiveMinutes = veryActiveMinutes;
    }

    public String toString() {
        return "FitbitSummary{activityScore=" + this.activityScore + ", activityCalories=" + this.activityCalories + ", caloriesBMR=" + this.caloriesBMR + ", caloriesOut=" + this.caloriesOut + ", fairlyActiveMinutes=" + this.fairlyActiveMinutes + ", lightlyActiveMinutes=" + this.lightlyActiveMinutes + ", marginalCalories=" + this.marginalCalories + ", sedentaryMinutes=" + this.sedentaryMinutes + ", steps=" + this.steps + ", veryActiveMinutes=" + this.veryActiveMinutes + '}';
    }
}
