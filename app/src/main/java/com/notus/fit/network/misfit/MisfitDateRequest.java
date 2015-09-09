package com.notus.fit.network.misfit;


public class MisfitDateRequest {

    public static final String MISFIT_DATE = "misfit_date_request";
    long endDate;
    long startDate;

    public MisfitDateRequest() {
    }

    public MisfitDateRequest(long l, long l1) {
        startDate = l;
        endDate = l1;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long l) {
        endDate = l;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long l) {
        startDate = l;
    }
}