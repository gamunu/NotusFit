package com.notus.fit.models.api_models;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by VBALAUD on 9/3/2015.
 */
@Parcel
public class WeekData {

    int average;
    String endDate;
    int friday;
    int id;
    int monday;
    int saturday;
    String startDate;
    int sunday;
    int thursday;
    int tuesday;
    User user;
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

    public ArrayList<Integer> getStepList() {
        ArrayList<Integer> stepList = new ArrayList();
        stepList.add(Integer.valueOf(this.monday));
        stepList.add(Integer.valueOf(this.tuesday));
        stepList.add(Integer.valueOf(this.wednesday));
        stepList.add(Integer.valueOf(this.thursday));
        stepList.add(Integer.valueOf(this.friday));
        stepList.add(Integer.valueOf(this.saturday));
        stepList.add(Integer.valueOf(this.sunday));
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
