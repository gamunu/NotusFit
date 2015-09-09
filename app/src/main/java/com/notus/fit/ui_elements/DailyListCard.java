package com.notus.fit.ui_elements;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.notus.fit.BuildConfig;
import com.notus.fit.R;
import com.notus.fit.models.DayReport;
import com.notus.fit.utils.ColorUtils;
import com.notus.fit.utils.PrefManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.gmariotti.cardslib.library.internal.Card;

public class DailyListCard extends Card implements Card.OnCardClickListener {
    @Bind(R.id.circle_bg)
    LinearLayout circleBg;
    @Bind(R.id.date)
    TextView date;
    @Bind(R.id.goal_steps)
    TextView goalSteps;
    @Bind(R.id.progress_bar)
    RoundCornerProgressBar progressBar;
    @Bind(R.id.steps)
    TextView stepCount;
    @Bind(R.id.weekday_text)
    TextView weekDay;
    private DayReport dayReport;

    public DailyListCard(Context context, DayReport dayReport) {
        super(context, R.layout.card_day_data);
        this.dayReport = dayReport;
    }

    public DailyListCard(Context context) {
        super(context, R.layout.card_day_data);
    }

    public DailyListCard(Context context, int innerLayout) {
        super(context, innerLayout);
    }

    public void setupInnerViewElements(ViewGroup parent, View view) {
        super.setupInnerViewElements(parent, view);
        ButterKnife.bind((Object) this, view);
        int stepGoal = 0;
        try {
            stepGoal = Integer.parseInt(PrefManager.with(getContext()).getString(getContext().getResources().getString(R.string.steps_goal), "10000"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            this.goalSteps.setText(String.valueOf(stepGoal));
            this.progressBar.setMax(Float.parseFloat(String.valueOf(stepGoal)));
            if (this.dayReport.getSteps() > stepGoal) {
                this.progressBar.setProgress((float) stepGoal);
                this.circleBg.setBackgroundResource(R.drawable.green_circle);
            } else {
                this.progressBar.setProgress((float) this.dayReport.getSteps());
                this.circleBg.setBackgroundResource(R.drawable.red_circle);
            }
            this.progressBar.setProgressColor(ColorUtils.getStepsColor(getContext(), this.dayReport.getSteps()));
            this.stepCount.setText(BuildConfig.FLAVOR + this.dayReport.getSteps() + " steps.");
            this.weekDay.setText(this.dayReport.getWeekDay());
            this.date.setText(this.dayReport.getFullDate());
        } catch (Exception ex2) {
            ex2.printStackTrace();
        }
    }

    public void onClick(Card card, View view) {
    }

    public void refreshCard() {
        this.progressBar.invalidate();
    }
}
