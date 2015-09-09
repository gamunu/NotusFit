package com.notus.fit.fragments;

import android.os.Bundle;

import com.notus.fit.activities.BaseActivity;
import com.notus.fit.models.RequestTime;
import com.notus.fit.models.WeekReport;
import com.notus.fit.models.api_models.User;
import com.notus.fit.network.fitbit.FitbitSeriesRequest;
import com.notus.fit.network.misfit.MisfitDateRequest;
import com.notus.fit.utils.FitnessUtils;
import com.notus.fit.utils.PrefManager;

import rx.Observable;
import rx.functions.Func0;

public abstract class FitnessFragment extends DefaultFragment {
    protected static final String TAG = FitnessFragment.class.getSimpleName();
    protected BaseActivity baseActivity;
    protected boolean hasFitbit;
    protected boolean hasJawbone;
    protected boolean hasMisfit;
    protected boolean hasMoves;
    protected boolean hasWearDevice;


    public FitnessFragment() {
        this.hasFitbit = false;
        this.hasJawbone = false;
        this.hasWearDevice = false;
        this.hasMisfit = false;
        this.hasMoves = false;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.baseActivity = (BaseActivity) getActivity();
        this.hasFitbit = PrefManager.with(getActivity()).getBoolean(User.HAS_FITBIT, false);
        this.hasWearDevice = PrefManager.with(getActivity()).getBoolean(User.HAS_GOOGLEFIT, false);
        this.hasJawbone = PrefManager.with(getActivity()).getBoolean(User.HAS_JAWBONE, false);
        this.hasMisfit = PrefManager.with(getActivity()).getBoolean(User.HAS_MISFIT, false);
        this.hasMoves = PrefManager.with(getActivity()).getBoolean(User.HAS_MOVES, false);
    }

    protected Observable<WeekReport> getAndroidWearWeekReport(final RequestTime requestTime) {
        return Observable.defer(new Func0<Observable<WeekReport>>() {
            @Override
            public Observable<WeekReport> call() {
                return Observable.just(FitnessUtils.getAndroidWearWeekReport(requestTime, FitnessFragment.this.getActivity(), FitnessFragment.this.baseActivity.getGoogleFitClient()));
            }
        });
    }

    protected Observable<WeekReport> getFitbitWeekReport(final FitbitSeriesRequest seriesRequest) {
        return Observable.defer(new Func0<Observable<WeekReport>>() {
            @Override
            public Observable<WeekReport> call() {
                return Observable.just(FitnessUtils.getFitbitWeekReport(seriesRequest, FitnessFragment.this.getActivity()));
            }
        });
    }

    protected Observable<WeekReport> getJawboneWeekReport(final int weekType) {
        return Observable.defer(new Func0<Observable<WeekReport>>() {
            @Override
            public Observable<WeekReport> call() {
                return Observable.just(FitnessUtils.getJawboneWeekReport(weekType, FitnessFragment.this.getActivity()));
            }
        });
    }

    protected Observable<WeekReport> getMisfitWeekReport(final MisfitDateRequest dateRequest) {
        return Observable.defer(new Func0<Observable<WeekReport>>() {
            @Override
            public Observable<WeekReport> call() {
                return Observable.just(FitnessUtils.getMisfitWeekReport(dateRequest, FitnessFragment.this.getActivity()));
            }
        });
    }

    protected Observable<WeekReport> getMovesWeekReport(final int weekType) {
        return Observable.defer(new Func0<Observable<WeekReport>>() {
            @Override
            public Observable<WeekReport> call() {
                return Observable.just(FitnessUtils.getMovesWeekReport(weekType, FitnessFragment.this.getActivity()));
            }
        });
    }

    protected Observable<WeekReport> getWeekReport() {
        return Observable.just(new WeekReport());
    }
}
