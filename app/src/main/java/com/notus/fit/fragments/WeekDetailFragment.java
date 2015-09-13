package com.notus.fit.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.notus.fit.R;
import com.notus.fit.barchart.BarChartData;
import com.notus.fit.barchart.BarChartDataBuilder;
import com.notus.fit.models.DayReport;
import com.notus.fit.models.ReportDate;
import com.notus.fit.models.WeekReport;
import com.notus.fit.models.api_models.WeekData;
import com.notus.fit.ui_elements.CardBarChart;
import com.notus.fit.ui_elements.DailyListCard;
import com.notus.fit.utils.CustomUtils;

import org.joda.time.LocalDate;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;


public class WeekDetailFragment extends DefaultListFragment {
    WeekData weekData;

    public WeekDetailFragment() {
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            this.weekData = Parcels.unwrap(getArguments().getParcelable("week"));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (weekData != null) {
                        String bottomText = "Week Average: " + weekData.getAverage() + " Steps.";
                        List<Card> cards = new ArrayList<>();
                        cards.add(new CardBarChart(getActivity(), new BarChartData().setBottomText(bottomText).setMaxVisibleValue(30000).setUnits(" Steps").setType(BarChartDataBuilder.STEPS).setxValues(WeekReport.getWeekLabels()).setyValues(weekData.getStepList()).build()));
                        String[] weekdays = getActivity().getResources().getStringArray(R.array.weekdays_short);
                        int counter = 0;
                        Object date = CustomUtils.getDate(weekData.getStartDate());
                        Iterator it = weekData.getStepList().iterator();
                        while (it.hasNext()) {
                            int i = (Integer) it.next();
                            DayReport d = new DayReport();
                            d.setFullDate(new ReportDate(new LocalDate(date).plusDays(counter)).getFullDateString());
                            d.setSteps(i);
                            d.setWeekDay(weekdays[counter]);
                            cards.add(new DailyListCard(getActivity(), d));
                            counter++;
                        }
                        mCardArrayAdapter = new CardArrayRecyclerViewAdapter(getActivity(), cards);
                        mRecyclerViewList.setAdapter(mCardArrayAdapter);
                        refreshList().run();
                        if (mProgressWheel.isEnabled()) {
                            mProgressWheel.setEnabled(false);
                        }
                        if (mProgressWheel != null) {
                            mProgressWheel.setVisibility(View.GONE);
                        }
                    }
                }
            }, 200);
        }
    }
}
