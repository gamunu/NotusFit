package com.notus.fit.models;

/**
 * Created by VBALAUD on 9/3/2015.
 */
public class DayReport {
    public static final int FRIDAY = 4;
    public static final int MONDAY = 0;
    public static final int SATURDAY = 5;
    public static final int SUNDAY = 6;
    public static final int THURSDAY = 3;
    public static final int TUESDAY = 1;
    public static final int WEDNESDAY = 2;
    private int day;
    private int dayOfWeek;
    private String fullDate;
    private String month;
    private int steps;
    private String weekDay;

    public DayReport(int steps) {
        this.steps = steps;
    }

    public DayReport() {

    }

    public int getSteps() {
        return this.steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getWeekDay() {
        return this.weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getMonth() {
        return this.month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getFullDate() {
        return this.fullDate;
    }

    public void setFullDate(String fullDate) {
        this.fullDate = fullDate;
    }

    public int getDayOfWeek() {
        return this.dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String toString() {
        return "DayReport{dayOfWeek=" + this.dayOfWeek + ", fullDate='" + this.fullDate + '\'' + ", month='" + this.month + '\'' + ", weekDay='" + this.weekDay + '\'' + ", day=" + this.day + ", steps=" + this.steps + '}';
    }
}
