package com.notus.fit.ui_elements;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.notus.fit.R;
import com.notus.fit.barchart.BarChartBuilder;
import com.notus.fit.barchart.BarChartData;
import com.notus.fit.barchart.BarChartDataBuilder;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CardBarChart extends CardWithChart {
    @Bind(R.id.chart)
    BarChart barChart;
    @Bind(R.id.week_average)
    TextView weekAverage;
    private BarChartData barChartData;

    public CardBarChart(Context context, BarChartData barChartData) {
        this(context);
        this.barChartData = barChartData;
    }

    public CardBarChart(Context context) {
        super(context, R.layout.card_bar_chart);
    }

    public void setupInnerViewElements(ViewGroup parent, View view) {
        super.setupInnerViewElements(parent, view);
        ButterKnife.bind(this, view);
        this.barChart = new BarChartBuilder(this.barChart).setUnits(this.barChartData.getUnits()).setMaxVisibleValue(this.barChartData.getMaxVisibleValue()).setDrawXLabels(true).build();
        this.barChart = new BarChartDataBuilder(this.barChart, getContext()).setyValues(this.barChartData.getyValues()).setxValues(this.barChartData.getxValues()).setType(this.barChartData.getType()).build();
        if (this.barChart != null) {
            this.weekAverage.setText(this.barChartData.getBottomText());
            this.barChart.setPinchZoom(false);
            this.barChart.setClickable(false);
            this.barChart.setVerticalScrollBarEnabled(false);
            this.barChart.setDoubleTapToZoomEnabled(false);
            this.barChart.animateY(2000);
        }
    }

    public void setBarChart(BarChart barChart) {
        this.barChart = barChart;
    }

    public Chart getChart() {
        return this.barChart;
    }
}
