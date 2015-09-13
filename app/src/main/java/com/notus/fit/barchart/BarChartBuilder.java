package com.notus.fit.barchart;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.notus.fit.BuildConfig;

/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/3/2015 7:45 PM
 */
public class BarChartBuilder {
    private BarChart barChart;
    private String description;
    private boolean drawLegend;
    private boolean drawXLabels;
    private int maxVisibleValue;
    private String units;


    public BarChartBuilder(BarChart barChart) {
        this(barChart, BuildConfig.FLAVOR, BuildConfig.FLAVOR, 10, false, false);
    }

    public BarChartBuilder(BarChart barChart, String description, String units, int maxVisibleValue, boolean drawXLabels, boolean drawLegend) {
        this.description = description;
        this.units = units;
        this.maxVisibleValue = maxVisibleValue;
        this.drawXLabels = drawXLabels;
        this.drawLegend = drawLegend;
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(maxVisibleValue);
        barChart.setDrawGridBackground(false);
        barChart.setDrawBarShadow(false);
        barChart.setDescription(description);
        barChart.setClickable(false);
        barChart.setPinchZoom(false);
        XAxis xL = barChart.getXAxis();
        xL.setPosition(XAxis.XAxisPosition.BOTTOM);
        xL.setDrawGridLines(false);
        YAxis yL = barChart.getAxisLeft();
        yL.setLabelCount(10, true);
        yL.setTextSize(10.0f);
        yL.setDrawGridLines(false);
        yL.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        barChart.getAxisRight().setEnabled(false);
        this.barChart = barChart;
    }

    public String getDescription() {
        return this.description;
    }

    public BarChartBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUnits() {
        return this.units;
    }

    public BarChartBuilder setUnits(String units) {
        this.units = units;
        return this;
    }

    public int getMaxVisibleValue() {
        return this.maxVisibleValue;
    }

    public BarChartBuilder setMaxVisibleValue(int maxVisibleValue) {
        this.maxVisibleValue = maxVisibleValue;
        return this;
    }

    public BarChart getBarChart() {
        return this.barChart;
    }

    public boolean isDrawXLabels() {
        return this.drawXLabels;
    }

    public BarChartBuilder setDrawXLabels(boolean drawXLabels) {
        this.drawXLabels = drawXLabels;
        return this;
    }

    public boolean isDrawLegend() {
        return this.drawLegend;
    }

    public BarChartBuilder setDrawLegend(boolean drawLegend) {
        this.drawLegend = drawLegend;
        return this;
    }

    public BarChart build() {
        return new BarChartBuilder(this.barChart, this.description, this.units, this.maxVisibleValue, this.drawXLabels, this.drawLegend).getBarChart();
    }
}
