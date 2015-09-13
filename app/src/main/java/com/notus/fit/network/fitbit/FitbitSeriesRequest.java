package com.notus.fit.network.fitbit;

import com.notus.fit.models.DateTimeRequestObject;
import com.notus.fit.utils.TimeUtils;

import org.joda.time.LocalDate;

public class FitbitSeriesRequest implements DateTimeRequestObject {

    public static final String MAX_PERIOD = "max";
    public static final String MONTH_PERIOD = "1m";
    public static final String SIX_MONTH_PERIOD = "6m";
    public static final String THREE_MONTH_PERIOD = "3m";
    public static final String WEEK_PERIOD = "1w";
    public static final String YEAR_PERIOD = "1y";
    private String date;
    private LocalDate endDate;
    private String period;

    public FitbitSeriesRequest() {
        this.endDate = LocalDate.now().withDayOfWeek(7);
        this.period = WEEK_PERIOD;
    }

    public FitbitSeriesRequest(LocalDate endDate, String period) {
        this.endDate = endDate;
        this.period = period;
    }

    public String getDate() {
        String month = TimeUtils.addZero(this.endDate.getMonthOfYear());
        this.date = this.endDate.getYear() + "-" + month + "-" + TimeUtils.addZero(this.endDate.getDayOfMonth());
        return this.date;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public FitbitSeriesRequest getCurrentWeek() {
        return this;
    }

    public FitbitSeriesRequest getPreviousWeek() {
        this.endDate = LocalDate.now().withDayOfWeek(7).minusWeeks(1);
        this.period = WEEK_PERIOD;
        return this;
    }
}