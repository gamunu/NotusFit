package com.jawbone.upplatformsdk.endpointModels.move;

import com.google.gson.annotations.SerializedName;

public class HourlyEvent {
    @SerializedName("calories")
    Float calories;
    @SerializedName("distance")
    Float distance;
    @SerializedName("inactive_time")
    Integer inactiveTime;
    @SerializedName("longest_active_time")
    Integer longestActiveTime;
    @SerializedName("longest_idle_time")
    Integer longestIdleTime;
    @SerializedName("steps")
    Integer steps;

    public Float getDistance() {
        return this.distance;
    }

    public Float getCalories() {
        return this.calories;
    }

    public void setCalories(Float calories) {
        this.calories = calories;
    }

    public Integer getSteps() {
        return this.steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    public Integer getInactiveTime() {
        return this.inactiveTime;
    }

    public void setInactiveTime(Integer inactiveTime) {
        this.inactiveTime = inactiveTime;
    }

    public Integer getLongestActiveTime() {
        return this.longestActiveTime;
    }

    public void setLongestActiveTime(Integer longestActiveTime) {
        this.longestActiveTime = longestActiveTime;
    }

    public Integer getLongestIdleTime() {
        return this.longestIdleTime;
    }

    public void setLongestIdleTime(Integer longestIdleTime) {
        this.longestIdleTime = longestIdleTime;
    }
}
