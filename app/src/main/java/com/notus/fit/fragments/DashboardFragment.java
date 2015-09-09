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
import com.notus.fit.network.weather.models.Weather;
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
    public static final String DEVICE = "DEVICE";
    public static final String WEEK = "WEEK";
    public int deviceType;
    public Subscriber<WeekReport> weekSubscriber;
    public int weekType;
    protected WeekReport androidWearWeekReport;
    protected MisfitDateRequest dateRequest;
    protected WeekReport fitbitWeekReport;
    protected WeekReport jawboneWeekReport;
    protected GoogleApiClient mWearClient;
    protected WeekReport misfitWeekReport;
    protected WeekReport movesWeekReport;
    protected RequestTime requestTime;
    protected WeekReport weekReport;
    ArrayList<Card> cards;
    @Bind(R.id.dashboard_layout)
    LinearLayout dashboardLayout;
    ArrayList<WeekReport> weekReports;
    private boolean barChart;


    public DashboardFragment() {
        this.weekType = -1;
        this.deviceType = -1;
        this.requestTime = null;
        this.barChart = true;
        this.weekSubscriber = new Subscriber<WeekReport>() {
            @Override
            public void onCompleted() {
                Log.d(FitnessFragment.TAG, "Hello from completed....");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(FitnessFragment.TAG, "Hello from error....");
                e.printStackTrace();
            }

            @Override
            public void onNext(WeekReport rWeekReport) {
                Log.d(FitnessFragment.TAG, "Hello from next....");
                DashboardFragment.this.weekReport = rWeekReport;
                DashboardFragment.this.weekReports.add(DashboardFragment.this.weekReport);
                if ((DashboardFragment.this.getActivity() instanceof DrawerPagerActivity) && DashboardFragment.this.weekType == 0) {
                    PrefManager.with(DashboardFragment.this.getActivity()).save(PreferenceUtils.WEEK_AVERAGE, String.valueOf(DashboardFragment.this.weekReport.getWeekAverage()));
                }
                DashboardFragment.this.setDevice();
                final GoogleApiClient gamesApiClient = ((DrawerActivity) DashboardFragment.this.getActivity()).getGamesClient();

                if (PrefManager.with(DashboardFragment.this.getActivity()).getBoolean(DashboardFragment.this.getString(R.string.games_enabled), true) && (DashboardFragment.this.getActivity() instanceof MainActivity)) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (gamesApiClient != null && gamesApiClient.isConnected()) {
                                Iterator it;
                                if (DashboardFragment.this.weekType == 0) {
                                    int maxSteps = 0;
                                    it = DashboardFragment.this.weekReport.getStepList().iterator();
                                    while (it.hasNext()) {
                                        Integer steps = (Integer) it.next();
                                        DashboardFragment.this.unlockAchievements(steps.intValue(), gamesApiClient);
                                        maxSteps += steps.intValue();
                                    }
                                    try {
                                        Games.Leaderboards.submitScore(gamesApiClient, DashboardFragment.this.getString(R.string.leaderboard_max_total_steps_in_1_week), (long) maxSteps);
                                    } catch (Exception ex) {
                                        if (ex.getMessage() != null) {
                                            Log.d(FitnessFragment.TAG, "Games Exception: " + ex.getMessage());
                                        }
                                    }
                                }
                                if (DashboardFragment.this.weekType == 1) {
                                    boolean hardWorker = true;
                                    boolean superWeek = true;
                                    int totalSteps = 0;
                                    it = DashboardFragment.this.weekReport.getStepList().iterator();
                                    while (it.hasNext()) {
                                        Integer i = (Integer) it.next();
                                        totalSteps += i.intValue();
                                        if (i.intValue() < GameUtils.WELCOME_FITHUB) {
                                            hardWorker = false;
                                        }
                                        if (i.intValue() < GameUtils.OVERACHIEVER) {
                                            superWeek = false;
                                        }
                                        try {
                                            Games.Leaderboards.submitScore(gamesApiClient, DashboardFragment.this.getString(R.string.leaderboard_max_steps_1_day), (long) i.intValue());
                                        } catch (Exception ex2) {
                                            if (ex2.getMessage() != null) {
                                                Log.d(FitnessFragment.TAG, "Games Exception: " + ex2.getMessage());
                                            }
                                        }
                                        DashboardFragment.this.unlockAchievements(i.intValue(), gamesApiClient);
                                    }
                                    try {
                                        Games.Leaderboards.submitScore(gamesApiClient, DashboardFragment.this.getString(R.string.leaderboard_max_total_steps_in_1_week), (long) totalSteps);
                                    } catch (Exception ex22) {
                                        if (ex22.getMessage() != null) {
                                            Log.d(FitnessFragment.TAG, "Games Exception: " + ex22.getMessage());
                                        }
                                    }
                                    if (hardWorker) {
                                        Games.Achievements.unlock(gamesApiClient, DashboardFragment.this.getString(R.string.achievement_hard_worker));
                                    }
                                    if (superWeek) {
                                        Games.Achievements.unlock(gamesApiClient, DashboardFragment.this.getString(R.string.achievement_super_week));
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
        this.weekReports = new ArrayList();
        if (getArguments() != null) {
            this.requestTime = (RequestTime) Parcels.unwrap(getArguments().getParcelable(RequestTime.REQUEST_TIME));
            this.dateRequest = (MisfitDateRequest) Parcels.unwrap(getArguments().getParcelable(MisfitDateRequest.MISFIT_DATE));
            this.weekType = getArguments().getInt(WEEK, -1);
            this.deviceType = getArguments().getInt(DEVICE, -1);
        }
        this.recyclerViewList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (DashboardFragment.this.cards != null) {
                    for (int i = 1; i < DashboardFragment.this.cards.size(); i++) {
                        try {
                            ((DailyListCard) DashboardFragment.this.cards.get(i)).refreshCard();
                        } catch (Exception e) {
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
        Observable<WeekReport> allDevicesWeekReport = Observable.zip(this.hasWearDevice ? getAndroidWearWeekReport(this.requestTime) : getWeekReport(), this.hasFitbit ? getFitbitWeekReport(seriesRequest) : getWeekReport(), this.hasJawbone ? getJawboneWeekReport(this.weekType) : getWeekReport(), this.hasMisfit ? getMisfitWeekReport(this.dateRequest) : getWeekReport(), this.hasMoves ? getMovesWeekReport(this.weekType) : getWeekReport(), new Func5<WeekReport, WeekReport, WeekReport, WeekReport, WeekReport, WeekReport>() {
            @Override
            public WeekReport call(WeekReport androidWear, WeekReport fitbit, WeekReport jawbone, WeekReport misfit, WeekReport moves) {
                DashboardFragment.this.addWeekToList(DashboardFragment.this.hasWearDevice ? androidWear : null);
                DashboardFragment.this.addWeekToList(DashboardFragment.this.hasWearDevice ? androidWear : null);
                DashboardFragment.this.addWeekToList(DashboardFragment.this.hasFitbit ? fitbit : null);
                DashboardFragment.this.addWeekToList(DashboardFragment.this.hasJawbone ? jawbone : null);
                DashboardFragment.this.addWeekToList(DashboardFragment.this.hasMisfit ? misfit : null);
                DashboardFragment.this.addWeekToList(DashboardFragment.this.hasMoves ? moves : null);
                DashboardFragment.this.androidWearWeekReport = androidWear;
                DashboardFragment.this.fitbitWeekReport = fitbit;
                DashboardFragment.this.jawboneWeekReport = jawbone;
                DashboardFragment.this.misfitWeekReport = misfit;
                DashboardFragment.this.movesWeekReport = moves;
                WeekReport weekReport = new AllDevicesWeekReport.Builder(DashboardFragment.this.getActivity()).setFitbitWeekReport(DashboardFragment.this.fitbitWeekReport).setGoogleFitWeekReport(DashboardFragment.this.androidWearWeekReport).setJawboneWeekReport(DashboardFragment.this.jawboneWeekReport).setMisfitWeekReport(DashboardFragment.this.misfitWeekReport).setMovesWeekReport(DashboardFragment.this.movesWeekReport).build();
                if (DashboardFragment.this.mWearClient == null) {
                    DashboardFragment.this.initWearClient();
                }
                int todaySteps = 0;
                try {
                    todaySteps = ((DayReport) weekReport.getDays().get(weekReport.getRealListSize() - 1)).getSteps();
                } catch (ArrayIndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                }
                try {
                    if (DashboardFragment.this.weekType == 0) {
                        if (DashboardFragment.this.getActivity() != null) {
                            PrefManager.with(DashboardFragment.this.getActivity()).save(DashboardFragment.this.getString(R.string.today_steps), todaySteps);
                        }
                    }
                } catch (Exception ex2) {
                    ex2.printStackTrace();
                }
                if (DashboardFragment.this.weekType == 0) {
                    try {
                        if (PrefManager.with(DashboardFragment.this.getActivity()).getString(User.UNITS, null) == null) {
                            String id = PrefManager.with(DashboardFragment.this.getActivity()).getString(User.OBJECT_ID, null);
                            if (id != null) {
                                ParseObject userObject = ParseQuery.getQuery(User.CLASS).whereEqualTo(User.OBJECT_ID, id).getFirst();
                                PrefManager.with(DashboardFragment.this.getActivity()).save(User.UNITS, userObject.getString(User.UNITS));
                                userObject.save();
                                userObject.pinInBackground();
                            }
                        }
                    } catch (Exception ex22) {
                        ex22.printStackTrace();
                    }
                    if (PrefManager.with(DashboardFragment.this.getActivity()).getString("temp", null) == null) {
                        GpsTracker tracker = new GpsTracker(DashboardFragment.this.getActivity());
                        float longitude = 0.0f;
                        float f = 0.0f;
                        try {
                            f = (float) tracker.getLatitude();
                            longitude = (float) tracker.getLongitude();
                        } catch (Exception ex222) {
                            ex222.printStackTrace();
                        }
                        tracker.stopUsingGPS();
                        WeatherApiClient.WeatherApi weatherApi = (WeatherApiClient.WeatherApi) WeatherApiClient.getBaseRestAdapter(DashboardFragment.this.getActivity()).create(WeatherApiClient.WeatherApi.class);
                        String units = "imperial";
                        if (PrefManager.with(DashboardFragment.this.getActivity()).getString(User.UNITS, DashboardFragment.this.getString(R.string.pref_units_imperial)).equals(DashboardFragment.this.getString(R.string.pref_units_metric))) {
                            units = "metric";
                        }
                        WeatherResponse response = weatherApi.getWeather(f, longitude, units, WeatherApiClient.API_KEY);
                        PrefManager.with(DashboardFragment.this.getActivity()).save("temp", String.valueOf(response.getMainWeather().getTemp()));
                        try {
                            PrefManager.with(DashboardFragment.this.getActivity()).save("weather_id", String.valueOf(((Weather) response.getWeather().get(0)).getId()));
                        } catch (Exception ex2222) {
                            ex2222.printStackTrace();
                        }
                    }
                    if (DashboardFragment.this.mWearClient != null) {
                        if (DashboardFragment.this.mWearClient.isConnected()) {
                            try {
                                if (DashboardFragment.this.getActivity() != null) {
                                    PutDataMapRequest request = PutDataMapRequest.create("/com.gabilheri.fithub.steps");
                                    DataMap map = request.getDataMap();
                                    map.putFloat("temp", Float.parseFloat(PrefManager.with(DashboardFragment.this.getActivity()).getString("temp", AppEventsConstants.EVENT_PARAM_VALUE_NO)));
                                    map.putString(User.UNITS, "imperial");
                                    map.putInt("steps", todaySteps);
                                    try {
                                        map.putInt("weather_id", Integer.parseInt(PrefManager.with(DashboardFragment.this.getActivity()).getString("weather_id", "800")));
                                    } catch (Exception e) {
                                    }
                                    map.putString("step_goal", PrefManager.with(DashboardFragment.this.getActivity()).getString(DashboardFragment.this.getResources().getString(R.string.steps_goal), "10000"));
                                    Wearable.DataApi.putDataItem(DashboardFragment.this.mWearClient, request.asPutDataRequest());
                                }
                            } catch (Exception ex22222) {
                                if (ex22222.getMessage() != null) {
                                    Log.d(FitnessFragment.TAG, ex22222.getMessage());
                                }
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
            this.weekReports.add(w);
        }
    }

    public int getLayoutResource() {
        return R.layout.dashboard_fragment;
    }

    public void setDevice(int device) {
        this.deviceType = device;
        if (this.weekReport != null) {
            this.progressWheel.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    DashboardFragment.this.setDevice();
                }
            }, 400);
        }
    }

    public void setBarChart(boolean barChart) {
        this.barChart = barChart;
    }

    public void setDevice() {
        List<DayReport> days = null;
        int weekAverage = 0;
        ArrayList<Integer> stepsList = new ArrayList();
        ArrayList<WeekReport> singleReport = new ArrayList();
        switch (this.deviceType) {
            case YearMonthDay.YEAR /*0*/:
                weekAverage = this.weekReport.getWeekAverage();
                stepsList = this.weekReport.getStepList();
                days = this.weekReport.getDays();
                break;
            case IslamicChronology.AH /*1*/:
                weekAverage = this.androidWearWeekReport.getWeekAverage();
                stepsList = this.androidWearWeekReport.getStepList();
                days = this.androidWearWeekReport.getDays();
                singleReport.add(this.androidWearWeekReport);
                break;
            case YearMonthDay.DAY_OF_MONTH /*2*/:
                weekAverage = this.fitbitWeekReport.getWeekAverage();
                stepsList = this.fitbitWeekReport.getStepList();
                days = this.fitbitWeekReport.getDays();
                singleReport.add(this.fitbitWeekReport);
                break;
            case TimeOfDay.MILLIS_OF_SECOND /*3*/:
                weekAverage = this.jawboneWeekReport.getWeekAverage();
                stepsList = this.jawboneWeekReport.getStepList();
                days = this.jawboneWeekReport.getDays();
                singleReport.add(this.jawboneWeekReport);
                break;
            case MutableDateTime.ROUND_HALF_CEILING /*4*/:
                weekAverage = this.misfitWeekReport.getWeekAverage();
                stepsList = this.misfitWeekReport.getStepList();
                days = this.misfitWeekReport.getDays();
                singleReport.add(this.misfitWeekReport);
                break;
            case MutableDateTime.ROUND_HALF_EVEN /*5*/:
                weekAverage = this.movesWeekReport.getWeekAverage();
                stepsList = this.movesWeekReport.getStepList();
                days = this.movesWeekReport.getDays();
                singleReport.add(this.movesWeekReport);
                break;
        }
        String bottomText = "Week Average: " + weekAverage + " Steps.";
        this.cards = new ArrayList();
        if (this.barChart) {
            this.cards.add(new CardBarChart(getActivity(), new BarChartData().setBottomText(bottomText).setMaxVisibleValue(50000).setUnits(" Steps").setType(BarChartDataBuilder.STEPS).setxValues(WeekReport.getWeekLabels()).setyValues(stepsList).build()));
        } else {
            LineChartData lineChartData;
            if (this.deviceType == 0) {
                Log.d(TAG, "Week Reports Size: " + this.weekReports.size());
                lineChartData = new LineChartData(this.weekReports, bottomText);
            } else {
                singleReport.add(this.weekReport);
                lineChartData = new LineChartData(singleReport, bottomText);
            }
            this.cards.add(new CardLineChart(getActivity(), lineChartData));
        }
        if (days != null) {
            for (int i = 0; i < days.size(); i++) {
                DayReport d = (DayReport) days.get(i);
                if (d.getSteps() != 0) {
                    this.cards.add(new DailyListCard(getActivity(), d));
                }
            }
        }
        this.mCardArrayAdapter = new CardArrayRecyclerViewAdapter(getActivity(), this.cards);
        this.recyclerViewList.setAdapter(this.mCardArrayAdapter);
        this.recyclerViewList.invalidate();
        refreshList().run();
        this.progressWheel.setVisibility(View.GONE);
        this.dashboardLayout.invalidate();
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
        if (this.hasWearDevice && this.weekType == 0 && getActivity() != null && (getActivity() instanceof MainActivity) && this.mWearClient == null) {
            this.mWearClient = ((MainActivity) getActivity()).initWearClient();
        }
    }

    public void unlockAchievements(int totalSteps, GoogleApiClient gamesApiClient) {
        if (totalSteps > GameUtils.WELCOME_FITHUB) {
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
            if (ex.getMessage() != null) {
                Log.d(TAG, "Games Exception: " + ex.getMessage());
            }
        }
    }
}
