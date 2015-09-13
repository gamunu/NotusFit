package com.notus.fit.lineChart;

import com.notus.fit.models.WeekReport;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/3/2015 7:52 PM
 */
public class LineChartData {
    String bottomText;
    List<WeekReport> weekReports;

    public LineChartData(List<WeekReport> weekReports, String bottomText) {
        this.weekReports = weekReports;
        this.bottomText = bottomText;
    }

    public List<WeekReport> getWeekReports() {
        return this.weekReports;
    }

    public void setWeekReports(List<WeekReport> weekReports) {
        this.weekReports = weekReports;
    }

    public String getBottomText() {
        return this.bottomText;
    }

    public void setBottomText(String bottomText) {
        this.bottomText = bottomText;
    }
}
