package com.notus.fit.lineChart;

import com.notus.fit.models.WeekReport;

import java.util.ArrayList;

/**
 * Created by VBALAUD on 9/3/2015.
 */
public class LineChartData {
    String bottomText;
    ArrayList<WeekReport> weekReports;

    public LineChartData(ArrayList<WeekReport> weekReports, String bottomText) {
        this.weekReports = weekReports;
        this.bottomText = bottomText;
    }

    public ArrayList<WeekReport> getWeekReports() {
        return this.weekReports;
    }

    public void setWeekReports(ArrayList<WeekReport> weekReports) {
        this.weekReports = weekReports;
    }

    public String getBottomText() {
        return this.bottomText;
    }

    public void setBottomText(String bottomText) {
        this.bottomText = bottomText;
    }
}
