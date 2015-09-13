package com.notus.fit.utils;


import android.content.Context;
import android.util.Log;

import com.facebook.AppEventsConstants;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataSource.Builder;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResult;
import com.jawbone.upplatformsdk.api.ApiManager;
import com.jawbone.upplatformsdk.builders.Params;
import com.jawbone.upplatformsdk.endpointModels.move.MoveItem;
import com.notus.fit.R;
import com.notus.fit.models.DayReport;
import com.notus.fit.models.ReportDate;
import com.notus.fit.models.RequestTime;
import com.notus.fit.models.WeekReport;
import com.notus.fit.models.fitbit.FitbitDailyStep;
import com.notus.fit.models.fitbit.FitbitWeekSteps;
import com.notus.fit.models.misfit.MisfitDay;
import com.notus.fit.models.misfit.MisfitSummary;
import com.notus.fit.network.fitbit.FitbitClient;
import com.notus.fit.network.fitbit.FitbitInterceptor;
import com.notus.fit.network.fitbit.FitbitSeriesRequest;
import com.notus.fit.network.jawbone.JawboneAPI;
import com.notus.fit.network.misfit.MisfitClient;
import com.notus.fit.network.misfit.MisfitDateRequest;
import com.notus.fit.network.moves.MoveApiClient;
import com.notus.fit.network.moves.MovesActivity;
import com.notus.fit.network.moves.MovesSummary;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;

/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/3/2015 8:10 PM
 */
public class FitnessUtils {
    public static final int CURRENT_WEEK = 0;
    public static final DataSource ESTIMATED_STEP_DELTAS;
    public static final String LOG_TAG;
    public static final int PAST_WEEK = 1;
    public static final String UNIT_IMPERIAL = "Imperial System";
    public static final String UNIT_METRIC = "Metric System";
    protected static final String DATE_FORMAT = "yyyy.MM.dd HH:mm:ss";

    static {
        LOG_TAG = FitnessUtils.class.getSimpleName();
        ESTIMATED_STEP_DELTAS = new Builder().setDataType(DataType.TYPE_STEP_COUNT_DELTA).setType(PAST_WEEK).setStreamName("estimated_steps").setAppPackageName("com.google.android.gms").build();
    }

    public static DataReadRequest queryFitnessData(RequestTime requestTime) {
        return new DataReadRequest.Builder().aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA).bucketByTime(PAST_WEEK, TimeUnit.DAYS).setTimeRange(requestTime.getStartTime(), requestTime.getEndTime(), TimeUnit.MILLISECONDS).build();
    }

    public static void printData(DataReadResult dataReadResult) {
        if (dataReadResult.getBuckets().size() > 0) {
            Log.i(LOG_TAG, "Number of returned buckets of DataSets is: " + dataReadResult.getBuckets().size());
            for (Bucket bucket : dataReadResult.getBuckets()) {
                for (DataSet dataSet : bucket.getDataSets()) {
                    dumpDataSet(dataSet);
                }
            }
        } else if (dataReadResult.getDataSets().size() > 0) {
            Log.i(LOG_TAG, "Number of returned DataSets is: " + dataReadResult.getDataSets().size());
            for (DataSet dataSet2 : dataReadResult.getDataSets()) {
                dumpDataSet(dataSet2);
            }
        }
    }

    public static DataSet createDataForRequest(Context context, DataType dataType, int dataSourceType, Object values, long startTime, long endTime, TimeUnit timeUnit) {
        DataSet dataSet = DataSet.create(new Builder().setAppPackageName(context).setDataType(dataType).setType(dataSourceType).build());
        DataPoint dataPoint = dataSet.createDataPoint().setTimeInterval(startTime, endTime, timeUnit);
        if (values instanceof Integer) {
            int[] iArr = new int[PAST_WEEK];
            iArr[CURRENT_WEEK] = (Integer) values;
            dataPoint = dataPoint.setIntValues(iArr);
        } else {
            float[] fArr = new float[PAST_WEEK];
            fArr[CURRENT_WEEK] = (Float) values;
            dataPoint = dataPoint.setFloatValues(fArr);
        }
        dataSet.add(dataPoint);
        return dataSet;
    }

    public static float convertWeight(float weight, String units) {
        if (units.equals(UNIT_IMPERIAL)) {
            return weight / 2.2046227f;
        }
        return weight;
    }

    private static void dumpDataSet(DataSet dataSet) {
        Log.i(LOG_TAG, "Data returned for Data type: " + dataSet.getDataType().getName());
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        for (DataPoint dp : dataSet.getDataPoints()) {
            Log.i(LOG_TAG, "Data point:");
            Log.i(LOG_TAG, "\tType: " + dp.getDataType().getName());
            Log.i(LOG_TAG, "\tStart: " + dateFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)));
            Log.i(LOG_TAG, "\tEnd: " + dateFormat.format(dp.getEndTime(TimeUnit.MILLISECONDS)));
            for (Field field : dp.getDataType().getFields()) {
                Log.i(LOG_TAG, "\tField: " + field.getName() + " Value: " + dp.getValue(field));
            }
        }
    }

    public static WeekReport getAndroidWearWeekReport(RequestTime requestTime, Context context, GoogleApiClient mClient) {
        try {
            DataReadRequest readRequest = queryFitnessData(requestTime);
            List<String> fullDates = requestTime.getFullDates();
            List<String> localDates = requestTime.getLocalDates();
            String[] weekdays = context.getResources().getStringArray(R.array.weekdays_short);
            Map<Integer, DayReport> dayReports = new HashMap<>();
            DataReadResult dataReadResult = Fitness.HistoryApi.readData(mClient, readRequest).await(1, TimeUnit.MINUTES);
            //printData(dataReadResult);
           // DataSet daSet = dataReadResult.getDataSet(DataType.STEP_COUNT_DELTA);
            int counter = CURRENT_WEEK;
            for (Bucket bucket : dataReadResult.getBuckets()) {
                for (DataSet dataSet : bucket.getDataSets()) {
                    for (DataPoint dp : dataSet.getDataPoints()) {
                        DayReport dayReport = new DayReport();
                        dayReport.setSteps(Integer.parseInt(dp.getValue(Field.FIELD_STEPS).toString()));
                        ReportDate rD = new ReportDate(LocalDate.parse(localDates.get(counter)));
                        dayReport.setWeekDay(weekdays[counter]);
                        dayReport.setFullDate(fullDates.get(counter));
                        dayReports.put(rD.getWeekDayNum(), dayReport);
                        counter += PAST_WEEK;
                    }
                }
            }
            return new WeekReport.Builder(context).setDays(dayReports).setDevice(Devices.GOOGLE_FIT).build();
        } catch (Exception ex) {
            Log.e(LOG_TAG, "Error getting Wear Report", ex);
            return new WeekReport();
        }
    }

    public static WeekReport getFitbitWeekReport(FitbitSeriesRequest seriesRequest, Context context) {
        try {
            FitbitWeekSteps fitbitDailySteps = FitbitClient.getBaseRestAdapter(new FitbitInterceptor(context, FitbitClient.getUrlStepsSeries(seriesRequest.getDate(), seriesRequest.getPeriod())), context)
                    .create(FitbitClient.Activities.class)
                    .getTimeSeries(seriesRequest.getDate(), seriesRequest.getPeriod());
            Map<Integer, DayReport> fitbitDayReports = new HashMap<>();
            if (fitbitDailySteps.getFitbitDailySteps() != null) {
                for (FitbitDailyStep f : fitbitDailySteps.getFitbitDailySteps()) {
                    ReportDate reportDate = new ReportDate(LocalDate.parse(f.getDateTime()));
                    DayReport dayReport = new DayReport();
                    dayReport.setSteps(Integer.parseInt(f.getValue()));
                    dayReport.setWeekDay(reportDate.getWeekDayShort());
                    dayReport.setFullDate(reportDate.getFullDateString());
                    fitbitDayReports.put(reportDate.getWeekDayNum(), dayReport);
                }
            }
            return new WeekReport.Builder(context).setDays(fitbitDayReports).setDevice(Devices.FITBIT).build();
        } catch (Exception ex) {
            Log.e(LOG_TAG, "Error getting Fitbit Report", ex);
            return new WeekReport();
        }
    }

    public static WeekReport getMisfitWeekReport(MisfitDateRequest dateRequest, Context context) {
        try {
            MisfitSummary summary = new MisfitClient(context).getAuthenticatedInterface().getSummary(TimeUtils.sanitizeDate(dateRequest.getStartDate()), TimeUtils.sanitizeDate(dateRequest.getEndDate()), true);
            Map<Integer, DayReport> misfitDayReports = new HashMap<>();
            for (MisfitDay d : summary.getSummary()) {
                ReportDate reportDate = new ReportDate(LocalDate.parse(d.getDate()));
                DayReport dayReport = new DayReport();
                dayReport.setSteps(d.getSteps());
                dayReport.setWeekDay(reportDate.getWeekDayShort());
                dayReport.setFullDate(reportDate.getFullDateString());
                misfitDayReports.put(reportDate.getWeekDayNum(), dayReport);
            }
            return new WeekReport.Builder(context).setDays(misfitDayReports).setDevice(Devices.MISFIT).build();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error getting MisFit Report", e);
            return new WeekReport();
        }
    }

    public static WeekReport getMovesWeekReport(int weekType, Context context) {
        try {
            List<MovesActivity> movesActivity;
            LocalDate now = LocalDate.now();
            LocalDate startDate = now.withDayOfWeek(PAST_WEEK);
            LocalDate endDate = now.withDayOfWeek(7);
            if (weekType == PAST_WEEK) {
                startDate = startDate.minusWeeks(PAST_WEEK);
                endDate = endDate.minusWeeks(PAST_WEEK);
            }
            RestAdapter restAdapter = MoveApiClient.getBaseRestAdapter(context);
            restAdapter.setLogLevel(LogLevel.FULL);
            MoveApiClient.MovesConnector movesConnecor = restAdapter.create(MoveApiClient.MovesConnector.class);
            DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
            LocalDate sDate = LocalDate.parse(startDate.toString(), format);
            LocalDate eDate = LocalDate.parse(endDate.toString(), format);
            String uStart = PrefManager.with(context).getString(MoveApiClient.MOVES_FIRST_DATE, AppEventsConstants.EVENT_PARAM_VALUE_YES);
            int year = Integer.parseInt(uStart.substring(CURRENT_WEEK, 4));
            LocalDate localDate = new LocalDate(year, Integer.parseInt(uStart.substring(4, 6)), Integer.parseInt(uStart.substring(6, 8)));
            Log.d(LOG_TAG, "S DATE: " + sDate.toString());
            Log.d(LOG_TAG, "User S DATE: " + localDate.toString());
            if (sDate.isAfter(localDate)) {
                if (eDate.isAfter(now)) {
                    movesActivity = movesConnecor.getActivity(sDate.toString(), now.toString());
                } else {
                    movesActivity = movesConnecor.getActivity(sDate.toString(), eDate.toString());
                }
            } else if (weekType != PAST_WEEK) {
                movesActivity = movesConnecor.getActivity(localDate.toString(), now.toString());
            } else if (!eDate.isAfter(localDate)) {
                Log.d(LOG_TAG, "Returning a new week for moves past week...");
                return new WeekReport();
            } else if (eDate.isAfter(now)) {
                movesActivity = movesConnecor.getActivity(localDate.toString(), now.toString());
            } else {
                movesActivity = movesConnecor.getActivity(localDate.toString(), eDate.toString());
            }
            Map<Integer, DayReport> dayReports = new HashMap<>();
            if (movesActivity != null) {
                for (MovesActivity m : movesActivity) {
                    if (m.getSummary() != null) {
                        ReportDate reportDate = new ReportDate(TimeUtils.convertFromJawboneDate(Integer.parseInt(m.getDate())));
                        DayReport dayReport = new DayReport();
                        dayReport.setWeekDay(reportDate.getWeekDayShort());
                        dayReport.setFullDate(reportDate.getFullDateString());
                        int steps = CURRENT_WEEK;
                        for (MovesSummary summary : m.getSummary()) {
                            steps += summary.getSteps();
                        }
                        dayReport.setSteps(steps);
                        dayReports.put(reportDate.getWeekDayNum(), dayReport);
                    }
                }
            }
            return new WeekReport.Builder(context).setDays(dayReports).setDevice(Devices.MOVES).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new WeekReport();
        }
    }

    public static WeekReport getJawboneWeekReport(int weekType, Context context) {
        try {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startDate = now.withTime(CURRENT_WEEK, CURRENT_WEEK, CURRENT_WEEK, CURRENT_WEEK).withDayOfWeek(PAST_WEEK);
            LocalDateTime endDate = now.withTime(23, 59, 59, 999).withDayOfWeek(7);
            if (weekType == PAST_WEEK) {
                startDate = startDate.minusWeeks(PAST_WEEK);
                endDate = endDate.minusWeeks(PAST_WEEK);
            }
            JawboneAPI jawboneAPI = new JawboneAPI(context);
            HashMap<String, Integer> jawboneRequestMap = new Params.Builder().setStartTime((int) (startDate.toDateTime().getMillis() / 1000)).setEndTime((int) (endDate.toDateTime().getMillis() / 1000)).build();
            ApiManager.getRequestInterceptor().setAccessToken(jawboneAPI.getAccessToken());
            List<MoveItem> moveItems = ApiManager.getRestApiInterface().getMoveEventsList("v.1.1", jawboneRequestMap).getMoveData().getMoveItems();
            Map<Integer, DayReport> dayReports = new HashMap<>();
            if (moveItems.size() < PAST_WEEK) {
                return new WeekReport();
            }
            for (MoveItem m : moveItems) {
                ReportDate reportDate = new ReportDate(TimeUtils.convertFromJawboneDate(m.getDate()));
                DayReport dayReport = new DayReport();
                dayReport.setWeekDay(reportDate.getWeekDayShort());
                dayReport.setFullDate(reportDate.getFullDateString());
                dayReport.setSteps(m.getDetails().getSteps());
                dayReports.put(reportDate.getWeekDayNum(), dayReport);
            }
            return new WeekReport.Builder(context).setDays(dayReports).setDevice(Devices.JAWBONE).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new WeekReport();
        }
    }

    public static int getResourceForButton(Context context, String text) {
        if (text.equals(context.getString(R.string.connect_google_fit))) {
            return R.drawable.google_fit_btn_selector;
        }
        if (text.equals(context.getString(R.string.connect_jawbone)) || text.equals(context.getString(R.string.connect_misfit))) {
            return R.drawable.jawbone_btn_selector;
        }
        if (text.equals(context.getString(R.string.connect_fitbit))) {
            return R.drawable.fitbit_btn_selector;
        }
        return CURRENT_WEEK;
    }
}
