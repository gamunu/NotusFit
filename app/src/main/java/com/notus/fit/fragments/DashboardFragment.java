package com.notus.fit.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.AppEventsConstants;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.Wearable;
import com.notus.fit.MainActivity;
import com.notus.fit.R;
import com.notus.fit.activities.DrawerActivity;
import com.notus.fit.activities.DrawerPagerActivity;
import com.notus.fit.barchart.BarChartData;
import com.notus.fit.barchart.BarChartDataBuilder;
import com.notus.fit.lineChart.LineChartData;
import com.notus.fit.models.AllDevicesWeekReport;
import com.notus.fit.models.DayReport;
import com.notus.fit.models.RequestTime;
import com.notus.fit.models.WeekReport;
import com.notus.fit.models.api_models.User;
import com.notus.fit.network.fitbit.FitbitSeriesRequest;
import com.notus.fit.network.misfit.MisfitDateRequest;
import com.notus.fit.network.weather.WeatherApiClient;
import com.notus.fit.network.weather.models.WeatherResponse;
import com.notus.fit.ui_elements.CardBarChart;
import com.notus.fit.ui_elements.CardLineChart;
import com.notus.fit.ui_elements.DailyListCard;
import com.notus.fit.utils.GameUtils;
import com.notus.fit.utils.GpsTracker;
import com.notus.fit.utils.PrefManager;
import com.notus.fit.utils.PreferenceUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.joda.time.MutableDateTime;
import org.joda.time.TimeOfDay;
import org.joda.time.YearMonthDay;
import org.joda.time.chrono.IslamicChronology;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func5;
import rx.schedulers.Schedulers;

public class DashboardFragment extends DefaultListFragment {
    private static final String TAG = DashboardFragment.class.getName();
    public static final String DEVICE = "DEVICE";
    public static final String WEEK = "WEEK";
    public int deviceType = -1;
    public Subscriber<WeekReport> weekSubscriber;
    public int weekType = -1;
    protected WeekReport mAndroidWearWeekReport;
    protected MisfitDateRequest mDateRequest;
    protected WeekReport mFitbitWeekReport;
    protected WeekReport mJawboneWeekReport;
    protected GoogleApiClient mWearClient;
    protected WeekReport mMisfitWeekReport;
    protected WeekReport mMovesWeekReport;
    protected RequestTime mRequestTime;
    protected WeekReport mWeekReport;
    List<Card> mCards;
    @Bind(R.id.dashboard_layout)
    LinearLayout mDashboardLayout;
    List<WeekReport> mWeekReports;
    private boolean mBarChart = true;


    public DashboardFragment() {
        this.weekSubscriber = new Subscriber<WeekReport>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "Hello from completed....");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Hello from error....", e);
            }

            @Override
            public void onNext(WeekReport rWeekReport) {
                Log.i(TAG, "Hello from next....");
                mWeekReport = rWeekReport;
                mWeekReports.add(mWeekReport);
                if ((getActivity() instanceof DrawerPagerActivity) && weekType == 0) {
                    PrefManager.with(getContext()).save(PreferenceUtils.WEEK_AVERAGE, String.valueOf(mWeekReport.getWeekAverage()));
                }
                setDevice();
                final GoogleApiClient gamesApiClient = ((DrawerActivity) getActivity()).getGamesClient();

                if (PrefManager.with(getContext()).getBoolean(getString(R.string.games_enabled), true) && (getActivity() instanceof MainActivity)) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (gamesApiClient != null && gamesApiClient.isConnected()) {
                                Iterator it;
                                if (weekType == 0) {
                                    int maxSteps = 0;
                                    it = mWeekReport.getStepList().iterator();
                                    while (it.hasNext()) {
                                        Integer steps = (Integer) it.next();
                                        unlockAchievements(steps, gamesApiClient);
                                        maxSteps += steps;
                                    }
                                    try {
                                        Games.Leaderboards.submitScore(gamesApiClient, getString(R.string.leaderboard_max_total_steps_in_1_week), (long) maxSteps);
                                    } catch (Exception ex) {
                                        if (ex.getMessage() != null) {
                                            Log.e(TAG, "Games Exception", ex);
                                        }
                                    }
                                }
                                if (weekType == 1) {
                                    boolean hardWorker = true;
                                    boolean superWeek = true;
                                    int totalSteps = 0;
                                    it = mWeekReport.getStepList().iterator();
                                    while (it.hasNext()) {
                                        Integer i = (Integer) it.next();
                                        totalSteps += i;
                                        if (i < GameUtils.WELCOME_NOTUSFIT) {
                                            hardWorker = false;
                                        }
                                        if (i < GameUtils.OVERACHIEVER) {
                                            superWeek = false;
                                        }
                                        try {
                                            Games.Leaderboards.submitScore(gamesApiClient, getString(R.string.leaderboard_max_steps_1_day), (long) i);
                                        } catch (Exception ex) {
                                            Log.e(TAG, "Games Exception", ex);

                                        }
                                        unlockAchievements(i, gamesApiClient);
                                    }
                                    try {
                                        Games.Leaderboards.submitScore(gamesApiClient, getString(R.string.leaderboard_max_total_steps_in_1_week), (long) totalSteps);
                                    } catch (Exception ex) {
                                        Log.e(TAG, "Games Exception", ex);
                                    }
                                    if (hardWorker) {
                                        Games.Achievements.unlock(gamesApiClient, getString(R.string.achievement_hard_worker));
                                    }
                                    if (superWeek) {
                                        Games.Achievements.unlock(gamesApiClient, getString(R.string.achievement_super_week));
                                    }
                                }
                            }
                        }
                    }, 5000);
                }
            }
        };
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mWeekReports = new ArrayList<>();
        if (getArguments() != null) {
            this.mRequestTime = Parcels.unwrap(getArguments().getParcelable(RequestTime.REQUEST_TIME));
            this.mDateRequest = Parcels.unwrap(getArguments().getParcelable(MisfitDateRequest.MISFIT_DATE));
            this.weekType = getArguments().getInt(WEEK, -1);
            this.deviceType = getArguments().getInt(DEVICE, -1);
        }
        this.mRecyclerViewList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mCards != null) {
                    for (int i = 1; i < mCards.size(); i++) {
                        try {
                            ((DailyListCard) mCards.get(i)).refreshCard();
                        } catch (Exception ex) {
                            Log.e(LOG_TAG, ex.getMessage(), ex);
                        }
                    }
                }
            }
        });
        FitbitSeriesRequest seriesRequest = null;
        if (this.weekType == 0) {
            seriesRequest = new FitbitSeriesRequest().getCurrentWeek();
        } else if (this.weekType == 1) {
            seriesRequest = new FitbitSeriesRequest().getPreviousWeek();
        }
        Observable<WeekReport> allDevicesWeekReport = Observable.zip(mHasWearDevice ? getAndroidWearWeekReport(this.mRequestTime) : getWeekReport(), this.mHasFitbit ? getFitbitWeekReport(seriesRequest) : getWeekReport(), this.mHasJawbone ? getJawboneWeekReport(this.weekType) : getWeekReport(), this.mHasMisfit ? getMisfitWeekReport(this.mDateRequest) : getWeekReport(), this.mHasMoves ? getMovesWeekReport(this.weekType) : getWeekReport(), new Func5<WeekReport, WeekReport, WeekReport, WeekReport, WeekReport, WeekReport>() {
            @Override
            public WeekReport call(WeekReport androidWear, WeekReport fitbit, WeekReport jawbone, WeekReport misfit, WeekReport moves) {
                addWeekToList(mHasWearDevice ? androidWear : null);
                addWeekToList(mHasWearDevice ? androidWear : null);
                addWeekToList(mHasFitbit ? fitbit : null);
                addWeekToList(mHasJawbone ? jawbone : null);
                addWeekToList(mHasMisfit ? misfit : null);
                addWeekToList(mHasMoves ? moves : null);
                mAndroidWearWeekReport = androidWear;
                mFitbitWeekReport = fitbit;
                mJawboneWeekReport = jawbone;
                mMisfitWeekReport = misfit;
                mMovesWeekReport = moves;
                WeekReport weekReport = new AllDevicesWeekReport.Builder(getActivity()).setFitbitWeekReport(mFitbitWeekReport).setGoogleFitWeekReport(mAndroidWearWeekReport).setJawboneWeekReport(mJawboneWeekReport).setMisfitWeekReport(mMisfitWeekReport).setMovesWeekReport(mMovesWeekReport).build();
                if (mWearClient == null) {
                    initWearClient();
                }
                int todaySteps = 0;
                try {
                    todaySteps = weekReport.getDays().get(weekReport.getRealListSize() - 1).getSteps();
                } catch (ArrayIndexOutOfBoundsException ex) {
                    Log.e(LOG_TAG, ex.getMessage(), ex);
                }
                try {
                    if (weekType == 0) {
                        if (getActivity() != null) {
                            PrefManager.with(getContext()).save(getString(R.string.today_steps), todaySteps);
                        }
                    }
                } catch (Exception ex) {
                    Log.e(LOG_TAG, ex.getMessage(), ex);
                }
                if (weekType == 0) {
                    try {
                        if (PrefManager.with(getContext()).getString(User.UNITS, null) == null) {
                            String id = PrefManager.with(getContext()).getString(User.OBJECT_ID, null);
                            if (id != null) {
                                ParseObject userObject = ParseQuery.getQuery(User.CLASS).whereEqualTo(User.OBJECT_ID, id).getFirst();
                                PrefManager.with(getContext()).save(User.UNITS, userObject.getString(User.UNITS));
                                userObject.save();
                                userObject.pinInBackground();
                            }
                        }
                    } catch (Exception ex) {
                        Log.e(LOG_TAG, ex.getMessage(), ex);
                    }
                    if (PrefManager.with(getContext()).getString("temp", null) == null) {
                        GpsTracker tracker = new GpsTracker(getActivity());
                        float longitude = 0.0f;
                        float f = 0.0f;
                        try {
                            f = (float) tracker.getLatitude();
                            longitude = (float) tracker.getLongitude();
                        } catch (Exception ex) {
                            Log.e(LOG_TAG, ex.getMessage(), ex);
                        }
                        tracker.stopUsingGPS();
                        WeatherApiClient.WeatherApi weatherApi = WeatherApiClient.getBaseRestAdapter(getActivity()).create(WeatherApiClient.WeatherApi.class);
                        String units = "imperial";
                        if (PrefManager.with(getContext()).getString(User.UNITS, getString(R.string.pref_units_imperial)).equals(getString(R.string.pref_units_metric))) {
                            units = "metric";
                        }
                        WeatherResponse response = weatherApi.getWeather(f, longitude, units, WeatherApiClient.API_KEY);
                        PrefManager.with(getContext()).save("temp", String.valueOf(response.getMainWeather().getTemp()));
                        try {
                            PrefManager.with(getContext()).save("weather_id", String.valueOf(response.getWeather().get(0).getId()));
                        } catch (Exception ex) {
                            Log.e(LOG_TAG, ex.getMessage(), ex);
                        }
                    }
                    if (mWearClient != null) {
                        if (mWearClient.isConnected()) {
                            try {
                                if (getActivity() != null) {
                                    PutDataMapRequest request = PutDataMapRequest.create("/com.notus.fit.steps");
                                    DataMap map = request.getDataMap();
                                    map.putFloat("temp", Float.parseFloat(PrefManager.with(getContext()).getString("temp", AppEventsConstants.EVENT_PARAM_VALUE_NO)));
                                    map.putString(User.UNITS, "imperial");
                                    map.putInt("steps", todaySteps);
                                    try {
                                        map.putInt("weather_id", Integer.parseInt(PrefManager.with(getContext()).getString("weather_id", "800")));
                                    } catch (Exception ex) {
                                        Log.e(LOG_TAG, ex.getMessage(), ex);
                                    }
                                    map.putString("step_goal", PrefManager.with(getContext()).getString(getResources().getString(R.string.steps_goal), "10000"));
                                    Wearable.DataApi.putDataItem(mWearClient, request.asPutDataRequest());
                                }
                            } catch (Exception ex) {
                                Log.e(LOG_TAG, ex.getMessage(), ex);
                            }
                        }
                    }
                }
                return weekReport;
            }
        });
        allDevicesWeekReport.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).cache().subscribe(this.weekSubscriber);
        AppObservable.bindSupportFragment(this, allDevicesWeekReport);
    }

    public void addWeekToList(WeekReport w) {
        if (w != null) {
            this.mWeekReports.add(w);
        }
    }

    public int getLayoutResource() {
        return R.layout.dashboard_fragment;
    }

    public void setDevice(int device) {
        this.deviceType = device;
        if (this.mWeekReport != null) {
            this.mProgressWheel.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setDevice();
                }
            }, 400);
        }
    }

    public void setmBarChart(boolean mBarChart) {
        this.mBarChart = mBarChart;
    }

    public void setDevice() {
        List<DayReport> days = null;
        int weekAverage = 0;
        List<Integer> stepsList = new ArrayList<>();
        List<WeekReport> singleReport = new ArrayList<>();
        switch (this.deviceType) {
            case YearMonthDay.YEAR /*0*/:
                weekAverage = this.mWeekReport.getWeekAverage();
                stepsList = this.mWeekReport.getStepList();
                days = this.mWeekReport.getDays();
                break;
            case IslamicChronology.AH /*1*/:
                weekAverage = this.mAndroidWearWeekReport.getWeekAverage();
                stepsList = this.mAndroidWearWeekReport.getStepList();
                days = this.mAndroidWearWeekReport.getDays();
                singleReport.add(this.mAndroidWearWeekReport);
                break;
            case YearMonthDay.DAY_OF_MONTH /*2*/:
                weekAverage = this.mFitbitWeekReport.getWeekAverage();
                stepsList = this.mFitbitWeekReport.getStepList();
                days = this.mFitbitWeekReport.getDays();
                singleReport.add(this.mFitbitWeekReport);
                break;
            case TimeOfDay.MILLIS_OF_SECOND /*3*/:
                weekAverage = this.mJawboneWeekReport.getWeekAverage();
                stepsList = this.mJawboneWeekReport.getStepList();
                days = this.mJawboneWeekReport.getDays();
                singleReport.add(this.mJawboneWeekReport);
                break;
            case MutableDateTime.ROUND_HALF_CEILING /*4*/:
                weekAverage = this.mMisfitWeekReport.getWeekAverage();
                stepsList = this.mMisfitWeekReport.getStepList();
                days = this.mMisfitWeekReport.getDays();
                singleReport.add(this.mMisfitWeekReport);
                break;
            case MutableDateTime.ROUND_HALF_EVEN /*5*/:
                weekAverage = this.mMovesWeekReport.getWeekAverage();
                stepsList = this.mMovesWeekReport.getStepList();
                days = this.mMovesWeekReport.getDays();
                singleReport.add(this.mMovesWeekReport);
                break;
        }
        String bottomText = "Week Average: " + weekAverage + " Steps.";
        this.mCards = new ArrayList<>();
        if (this.mBarChart) {
            this.mCards.add(new CardBarChart(getActivity(), new BarChartData().setBottomText(bottomText).setMaxVisibleValue(50000).setUnits(" Steps").setType(BarChartDataBuilder.STEPS).setxValues(WeekReport.getWeekLabels()).setyValues(stepsList).build()));
        } else {
            LineChartData lineChartData;
            if (this.deviceType == 0) {
                Log.d(TAG, "Week Reports Size: " + this.mWeekReports.size());
                lineChartData = new LineChartData(this.mWeekReports, bottomText);
            } else {
                singleReport.add(this.mWeekReport);
                lineChartData = new LineChartData(singleReport, bottomText);
            }
            this.mCards.add(new CardLineChart(getActivity(), lineChartData));
        }
        if (days != null) {
            for (int i = 0; i < days.size(); i++) {
                DayReport d = days.get(i);
                if (d.getSteps() != 0) {
                    this.mCards.add(new DailyListCard(getActivity(), d));
                }
            }
        }
        this.mCardArrayAdapter = new CardArrayRecyclerViewAdapter(getActivity(), this.mCards);
        this.mRecyclerViewList.setAdapter(this.mCardArrayAdapter);
        this.mRecyclerViewList.invalidate();
        refreshList().run();
        this.mProgressWheel.setVisibility(View.GONE);
        this.mDashboardLayout.invalidate();
    }

    public void onStart() {
        super.onStart();
        if (this.mWearClient == null) {
            initWearClient();
        }
    }

    public void onResume() {
        super.onResume();
        if (this.mWearClient == null) {
            initWearClient();
        }
    }

    public void initWearClient() {
        if (this.mHasWearDevice && this.weekType == 0 && getActivity() != null && (getActivity() instanceof MainActivity) && this.mWearClient == null) {
            this.mWearClient = ((MainActivity) getActivity()).initWearClient();
        }
    }

    public void unlockAchievements(int totalSteps, GoogleApiClient gamesApiClient) {
        if (totalSteps > GameUtils.WELCOME_NOTUSFIT) {
            Games.Achievements.unlock(gamesApiClient, getString(R.string.achievement_welcome_to_fithub));
        }
        if (totalSteps > GameUtils.OVERACHIEVER) {
            Games.Achievements.unlock(gamesApiClient, getString(R.string.achievement_overachiever));
        }
        if (totalSteps > GameUtils.RUNNER) {
            Games.Achievements.unlock(gamesApiClient, getString(R.string.achievement_runner));
        }
        if (totalSteps > GameUtils.PADAWAN) {
            Games.Achievements.unlock(gamesApiClient, getString(R.string.achievement_fitness_padawan));
        }
        if (totalSteps > GameUtils.JEDI) {
            Games.Achievements.unlock(gamesApiClient, getString(R.string.achievement_fitness_jedi));
        }
        try {
            Games.Leaderboards.submitScore(gamesApiClient, getString(R.string.leaderboard_max_steps_1_day), (long) totalSteps);
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
        }
    }
}
