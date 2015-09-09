package com.notus.fit.ui_elements;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.crashlytics.android.Crashlytics;
import com.notus.fit.BuildConfig;
import com.notus.fit.R;
import com.notus.fit.activities.WeekDetailActivity;
import com.notus.fit.models.api_models.WeekData;
import com.notus.fit.utils.ColorUtils;
import com.notus.fit.utils.CustomUtils;
import com.notus.fit.utils.PrefManager;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.gmariotti.cardslib.library.internal.Card;

public class CardWeekReport extends Card
        implements it.gmariotti.cardslib.library.internal.Card.OnCardClickListener {
    @Bind(R.id.date)
    TextView date;
    @Bind(R.id.goal_steps)
    TextView goalSteps;
    @Bind(R.id.progress_bar)
    RoundCornerProgressBar progressBar;
    @Bind(R.id.steps)
    TextView steps;
    WeekData weekData;

    public CardWeekReport(Context context, WeekData weekData) {
        super(context, R.layout.week_history_card);
        this.weekData = weekData;
        setOnClickListener(this);
    }

    public void setupInnerViewElements(ViewGroup parent, View view) {
        super.setupInnerViewElements(parent, view);
        ButterKnife.bind((Object) this, view);
        int stepGoal = Integer.parseInt(PrefManager.with(getContext()).getString(getContext().getResources().getString(R.string.steps_goal), "10000"));
        this.goalSteps.setText(String.valueOf(stepGoal));
        this.progressBar.setMax(Float.parseFloat(String.valueOf(stepGoal)));
        if (this.weekData.getAverage() > stepGoal) {
            this.progressBar.setProgress((float) stepGoal);
        } else {
            this.progressBar.setProgress((float) this.weekData.getAverage());
        }
        this.progressBar.setProgressColor(ColorUtils.getStepsColor(getContext(), this.weekData.getAverage()));
        this.steps.setText(BuildConfig.FLAVOR + this.weekData.getAverage() + " steps.");
        this.date.setText("Week Of " + CustomUtils.formatLongDate(getContext(), this.weekData.getStartDate()));
        try {
            this.date.setBackgroundColor(getContext().getResources().getColor(ColorUtils.getRandomColor()));
        } catch (Exception ex) {
            Crashlytics.logException(ex);
        }
    }

    public void onClick(Card card, View view) {
        Intent i = new Intent(getContext(), WeekDetailActivity.class);
        i.putExtra("week", Parcels.wrap(this.weekData));
        getContext().startActivity(i);
    }
}
