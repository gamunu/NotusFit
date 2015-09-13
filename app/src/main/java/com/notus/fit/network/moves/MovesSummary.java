package com.notus.fit.network.moves;


import com.google.gson.annotations.SerializedName;

public class MovesSummary {

    @SerializedName("activity")
    String activity;
    @SerializedName("calories")
    int calories;
    @SerializedName("distance")
    int distance;
    @SerializedName("duration")
    int duration;
    @SerializedName("group")
    String group;
    @SerializedName("steps")
    int steps;

    public String getActivity() {
        return this.activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getSteps() {
        return this.steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getCalories() {
        return this.calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String toString() {
        return "MovesSummary{activity='" + this.activity + '\'' + ", group='" + this.group + '\'' + ", duration=" + this.duration + ", distance=" + this.distance + ", steps=" + this.steps + ", calories=" + this.calories + '}';
    }
}
