package com.notus.fit.models;


import android.content.Context;

import com.notus.fit.utils.Devices;
import com.notus.fit.utils.MathUtils;
import com.notus.fit.utils.PrefManager;

import java.util.ArrayList;

public class AllDevicesWeekReport extends WeekReport {
    protected WeekReport fitbitWeekReport;
    protected WeekReport googleFitWeekReport;
    protected WeekReport jawboneWeekReport;
    protected WeekReport movesWeekReport;

    public WeekReport getGoogleFitWeekReport() {
        return this.googleFitWeekReport;
    }

    public void setGoogleFitWeekReport(WeekReport googleFitWeekReport) {
        this.googleFitWeekReport = googleFitWeekReport;
    }

    public WeekReport getFitbitWeekReport() {
        return this.fitbitWeekReport;
    }

    public void setFitbitWeekReport(WeekReport fitbitWeekReport) {
        this.fitbitWeekReport = fitbitWeekReport;
    }

    public WeekReport getJawboneWeekReport() {
        return this.jawboneWeekReport;
    }

    public void setJawboneWeekReport(WeekReport jawboneWeekReport) {
        this.jawboneWeekReport = jawboneWeekReport;
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
            ArrayList<DayReport> dayReports = new ArrayList();
            ArrayList<Integer> stepList = new ArrayList();
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
                        dayAverage = 0 + this.googleFitWeekReport.getDays().get(i).getSteps();
                        if (this.googleFitWeekReport.getDays().get(i).getSteps() != 0) {
                            fitCounter++;
                            numDevices = 0 + 1;
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
                            weekDay = ((DayReport) this.jawboneWeekReport.getDays().get(i)).getWeekDay();
                        } catch (Exception ex22) {
                            ex22.printStackTrace();
                        }
                    }
                    if (fullDay == null) {
                        fullDay = ((DayReport) this.jawboneWeekReport.getDays().get(i)).getFullDate();
                    }
                    dayAverage += ((DayReport) this.jawboneWeekReport.getDays().get(i)).getSteps();
                    if (((DayReport) this.jawboneWeekReport.getDays().get(i)).getSteps() != 0) {
                        jawboneCounter++;
                        numDevices++;
                    }
                }
                if (misfitSize > misfitCounter) {
                    if (weekDay == null) {
                        try {
                            weekDay = ((DayReport) this.misfitWeekReport.getDays().get(i)).getWeekDay();
                        } catch (Exception ex222) {
                            ex222.printStackTrace();
                        }
                    }
                    if (fullDay == null) {
                        fullDay = ((DayReport) this.misfitWeekReport.getDays().get(i)).getFullDate();
                    }
                    dayAverage += ((DayReport) this.misfitWeekReport.getDays().get(i)).getSteps();
                    if (((DayReport) this.misfitWeekReport.getDays().get(i)).getSteps() != 0) {
                        misfitCounter++;
                        numDevices++;
                    }
                }
                if (movesSize > movesCounter) {
                    if (weekDay == null) {
                        try {
                            weekDay = ((DayReport) this.movesWeekReport.getDays().get(i)).getWeekDay();
                        } catch (Exception ex2222) {
                            ex2222.printStackTrace();
                        }
                    }
                    if (fullDay == null) {
                        fullDay = ((DayReport) this.movesWeekReport.getDays().get(i)).getFullDate();
                    }
                    dayAverage += ((DayReport) this.movesWeekReport.getDays().get(i)).getSteps();
                    if (((DayReport) this.movesWeekReport.getDays().get(i)).getSteps() != 0) {
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
                stepList.add(Integer.valueOf(dayAverage));
            }
            if (size != 0) {
                weekAverage /= size;
            }
            while (stepList.size() < 7) {
                stepList.add(Integer.valueOf(0));
            }
            return new WeekReport(dayReports, stepList, weekAverage, size, Devices.FITHUB);
        }
    }
}
