package com.notus.fit.models;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

/**
 * Created by VBALAUD on 9/3/2015.
 */
public class ReportDate {
    public static final int FRIDAY = 5;
    public static final int MONDAY = 1;
    public static final int SATURDAY = 6;
    public static final int SUNDAY = 7;
    public static final int THURSDAY = 4;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static String[] weekdays;

    static {
        String[] strArr = new String[SUNDAY];
        strArr[0] = "Mo";
        strArr[MONDAY] = "Tu";
        strArr[TUESDAY] = "We";
        strArr[WEDNESDAY] = "Th";
        strArr[THURSDAY] = "Fr";
        strArr[FRIDAY] = "Sa";
        strArr[SATURDAY] = "Su";
        weekdays = strArr;
    }

    protected LocalDate date;
    protected String fullDateString;
    protected int weekDayNum;
    protected String weekDayShort;

    public ReportDate(LocalDate date) {
        LocalDate date2 = new LocalDate(date);
        this.fullDateString = DateTimeFormat.forPattern("MMM dd, YYYY").print(date2);
        this.weekDayNum = date2.dayOfWeek().get();
        this.weekDayShort = weekdays[this.weekDayNum - 1];
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getWeekDayShort() {
        return this.weekDayShort;
    }

    public ReportDate setWeekDayShort(String weekDayShort) {
        this.weekDayShort = weekDayShort;
        return this;
    }

    public String getFullDateString() {
        return this.fullDateString;
    }

    public ReportDate setFullDateString(String fullDateString) {
        this.fullDateString = fullDateString;
        return this;
    }

    public int getWeekDayNum() {
        return this.weekDayNum;
    }

    public void setWeekDayNum(int weekDayNum) {
        this.weekDayNum = weekDayNum;
    }
}
