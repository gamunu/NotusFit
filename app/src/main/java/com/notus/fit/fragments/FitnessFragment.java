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
    protected BaseActivity mBaseActivity;
    protected boolean mHasFitbit = false;
    protected boolean mHasJawbone = false;
    protected boolean mHasMisfit = false;
    protected boolean mHasMoves = false;
    protected boolean mHasWearDevice = false;


    public FitnessFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBaseActivity = (BaseActivity) getActivity();
        mHasFitbit = PrefManager.with(getContext()).getBoolean(User.HAS_FITBIT, false);
        mHasWearDevice = PrefManager.with(getContext()).getBoolean(User.HAS_GOOGLEFIT, false);
        mHasJawbone = PrefManager.with(getContext()).getBoolean(User.HAS_JAWBONE, false);
        mHasMisfit = PrefManager.with(getContext()).getBoolean(User.HAS_MISFIT, false);
        mHasMoves = PrefManager.with(getContext()).getBoolean(User.HAS_MOVES, false);
    }

    protected Observable<WeekReport> getAndroidWearWeekReport(final RequestTime requestTime) {
        return Observable.defer(new Func0<Observable<WeekReport>>() {
            @Override
            public Observable<WeekReport> call() {
                return Observable.just(FitnessUtils.getAndroidWearWeekReport(requestTime, getActivity(), mBaseActivity.getGoogleFitClient()));
            }
        });
    }

    protected Observable<WeekReport> getFitbitWeekReport(final FitbitSeriesRequest seriesRequest) {
        return Observable.defer(new Func0<Observable<WeekReport>>() {
            @Override
            public Observable<WeekReport> call() {
                return Observable.just(FitnessUtils.getFitbitWeekReport(seriesRequest, getActivity()));
            }
        });
    }

    protected Observable<WeekReport> getJawboneWeekReport(final int weekType) {
        return Observable.defer(new Func0<Observable<WeekReport>>() {
            @Override
            public Observable<WeekReport> call() {
                return Observable.just(FitnessUtils.getJawboneWeekReport(weekType, getActivity()));
            }
        });
    }

    protected Observable<WeekReport> getMisfitWeekReport(final MisfitDateRequest dateRequest) {
        return Observable.defer(new Func0<Observable<WeekReport>>() {
            @Override
            public Observable<WeekReport> call() {
                return Observable.just(FitnessUtils.getMisfitWeekReport(dateRequest, getActivity()));
            }
        });
    }

    protected Observable<WeekReport> getMovesWeekReport(final int weekType) {
        return Observable.defer(new Func0<Observable<WeekReport>>() {
            @Override
            public Observable<WeekReport> call() {
                return Observable.just(FitnessUtils.getMovesWeekReport(weekType, getActivity()));
            }
        });
    }

    protected Observable<WeekReport> getWeekReport() {
        return Observable.just(new WeekReport());
    }
}
