package com.notus.fit.models;

import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.parceler.Parcel;
import org.parceler.Transient;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/3/2015 4:59 PM
 */
@Parcel
public class RequestTime implements DateTimeRequestObject {
    @Transient
    public static final String REQUEST_TIME = "request_time";
    long endTime;
    List<String> fullDates;
    List<String> localDates;
    List<String> months;
    long startTime;
    List<Integer> weekDays;

    public RequestTime() {

    }

    public RequestTime(long startTime, long endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.weekDays = new ArrayList<>();
        this.months = new ArrayList<>();
        this.fullDates = new ArrayList<>();
        this.localDates = new ArrayList<>();
        LocalDate startDate = new LocalDate(startTime);
        int days = Days.daysBetween(startDate, new LocalDate(endTime)).getDays() + 1;
        for (int i = 0; i < days; i++) {
            LocalDate d = startDate.withFieldAdded(DurationFieldType.days(), i);
            ReportDate reportDate = new ReportDate(d);
            this.weekDays.add(d.getDayOfMonth());
            this.localDates.add(d.toString());
            this.months.add(DateTimeFormat.forPattern("MMM").print(d));
            this.fullDates.add(reportDate.getFullDateString());
        }
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public List<String> getFullDates() {
        return this.fullDates;
    }

    public List<String> getMonths() {
        return this.months;
    }

    public List<Integer> getDays() {
        return this.weekDays;
    }

    public List<String> getLocalDates() {
        return this.localDates;
    }

    public void setLocalDates(List<String> localDates) {
        this.localDates = localDates;
    }
}