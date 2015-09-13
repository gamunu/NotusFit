package com.notus.fit.models;


import android.content.Context;

import com.notus.fit.utils.Devices;
import com.notus.fit.utils.MathUtils;
import com.notus.fit.utils.PrefManager;

import java.util.ArrayList;
import java.util.List;

public class AllDevicesWeekReport extends WeekReport {
    protected WeekReport mFitbitWeekReport;
    protected WeekReport mGoogleFitWeekReport;
    protected WeekReport mJawboneWeekReport;
    protected WeekReport mMovesWeekReport;

    public WeekReport getmGoogleFitWeekReport() {
        return this.mGoogleFitWeekReport;
    }

    public void setmGoogleFitWeekReport(WeekReport mGoogleFitWeekReport) {
        this.mGoogleFitWeekReport = mGoogleFitWeekReport;
    }

    public WeekReport getmFitbitWeekReport() {
        return this.mFitbitWeekReport;
    }

    public void setmFitbitWeekReport(WeekReport mFitbitWeekReport) {
        this.mFitbitWeekReport = mFitbitWeekReport;
    }

    public WeekReport getmJawboneWeekReport() {
        return this.mJawboneWeekReport;
    }

    public void setmJawboneWeekReport(WeekReport mJawboneWeekReport) {
        this.mJawboneWeekReport = mJawboneWeekReport;
    }

    public static class Builder {
        private Context context;
        private WeekReport fitbitWeekReport;
        private WeekReport googleFitWeekReport;
        private WeekReport jawboneWeekReport;
        private WeekReport misfitWeekReport;
        private WeekReport movesWeekReport;

        public Builder(Context context) {
            this.context = context;
            this.googleFitWeekReport = new WeekReport();
            this.fitbitWeekReport = new WeekReport();
            this.jawboneWeekReport = new WeekReport();
            this.misfitWeekReport = new WeekReport();
            this.movesWeekReport = new WeekReport();
        }

        public Builder setGoogleFitWeekReport(WeekReport googleFitWeekReport) {
            this.googleFitWeekReport = googleFitWeekReport;
            return this;
        }

        public Builder setFitbitWeekReport(WeekReport fitbitWeekReport) {
            this.fitbitWeekReport = fitbitWeekReport;
            return this;
        }

        public Builder setJawboneWeekReport(WeekReport jawboneWeekReport) {
            this.jawboneWeekReport = jawboneWeekReport;
            return this;
        }

        public Builder setMisfitWeekReport(WeekReport misfitWeekReport) {
            this.misfitWeekReport = misfitWeekReport;
            return this;
        }

        public Builder setMovesWeekReport(WeekReport movesWeekReport) {
            this.movesWeekReport = movesWeekReport;
            return this;
        }

        public WeekReport build() {
            List<DayReport> dayReports = new ArrayList<>();
            List<Integer> stepList = new ArrayList<>();
            int weekAverage = 0;
            int size = MathUtils.max(new int[]{this.googleFitWeekReport.getRealListSize(), this.fitbitWeekReport.getRealListSize(), this.jawboneWeekReport.getRealListSize(), this.misfitWeekReport.getRealListSize(), this.movesWeekReport.getRealListSize()});
            int fitSize = googleFitWeekReport.getRealListSize();
            int fitbitSize = fitbitWeekReport.getRealListSize();
            int jawboneSize = jawboneWeekReport.getRealListSize();
            int misfitSize = misfitWeekReport.getRealListSize();
            int movesSize = movesWeekReport.getRealListSize();


            int fitbitCounter = 0;
            int fitCounter = 0;
            int jawboneCounter = 0;
            int misfitCounter = 0;
            int movesCounter = 0;
            for (int i = 0; i < 7; i++) {
                int dayAverage = 0;
                int numDevices = 0;
                String weekDay = null;
                String fullDay = null;
                if (fitSize > fitCounter) {
                    try {
                        weekDay = this.googleFitWeekReport.getDays().get(i).getWeekDay();
                        fullDay = this.googleFitWeekReport.getDays().get(i).getFullDate();
                        dayAverage = this.googleFitWeekReport.getDays().get(i).getSteps();
                        if (this.googleFitWeekReport.getDays().get(i).getSteps() != 0) {
                            fitCounter++;
                            numDevices = 1;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                if (fitbitSize > fitbitCounter) {
                    if (weekDay == null) {
                        try {
                            weekDay = this.fitbitWeekReport.getDays().get(i).getWeekDay();
                        } catch (Exception ex2) {
                            ex2.printStackTrace();
                        }
                    }
                    if (fullDay == null) {
                        fullDay = this.fitbitWeekReport.getDays().get(i).getFullDate();
                    }
                    dayAverage += this.fitbitWeekReport.getDays().get(i).getSteps();
                    if (this.fitbitWeekReport.getDays().get(i).getSteps() != 0) {
                        fitbitCounter++;
                        numDevices++;
                    }
                }
                if (jawboneSize > jawboneCounter) {
                    if (weekDay == null) {
                        try {
                            weekDay = this.jawboneWeekReport.getDays().get(i).getWeekDay();
                        } catch (Exception ex22) {
                            ex22.printStackTrace();
                        }
                    }
                    if (fullDay == null) {
                        fullDay = this.jawboneWeekReport.getDays().get(i).getFullDate();
                    }
                    dayAverage += this.jawboneWeekReport.getDays().get(i).getSteps();
                    if (this.jawboneWeekReport.getDays().get(i).getSteps() != 0) {
                        jawboneCounter++;
                        numDevices++;
                    }
                }
                if (misfitSize > misfitCounter) {
                    if (weekDay == null) {
                        try {
                            weekDay = this.misfitWeekReport.getDays().get(i).getWeekDay();
                        } catch (Exception ex222) {
                            ex222.printStackTrace();
                        }
                    }
                    if (fullDay == null) {
                        fullDay = this.misfitWeekReport.getDays().get(i).getFullDate();
                    }
                    dayAverage += this.misfitWeekReport.getDays().get(i).getSteps();
                    if (this.misfitWeekReport.getDays().get(i).getSteps() != 0) {
                        misfitCounter++;
                        numDevices++;
                    }
                }
                if (movesSize > movesCounter) {
                    if (weekDay == null) {
                        try {
                            weekDay = this.movesWeekReport.getDays().get(i).getWeekDay();
                        } catch (Exception ex2222) {
                            ex2222.printStackTrace();
                        }
                    }
                    if (fullDay == null) {
                        fullDay = this.movesWeekReport.getDays().get(i).getFullDate();
                    }
                    dayAverage += this.movesWeekReport.getDays().get(i).getSteps();
                    if (this.movesWeekReport.getDays().get(i).getSteps() != 0) {
                        movesCounter++;
                        numDevices++;
                    }
                }
                boolean avg = true;
                if (this.context != null) {
                    avg = PrefManager.with(this.context).getBoolean("should_average", true);
                }
                if (avg && numDevices != 0) {
                    dayAverage /= numDevices;
                }
                weekAverage += dayAverage;
                DayReport d = new DayReport();
                d.setFullDate(fullDay);
                d.setWeekDay(weekDay);
                d.setSteps(dayAverage);
                dayReports.add(d);
                stepList.add(dayAverage);
            }
            if (size != 0) {
                weekAverage /= size;
            }
            while (stepList.size() < 7) {
                stepList.add(0);
            }
            return new WeekReport(dayReports, stepList, weekAverage, size, Devices.NOTUSFIT);
        }
    }
}
