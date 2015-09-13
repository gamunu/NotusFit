package com.notus.fit.barchart;

import java.util.List;

/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/3/2015 7:45 PM
 */
public class BarChartData {
    private String bottomText;
    private int maxVisibleValue;
    private int type;
    private String units;
    private List<String> xValues;
    private List<Integer> yValues;

    public String getUnits() {
        return this.units;
    }

    public BarChartData setUnits(String units) {
        this.units = units;
        return this;
    }

    public int getMaxVisibleValue() {
        return this.maxVisibleValue;
    }

    public BarChartData setMaxVisibleValue(int maxVisibleValue) {
        this.maxVisibleValue = maxVisibleValue;
        return this;
    }

    public String getBottomText() {
        return this.bottomText;
    }

    public BarChartData setBottomText(String bottomText) {
        this.bottomText = bottomText;
        return this;
    }

    public List<Integer> getyValues() {
        return this.yValues;
    }

    public BarChartData setyValues(List<Integer> yValues) {
        this.yValues = yValues;
        return this;
    }

    public List<String> getxValues() {
        return this.xValues;
    }

    public BarChartData setxValues(List<String> xValues) {
        this.xValues = xValues;
        return this;
    }

    public BarChartData build() {
        return this;
    }

    public int getType() {
        return this.type;
    }

    public BarChartData setType(int type) {
        this.type = type;
        return this;
    }
}
