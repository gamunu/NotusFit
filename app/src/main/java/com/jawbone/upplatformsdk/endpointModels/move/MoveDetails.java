package com.jawbone.upplatformsdk.endpointModels.move;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class MoveDetails {
    @SerializedName("bg_calories")
    Float bgCalories;
    @SerializedName("bmr_day")
    Float bmrDay;
    @SerializedName("calories")
    Float calories;
    @SerializedName("distance")
    Float distance;
    @SerializedName("hourly_totals")
    Map<String, HourlyEvent> hourlyTotals;
    @SerializedName("inactive_time")
    Integer inactiveTime;
    @SerializedName("km")
    String km;
    @SerializedName("longest_active")
    Integer longestActive;
    @SerializedName("longest_idle")
    Integer longestIdle;
    @SerializedName("steps")
    Integer steps;
    @SerializedName("sunrise")
    Long sunrise;
    @SerializedName("sunset")
    Long sunset;
    @SerializedName("tz")
    String timeZone;
    @SerializedName("tzs")
    Map<Integer, String> tzs;
    @SerializedName("wo_active_time")
    Integer woActiveTime;
    @SerializedName("wo_calories")
    Float woCalories;
    @SerializedName("wo_count")
    Integer woCount;
    @SerializedName("wo_longest")
    Integer woLongest;
    @SerializedName("wo_time")
    Integer woTime;

    public Float getDistance() {
        return this.distance;
    }

    public String getKm() {
        return this.km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public Integer getSteps() {
        return this.steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    public Integer getLongestActive() {
        return this.longestActive;
    }

    public void setLongestActive(Integer longestActive) {
        this.longestActive = longestActive;
    }

    public Integer getInactiveTime() {
        return this.inactiveTime;
    }

    public void setInactiveTime(Integer inactiveTime) {
        this.inactiveTime = inactiveTime;
    }

    public Integer getLongestIdle() {
        return this.longestIdle;
    }

    public void setLongestIdle(Integer longestIdle) {
        this.longestIdle = longestIdle;
    }

    public Float getCalories() {
        return this.calories;
    }

    public void setCalories(Float calories) {
        this.calories = calories;
    }

    public Float getBmrDay() {
        return this.bmrDay;
    }

    public void setBmrDay(Float bmrDay) {
        this.bmrDay = bmrDay;
    }

    public Float getBgCalories() {
        return this.bgCalories;
    }

    public void setBgCalories(Float bgCalories) {
        this.bgCalories = bgCalories;
    }

    public Float getWoCalories() {
        return this.woCalories;
    }

    public void setWoCalories(Float woCalories) {
        this.woCalories = woCalories;
    }

    public Integer getWoTime() {
        return this.woTime;
    }

    public void setWoTime(Integer woTime) {
        this.woTime = woTime;
    }

    public Integer getWoActiveTime() {
        return this.woActiveTime;
    }

    public void setWoActiveTime(Integer woActiveTime) {
        this.woActiveTime = woActiveTime;
    }

    public Integer getWoCount() {
        return this.woCount;
    }

    public void setWoCount(Integer woCount) {
        this.woCount = woCount;
    }

    public Integer getWoLongest() {
        return this.woLongest;
    }

    public void setWoLongest(Integer woLongest) {
        this.woLongest = woLongest;
    }

    public Long getSunrise() {
        return this.sunrise;
    }

    public void setSunrise(Long sunrise) {
        this.sunrise = sunrise;
    }

    public Long getSunset() {
        return this.sunset;
    }

    public void setSunset(Long sunset) {
        this.sunset = sunset;
    }

    public String getTimeZone() {
        return this.timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Map<Integer, String> getTzs() {
        return this.tzs;
    }

    public void setTzs(Map<Integer, String> tzs) {
        this.tzs = tzs;
    }

    public Map<String, HourlyEvent> getHourlyTotals() {
        return this.hourlyTotals;
    }

    public void setHourlyTotals(Map<String, HourlyEvent> hourlyTotals) {
        this.hourlyTotals = hourlyTotals;
    }
}
