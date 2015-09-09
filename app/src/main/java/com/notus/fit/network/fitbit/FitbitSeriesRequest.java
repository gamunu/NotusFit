package com.notus.fit.network.fitbit;

import com.notus.fit.models.DateTimeRequestObject;
import com.notus.fit.utils.TimeUtils;

import org.joda.time.LocalDate;

public class FitbitSeriesRequest
        implements DateTimeRequestObject {

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
        endDate = LocalDate.now().withDayOfWeek(7);
        period = "1w";
    }

    public FitbitSeriesRequest(LocalDate localdate, String s) {
        endDate = localdate;
        period = s;
    }

    public FitbitSeriesRequest getCurrentWeek() {
        return this;
    }

    public String getDate() {
        String s = TimeUtils.addZero(endDate.getMonthOfYear());
        String s1 = TimeUtils.addZero(endDate.getDayOfMonth());
        date = (new StringBuilder()).append(endDate.getYear()).append("-").append(s).append("-").append(s1).toString();
        return date;
    }

    public void setDate(String s) {
        date = s;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate localdate) {
        endDate = localdate;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String s) {
        period = s;
    }

    public FitbitSeriesRequest getPreviousWeek() {
        endDate = LocalDate.now().withDayOfWeek(7).minusWeeks(1);
        period = "1w";
        return this;
    }
}
