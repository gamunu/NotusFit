package com.notus.fit.barchart;

import android.content.Context;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.notus.fit.BuildConfig;
import com.notus.fit.R;
import com.notus.fit.utils.ColorUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by VBALAUD on 9/3/2015.
 */
public class BarChartDataBuilder {

    public static int DEFAULT;
    public static int STEPS;

    static {
        DEFAULT = 0;
        STEPS = 1;
    }

    private BarChart barChart;
    private float barSpaceContent;
    private String bottomText;
    private ArrayList<Integer> colors;
    private Context context;
    private int type;
    private ArrayList<String> xValues;
    private ArrayList<Integer> yValues;

    public BarChartDataBuilder(BarChart barChart, Context context) {
        this.barChart = barChart;
        this.context = context;
        this.colors = new ArrayList();
        this.barSpaceContent = 30.0f;
        this.xValues = new ArrayList();
        this.yValues = new ArrayList();
        this.bottomText = BuildConfig.FLAVOR;
        this.type = DEFAULT;
    }

    public BarChart getBarChart() {
        return this.barChart;
    }

    public BarChartDataBuilder setBarChart(BarChart barChart) {
        this.barChart = barChart;
        return this;
    }

    public ArrayList<Integer> getColors() {
        return this.colors;
    }

    public BarChartDataBuilder setColors(ArrayList<Integer> colors) {
        this.colors = colors;
        return this;
    }

    public ArrayList<Integer> getyValues() {
        return this.yValues;
    }

    public BarChartDataBuilder setyValues(ArrayList<Integer> yValues) {
        this.yValues = yValues;
        return this;
    }

    public ArrayList<String> getxValues() {
        return this.xValues;
    }

    public BarChartDataBuilder setxValues(ArrayList<String> xValues) {
        this.xValues = xValues;
        return this;
    }

    public Context getContext() {
        return this.context;
    }

    public BarChartDataBuilder setContext(Context context) {
        this.context = context;
        return this;
    }

    public float getBarSpaceContent() {
        return this.barSpaceContent;
    }

    public BarChartDataBuilder setBarSpaceContent(float barSpaceContent) {
        this.barSpaceContent = barSpaceContent;
        return this;
    }

    public String getBottomText() {
        return this.bottomText;
    }

    public BarChartDataBuilder setBottomText(String bottomText) {
        this.bottomText = bottomText;
        return this;
    }

    public int getType() {
        return this.type;
    }

    public BarChartDataBuilder setType(int type) {
        this.type = type;
        return this;
    }

    public BarChart build() {
        try {
            ArrayList<BarEntry> yVals = new ArrayList();
            int counter = 0;
            Iterator it = this.yValues.iterator();
            while (it.hasNext()) {
                yVals.add(new BarEntry((float) ((Integer) it.next()).intValue(), counter));
                counter++;
            }
            if (yVals.size() != 0) {
                int i;
                BarDataSet set = new BarDataSet(yVals, this.bottomText);
                for (i = 0; i < this.colors.size(); i++) {
                    this.colors.add(i, Integer.valueOf(this.context.getResources().getColor(((Integer) this.colors.get(i)).intValue())));
                }
                if (this.colors.size() == 0) {
                    if (this.type == DEFAULT) {
                        this.colors.add(Integer.valueOf(this.context.getResources().getColor(R.color.primary)));
                        this.colors.add(Integer.valueOf(this.context.getResources().getColor(R.color.accent_color)));
                    } else if (this.type == STEPS) {
                        it = this.yValues.iterator();
                        while (it.hasNext()) {
                            this.colors.add(Integer.valueOf(ColorUtils.getStepsColor(getContext(), ((Integer) it.next()).intValue())));
                        }
                    }
                }
                set.setColors(this.colors);
                List dataSets = new ArrayList();
                dataSets.add(set);
                if (this.xValues.size() == 0) {
                    for (i = 0; i < yVals.size(); i++) {
                        this.xValues.add(BuildConfig.FLAVOR);
                    }
                }
                this.barChart.setData(new BarData(this.xValues, dataSets));
            }
            this.barChart.getLegend().setEnabled(false);
            this.barChart.invalidate();
            return this.barChart;
        } catch (Exception e) {
            return null;
        }
    }
}
