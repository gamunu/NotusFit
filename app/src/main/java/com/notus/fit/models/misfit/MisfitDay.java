package com.notus.fit.models.misfit;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/3/2015 8:54 AM
 */
@Parcel
public class MisfitDay {
    @SerializedName("activity_calories")
    float activityCalories;
    @SerializedName("date")
    String date;
    @SerializedName("distance")
    float distance;
    @SerializedName("points")
    float points;
    @SerializedName("steps")
    Integer steps;
    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getPoints() {
        return this.points;
    }

    public void setPoints(float points) {
        this.points = points;
    }

    public Integer getSteps() {
        return this.steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    public float getActivityCalories() {
        return this.activityCalories;
    }

    public void setActivityCalories(float activityCalories) {
        this.activityCalories = activityCalories;
    }

    public float getDistance() {
        return this.distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}