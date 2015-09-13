package com.notus.fit.network.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.NotificationCompat.WearableExtender;
import android.util.Log;

import com.facebook.AppEventsConstants;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.games.Games;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.Wearable;
import com.notus.fit.MainActivity;
import com.notus.fit.R;
import com.notus.fit.models.AllDevicesWeekReport;
import com.notus.fit.models.RequestTime;
import com.notus.fit.models.WeekReport;
import com.notus.fit.models.api_models.User;
import com.notus.fit.network.fitbit.FitbitSeriesRequest;
import com.notus.fit.network.misfit.MisfitDateRequest;
import com.notus.fit.network.weather.WeatherApiClient;
import com.notus.fit.network.weather.WeatherApiClient.WeatherApi;
import com.notus.fit.network.weather.models.WeatherResponse;
import com.notus.fit.utils.Devices;
import com.notus.fit.utils.FitnessUtils;
import com.notus.fit.utils.GpsTracker;
import com.notus.fit.utils.PrefManager;
import com.notus.fit.utils.PreferenceUtils;
import com.notus.fit.utils.TimeUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.joda.time.LocalDateTime;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;


public class ScheduleService extends IntentService {
    public static final int NOTIFICATION_ID = 337;
    public static final String SYNC_PREVIOUS_WEEK = "SYNC_PREV_WEEK";
    public static final String TAG = ScheduleService.class.getSimpleName();
    protected WeekReport androidWearWeekReport;
    protected boolean authInProgress;
    protected Integer currentWeekSteps;
    protected WeekReport fitbitWeekReport;
    protected boolean hasFitbit;
    protected boolean hasJawbone;
    protected boolean hasMisfit;
    protected boolean hasMoves;
    protected boolean hasWearDevice;
    protected boolean isGamesEnabled;
    protected WeekReport jawboneWeekReport;
    protected WeekReport misfitWeekReport;
    protected WeekReport movesWeekReport;
    protected Integer todaySteps;
    protected ParseObject userObject;
    protected WeekReport weekReport;
    Games.GamesOptions mGamesApiOptions;
    private GoogleApiClient mClient;
    private GoogleApiClient mGamesClient;
    private NotificationManager mNotificationManager;
    private GoogleApiClient mWearApiClient;


    public ScheduleService() {
        super("SchedulingService");
        this.authInProgress = false;
        this.hasFitbit = false;
        this.hasJawbone = false;
        this.hasWearDevice = false;
        this.hasMisfit = false;
        this.hasMoves = false;
        this.currentWeekSteps = 0;
        this.isGamesEnabled = false;
        this.mGamesApiOptions = Games.GamesOptions.builder().build();
    }

    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Starting service....");
        this.hasWearDevice = PrefManager.with(this).getBoolean(User.HAS_GOOGLEFIT, false);
        this.hasFitbit = PrefManager.with(this).getBoolean(User.HAS_FITBIT, false);
        this.hasJawbone = PrefManager.with(this).getBoolean(User.HAS_JAWBONE, false);
        this.hasMisfit = PrefManager.with(this).getBoolean(User.HAS_MISFIT, false);
        this.mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (this.hasWearDevice) {
            buildFitnessClient();
            initWearClient();
        }
        this.isGamesEnabled = PrefManager.with(this).getBoolean(getString(R.string.games_enabled), true);
        if (this.isGamesEnabled) {
            buildGamesClient();
        }
    }

    public void clearPreferences() {
        PrefManager.with(this).save(PreferenceUtils.OVERACHIEVER, false);
        PrefManager.with(this).save(PreferenceUtils.HALFWAY_DONE, false);
        PrefManager.with(this).save(PreferenceUtils.ALMOST_THERE, false);
        PrefManager.with(this).save(PreferenceUtils.GOAL_REACHED, false);
    }

    protected void onHandleIntent(Intent intent) {
        try {
            if (this.hasWearDevice) {
                this.mClient.blockingConnect(2000, TimeUnit.MILLISECONDS);
                this.mWearApiClient.blockingConnect(2000, TimeUnit.MILLISECONDS);
            }
            if (this.isGamesEnabled) {
                this.mGamesClient.blockingConnect(2000, TimeUnit.MILLISECONDS);
            }
            this.weekReport = getAllDevicesWeekReport(0);
            try {
                this.todaySteps = this.weekReport.getDays().get(this.weekReport.getRealListSize() - 1).getSteps();
            } catch (Exception e) {
                this.todaySteps = 0;
            }
            PrefManager.with(this).save(PreferenceUtils.WEEK_AVERAGE, String.valueOf(this.weekReport.getWeekAverage()));
            PrefManager.with(this).save(getString(R.string.today_steps), this.todaySteps.intValue());
            String id = PrefManager.with(this).getString(User.OBJECT_ID, null);
            Log.d(TAG, "Updating user with user id: " + id + " with week average: " + this.weekReport.getWeekAverage());
            if (id != null) {
                this.userObject = ParseQuery.getQuery(User.CLASS).whereEqualTo(User.OBJECT_ID, id).getFirst();
                this.userObject.put(User.STEPS_AVERAGE, this.weekReport.getWeekAverage());
                PrefManager.with(this).save(User.UNITS, this.userObject.getString(User.UNITS));
                this.userObject.save();
                this.userObject.pinInBackground();
            }
            Iterator it = this.weekReport.getStepList().iterator();
            while (it.hasNext()) {
                this.currentWeekSteps = this.currentWeekSteps + (Integer) it.next();
            }
            LocalDateTime now = LocalDateTime.now();
            if (now.getMinuteOfHour() > 20 && now.getHourOfDay() % 2 == 0) {
                updateWeather();
            }
            int stepGoal = Integer.parseInt(PrefManager.with(this).getString(getResources().getString(R.string.steps_goal), "10000"));
            if (this.mWearApiClient != null && this.mWearApiClient.isConnected()) {
                try {
                    PutDataMapRequest request = PutDataMapRequest.create("/com.notus.fit.steps");
                    DataMap map = request.getDataMap();
                    map.putFloat("temp", Float.parseFloat(PrefManager.with(this).getString("temp", AppEventsConstants.EVENT_PARAM_VALUE_NO)));
                    map.putString(User.UNITS, "imperial");
                    map.putInt("steps", this.todaySteps);
                    try {
                        map.putInt("weather_id", Integer.parseInt(PrefManager.with(this).getString("weather_id", "800")));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    map.putString("step_goal", String.valueOf(stepGoal));
                    Wearable.DataApi.putDataItem(this.mWearApiClient, request.asPutDataRequest());
                } catch (Exception ex2) {
                    ex2.printStackTrace();
                }
            }
            if (PrefManager.with(this).getBoolean(getString(R.string.notifications_enable), true)) {
                if (this.todaySteps != 0 && this.todaySteps < stepGoal / 2) {
                    clearPreferences();
                }
                if (!PrefManager.with(this).getBoolean(PreferenceUtils.OVERACHIEVER, false) && ((double) this.todaySteps) > ((double) stepGoal) * 1.25d) {
                    PrefManager.with(this).save(PreferenceUtils.OVERACHIEVER, true);
                    sendNotification(Devices.NOTUSFIT, "Overachiever!! You smashed your step goal of " + stepGoal + " with " + this.todaySteps + " steps!!");
                }
                if (!(PrefManager.with(this).getBoolean(PreferenceUtils.GOAL_REACHED, false) || PrefManager.with(this).getBoolean(PreferenceUtils.OVERACHIEVER, false) || this.todaySteps <= stepGoal)) {
                    PrefManager.with(this).save(PreferenceUtils.GOAL_REACHED, true);
                    sendNotification("Goal reached!", "Congratulations! You reached your goal with " + this.todaySteps + " steps.");
                }
                if (!(PrefManager.with(this).getBoolean(PreferenceUtils.ALMOST_THERE, false) || PrefManager.with(this).getBoolean(PreferenceUtils.GOAL_REACHED, false) || PrefManager.with(this).getBoolean(PreferenceUtils.OVERACHIEVER, false) || ((double) this.todaySteps) <= ((double) stepGoal) * 0.75d)) {
                    PrefManager.with(this).save(PreferenceUtils.ALMOST_THERE, true);
                    sendNotification(Devices.NOTUSFIT, "Keep up the good work! You need " + (stepGoal - this.todaySteps) + " steps to reach your step goal for today.");
                }
                if (!(PrefManager.with(this).getBoolean(PreferenceUtils.HALFWAY_DONE, false) || PrefManager.with(this).getBoolean(PreferenceUtils.ALMOST_THERE, false) || PrefManager.with(this).getBoolean(PreferenceUtils.GOAL_REACHED, false) || PrefManager.with(this).getBoolean(PreferenceUtils.OVERACHIEVER, false) || this.todaySteps <= stepGoal / 2)) {
                    PrefManager.with(this).save(PreferenceUtils.HALFWAY_DONE, true);
                    sendNotification(Devices.NOTUSFIT, "Keep up the good work! You are halfway through your goal with  " + this.todaySteps + " steps.");
                }
            }
        } catch (Exception ex22) {
            Log.d(TAG, "Service Exception Message: " + ex22.getMessage());
            ex22.printStackTrace();
        }
        if (this.isGamesEnabled) {
            try {
                Log.d(TAG, "Starting update of the leaderboard...");
                Games.Leaderboards.submitScore(this.mGamesClient, getString(R.string.leaderboard_max_steps_1_day), (long) this.todaySteps);
            } catch (Exception ex222) {
                Log.d(TAG, "Games Exception: " + ex222.getMessage());
            }
            try {
                Log.d(TAG, "Starting update of the weekly leader board...");
                Games.Leaderboards.submitScore(this.mGamesClient, getString(R.string.leaderboard_max_total_steps_in_1_week), (long) this.currentWeekSteps);
            } catch (Exception ex2222) {
                Log.d(TAG, "Games Exception: " + ex2222.getMessage());
            }
            try {
                syncPreviousWeek();
            } catch (Exception ex22222) {
                Log.d(TAG, "Games Exception: " + ex22222.getMessage());
            }
        }
    }

    public void syncPreviousWeek() {
        LocalDateTime localDateTime = LocalDateTime.now();
        if (localDateTime.getDayOfWeek() < 2 || localDateTime.getDayOfWeek() > 6) {
            PrefManager.with(this).save(SYNC_PREVIOUS_WEEK, false);
        } else if (!PrefManager.with(this).getBoolean(SYNC_PREVIOUS_WEEK, false)) {
            int totalSteps = 0;
            Iterator it = getAllDevicesWeekReport(1).getStepList().iterator();
            while (it.hasNext()) {
                Integer i = (Integer) it.next();
                totalSteps += i;
                Games.Leaderboards.submitScore(this.mGamesClient, getString(R.string.leaderboard_max_steps_1_day), (long) i);
            }
            Games.Leaderboards.submitScore(this.mGamesClient, getString(R.string.leaderboard_max_total_steps_in_1_week), (long) totalSteps);
            PrefManager.with(this).save(SYNC_PREVIOUS_WEEK, true);
        }
    }

    public void updateWeather() {
        try {
            GpsTracker tracker = new GpsTracker(this);
            float longitude = (float) tracker.getLongitude();
            float latitude = (float) tracker.getLatitude();
            tracker.stopUsingGPS();
            RestAdapter weatherRestAdapter = WeatherApiClient.getBaseRestAdapter(this);
            weatherRestAdapter.setLogLevel(LogLevel.FULL);
            WeatherApi weatherApi = weatherRestAdapter.create(WeatherApi.class);
            String units = "imperial";
            if (PrefManager.with(this).getString(User.UNITS, getString(R.string.pref_units_imperial)).equals(getString(R.string.pref_units_metric))) {
                units = "metric";
            }
            WeatherResponse response = weatherApi.getWeather(latitude, longitude, units, WeatherApiClient.API_KEY);
            PrefManager.with(this).save("temp", String.valueOf(response.getMainWeather().getTemp()));
            try {
                PrefManager.with(this).save("weather_id", String.valueOf(response.getWeather().get(0).getId()));
            } catch (Exception ex) {
                Log.d("WEATHER SERVICE", "Exception happened!");
                ex.printStackTrace();
            }
        } catch (Exception ex2) {
            Log.d("WEATHER SERVICE", "Exception happened!");
            ex2.printStackTrace();
        }
    }

    public WeekReport getAllDevicesWeekReport(int weekType) {
        RequestTime requestWear;
        MisfitDateRequest mfRequest;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.withDayOfWeek(1).withTime(0, 0, 0, 0);
        LocalDateTime lwEndDate = now.withDayOfWeek(7).minusWeeks(1).withTime(23, 59, 59, 999);
        LocalDateTime lwStartDate = startDate.minusWeeks(1).withTime(0, 0, 0, 0);
        if (weekType == 0) {
            requestWear = TimeUtils.getWeekRequest();
            mfRequest = new MisfitDateRequest(startDate.toDateTime().getMillis(), now.toDateTime().getMillis());
        } else {
            requestWear = TimeUtils.getPreviousWeekRequest();
            mfRequest = new MisfitDateRequest(lwStartDate.toDateTime().getMillis(), lwEndDate.toDateTime().getMillis());
        }
        if (this.hasWearDevice && this.mClient != null && this.mClient.isConnected()) {
            this.androidWearWeekReport = FitnessUtils.getAndroidWearWeekReport(requestWear, this, this.mClient);
            Log.d(TAG, "Android Wear Steps: " + this.androidWearWeekReport.getWeekAverage());
        } else {
            this.androidWearWeekReport = new WeekReport();
        }
        if (this.hasFitbit) {
            FitbitSeriesRequest seriesRequest = null;
            if (weekType == 0) {
                seriesRequest = new FitbitSeriesRequest().getCurrentWeek();
            } else if (weekType == 1) {
                seriesRequest = new FitbitSeriesRequest().getPreviousWeek();
            }
            this.fitbitWeekReport = FitnessUtils.getFitbitWeekReport(seriesRequest, this);
        } else {
            this.fitbitWeekReport = new WeekReport();
        }
        if (this.hasJawbone) {
            this.jawboneWeekReport = FitnessUtils.getJawboneWeekReport(weekType, this);
        } else {
            this.jawboneWeekReport = new WeekReport();
        }
        if (this.hasMisfit) {
            this.misfitWeekReport = FitnessUtils.getMisfitWeekReport(mfRequest, this);
            if (this.misfitWeekReport == null) {
                this.misfitWeekReport = new WeekReport();
            }
        } else {
            this.misfitWeekReport = new WeekReport();
        }
        if (this.hasMoves) {
            this.movesWeekReport = FitnessUtils.getMovesWeekReport(weekType, this);
        } else {
            this.movesWeekReport = new WeekReport();
        }
        return new AllDevicesWeekReport.Builder(this).setFitbitWeekReport(this.fitbitWeekReport).setGoogleFitWeekReport(this.androidWearWeekReport).setJawboneWeekReport(this.jawboneWeekReport).setMisfitWeekReport(this.misfitWeekReport).setMovesWeekReport(this.movesWeekReport).build();
    }

    private void sendNotification(String title, String msg) {
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        Builder mBuilder = new Builder(this).setDefaults(-1).setSmallIcon(R.drawable.ic_stat_notification_heart).setContentTitle(title).setColor(getResources().getColor(R.color.purple_600)).setStyle(new BigTextStyle().bigText(msg)).setOnlyAlertOnce(true).setContentText(msg).extend(new WearableExtender().setContentAction(R.drawable.wall1_720));
        mBuilder.setContentIntent(contentIntent);
        this.mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    public void buildFitnessClient() {
        Log.d(TAG, "Starting fitness client...");
        this.mClient = new GoogleApiClient.Builder(getApplicationContext()).addApi(Fitness.HISTORY_API).addApi(Fitness.CONFIG_API).addScope(new Scope("https://www.googleapis.com/auth/fitness.activity.write")).addConnectionCallbacks(new ConnectionCallbacks() {
            @Override
            public void onConnected(Bundle bundle) {
                if (ScheduleService.this.hasWearDevice) {
                    ScheduleService.this.mClient.connect();
                }
            }

            @Override
            public void onConnectionSuspended(int i) {
                if (i == 2) {
                    Log.i(ScheduleService.TAG, "Connection lost.  Cause: Network Lost.");
                } else if (i == 1) {
                    Log.i(ScheduleService.TAG, "Connection lost.  Reason: Service Disconnected");
                }
            }
        }).addOnConnectionFailedListener(new OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(ConnectionResult connectionResult) {
                if (Log.isLoggable(ScheduleService.TAG, 3)) {
                    Log.d(ScheduleService.TAG, "Disconnected from Google Api Service");
                }
            }
        }).build();
        this.mClient.connect();
    }

    public void buildGamesClient() {
        this.mGamesClient = new GoogleApiClient.Builder(getApplicationContext()).addApi(Games.API, this.mGamesApiOptions).addScope(Games.SCOPE_GAMES).addConnectionCallbacks(new ConnectionCallbacks() {
            @Override
            public void onConnected(Bundle bundle) {
                ScheduleService.this.mGamesClient.connect();
            }

            @Override
            public void onConnectionSuspended(int i) {
                if (i == 2) {
                    Log.i(ScheduleService.TAG, "Connection lost.  Cause: Network Lost.");
                } else if (i == 1) {
                    Log.i(ScheduleService.TAG, "Connection lost.  Reason: Service Disconnected");
                }
            }
        }).addOnConnectionFailedListener(new OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(ConnectionResult connectionResult) {
                if (Log.isLoggable(ScheduleService.TAG, 3)) {
                    Log.d(ScheduleService.TAG, "Disconnected from Google Api Service");
                }
            }
        }).build();
        this.mGamesClient.connect();
    }

    public void initWearClient() {
        this.mWearApiClient = new GoogleApiClient.Builder(getApplicationContext()).addConnectionCallbacks(new ConnectionCallbacks() {
            @Override
            public void onConnected(Bundle bundle) {

            }

            @Override
            public void onConnectionSuspended(int i) {

            }
        }).addOnConnectionFailedListener(new OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(ConnectionResult connectionResult) {

            }
        }).addApi(Wearable.API).build();
        this.mWearApiClient.connect();
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.hasWearDevice) {
            this.mClient.disconnect();
        }
    }
}
