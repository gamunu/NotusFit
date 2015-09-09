package com.notus.fit.models.misfit;

import org.parceler.Parcel;

/**
 * Created by VBALAUD on 9/3/2015.
 */
@Parcel
public class MisfitDay {
    float activityCalories;
    String date;
    float distance;
    float points;
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