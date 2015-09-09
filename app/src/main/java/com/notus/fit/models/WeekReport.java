package com.notus.fit.models;

import android.content.Context;

import com.notus.fit.utils.PrefManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by VBALAUD on 9/3/2015.
 */
public class WeekReport {
    protected static final String LOG_TAG = WeekReport.class.getSimpleName();
    protected ArrayList<DayReport> days;
    protected String device;
    protected DayReport friday;
    protected DayReport monday;
    protected int realListSize;
    protected DayReport saturday;
    protected ArrayList<Integer> stepList;
    protected DayReport sunday;
    protected DayReport thursday;
    protected DayReport tuesday;
    protected DayReport wednesday;
    protected int weekAverage;
    protected long weekStart;

    public WeekReport() {
        this.realListSize = 0;
        this.weekAverage = 0;
        this.days = new ArrayList();
        this.stepList = new ArrayList();
    }


    public WeekReport(ArrayList<DayReport> days, ArrayList<Integer> stepList, int weekAverage, int realListSize, String device) {
        this.realListSize = 0;
        this.weekAverage = 0;
        this.days = days;
        this.stepList = stepList;
        this.weekAverage = weekAverage;
        this.realListSize = realListSize;
        this.device = device;
        for (int i = 0; i < days.size(); i++) {
            if (i == 0) {
                try {
                    this.monday = (DayReport) days.get(i);
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
            if (i == 1) {
                try {
                    this.tuesday = (DayReport) days.get(i);
                } catch (ArrayIndexOutOfBoundsException e2) {
                }
            }
            if (i == 2) {
                try {
                    this.wednesday = (DayReport) days.get(i);
                } catch (ArrayIndexOutOfBoundsException e3) {
                }
            }
            if (i == 3) {
                try {
                    this.thursday = (DayReport) days.get(i);
                } catch (ArrayIndexOutOfBoundsException e4) {
                }
            }
            if (i == 4) {
                try {
                    this.friday = (DayReport) days.get(i);
                } catch (ArrayIndexOutOfBoundsException e5) {
                }
            }
            if (i == 5) {
                try {
                    this.saturday = (DayReport) days.get(i);
                } catch (ArrayIndexOutOfBoundsException e6) {
                }
            }
            if (i == 6) {
                try {
                    this.sunday = (DayReport) days.get(i);
                } catch (ArrayIndexOutOfBoundsException e7) {
                }
            }
        }
    }

    public static ArrayList<String> getWeekLabels() {
        ArrayList<String> weekLabels = new ArrayList();
        weekLabels.add("Mon");
        weekLabels.add("Tue");
        weekLabels.add("Wed");
        weekLabels.add("Thu");
        weekLabels.add("Fri");
        weekLabels.add("Sat");
        weekLabels.add("Sun");
        return weekLabels;
    }

    public String getDevice() {
        return this.device;
    }

    public ArrayList<DayReport> getDays() {
        return this.days;
    }

    public ArrayList<Integer> getStepList() {
        return this.stepList;
    }

    public int getWeekAverage() {
        return this.weekAverage;
    }

    public int getRealListSize() {
        return this.realListSize;
    }

    public String toString() {
        return "WeekReport{sunday=" + this.sunday + ", saturday=" + this.saturday + ", friday=" + this.friday + ", thursday=" + this.thursday + ", wednesday=" + this.wednesday + ", tuesday=" + this.tuesday + ", monday=" + this.monday + ", weekAverage=" + this.weekAverage + ", weekStart=" + this.weekStart + ", device='" + this.device + '\'' + ", realListSize=" + this.realListSize + ", stepList=" + this.stepList + ", days=" + this.days + '}';
    }

    public static class Builder {
        protected HashMap<Integer, DayReport> days;
        private Context context;
        private String device;
        private ArrayList<Integer> stepList;
        private int weekAverage;

        public Builder(Context context) {
            this.weekAverage = 0;
            this.context = context;
            this.days = new HashMap();
            this.stepList = new ArrayList();
        }

        public Builder setDays(HashMap<Integer, DayReport> days) {
            this.days = days;
            return this;
        }

        public Builder setDevice(String device) {
            this.device = device;
            return this;
        }

        public WeekReport build() {
            DayReport d;
            int sum = 0;
            int realListSize = 0;
            ArrayList<DayReport> reports = new ArrayList();
            for (int i = 0; i < 7; i++) {
                try {
                    d = (DayReport) this.days.get(Integer.valueOf(i + 1));
                    sum += d.getSteps();
                    this.stepList.add(Integer.valueOf(d.getSteps()));
                    if (d.getSteps() != 0) {
                        realListSize++;
                    }
                    reports.add(d);
                } catch (Exception e) {
                    d = new DayReport();
                    d.setSteps(0);
                    d.setWeekDay(ReportDate.weekdays[i]);
                    this.stepList.add(Integer.valueOf(0));
                    reports.add(d);
                }
            }
            if (realListSize == 0) {
                this.weekAverage = sum;
            } else if (PrefManager.with(this.context).getBoolean("should_average", true)) {
                this.weekAverage = sum / realListSize;
            } else {
                this.weekAverage = sum;
            }
            return new WeekReport(reports, this.stepList, this.weekAverage, realListSize, this.device);
        }
    }
}
