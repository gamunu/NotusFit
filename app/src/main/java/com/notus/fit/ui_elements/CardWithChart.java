package com.notus.fit.ui_elements;

import android.content.Context;

import com.github.mikephil.charting.charts.Chart;

import it.gmariotti.cardslib.library.internal.Card;

public abstract class CardWithChart extends Card {

    public CardWithChart(Context context) {
        super(context);
    }

    public CardWithChart(Context context, int i) {
        super(context, i);
    }

    public abstract Chart getChart();
}
