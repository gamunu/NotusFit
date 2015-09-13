package com.notus.fit.models.api_models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/3/2015 9:59 AM
 */
@Parcel
public class WeekData {
    @SerializedName("average")
    int average;
    @SerializedName("end_date")
    String endDate;
    @SerializedName("friday")
    int friday;
    @SerializedName("id")
    int id;
    @SerializedName("monday")
    int monday;
    @SerializedName("saturday")
    int saturday;
    @SerializedName("start_date")
    String startDate;
    @SerializedName("sunday")
    int sunday;
    @SerializedName("thursday")
    int thursday;
    @SerializedName("tuesday")
    int tuesday;
    @SerializedName("user_w")
    User user;
    @SerializedName("wednesday")
    int wednesday;

    public int getId() {
        return this.id;
    }

    public WeekData setId(int id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public WeekData setUser(User user) {
        this.user = user;
        return this;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public WeekData setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public WeekData setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public int getMonday() {
        return this.monday;
    }

    public WeekData setMonday(int monday) {
        this.monday = monday;
        return this;
    }

    public int getTuesday() {
        return this.tuesday;
    }

    public WeekData setTuesday(int tuesday) {
        this.tuesday = tuesday;
        return this;
    }

    public int getWednesday() {
        return this.wednesday;
    }

    public WeekData setWednesday(int wednesday) {
        this.wednesday = wednesday;
        return this;
    }

    public int getThursday() {
        return this.thursday;
    }

    public WeekData setThursday(int thursday) {
        this.thursday = thursday;
        return this;
    }

    public int getFriday() {
        return this.friday;
    }

    public WeekData setFriday(int friday) {
        this.friday = friday;
        return this;
    }

    public int getSaturday() {
        return this.saturday;
    }

    public WeekData setSaturday(int saturday) {
        this.saturday = saturday;
        return this;
    }

    public int getSunday() {
        return this.sunday;
    }

    public WeekData setSunday(int sunday) {
        this.sunday = sunday;
        return this;
    }

    public int getAverage() {
        return this.average;
    }

    public WeekData setAverage(int average) {
        this.average = average;
        return this;
    }

    public List<Integer> getStepList() {
        List<Integer> stepList = new ArrayList<>();
        stepList.add(this.monday);
        stepList.add(this.tuesday);
        stepList.add(this.wednesday);
        stepList.add(this.thursday);
        stepList.add(this.friday);
        stepList.add(this.saturday);
        stepList.add(this.sunday);
        return stepList;
    }

    public WeekData setStepList(ArrayList<Integer> steps) {
        this.monday = ((Integer) steps.get(0)).intValue();
        this.tuesday = ((Integer) steps.get(1)).intValue();
        this.wednesday = ((Integer) steps.get(2)).intValue();
        this.thursday = ((Integer) steps.get(3)).intValue();
        this.friday = ((Integer) steps.get(4)).intValue();
        this.saturday = ((Integer) steps.get(5)).intValue();
        this.sunday = ((Integer) steps.get(6)).intValue();
        return this;
    }
}
