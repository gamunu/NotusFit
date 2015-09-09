package com.notus.fit.barchart;

import java.util.ArrayList;

/**
 * Created by VBALAUD on 9/3/2015.
 */
public class BarChartData {
    private String bottomText;
    private int maxVisibleValue;
    private int type;
    private String units;
    private ArrayList<String> xValues;
    private ArrayList<Integer> yValues;

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

    public ArrayList<Integer> getyValues() {
        return this.yValues;
    }

    public BarChartData setyValues(ArrayList<Integer> yValues) {
        this.yValues = yValues;
        return this;
    }

    public ArrayList<String> getxValues() {
        return this.xValues;
    }

    public BarChartData setxValues(ArrayList<String> xValues) {
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
