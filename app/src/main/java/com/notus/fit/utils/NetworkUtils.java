package com.notus.fit.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.facebook.internal.ServerProtocol;
import com.notus.fit.BuildConfig;
import com.notus.fit.R;
import com.notus.fit.models.api_models.User;
import com.notus.fit.network.alarm.AlarmReceiver;
import com.notus.fit.network.alarm.ResetNotificationsReceiver;
import com.notus.fit.network.fitbit.FitbitClient;
import com.notus.fit.network.misfit.MisfitClient;
import com.notus.fit.network.moves.MoveApiClient;
import com.notus.fit.network.moves.MovesUser;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/3/2015 8:15 PM
 */
public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    public NetworkUtils() {
    }

    public static void cancelAlarms(Context context) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmScheduler = PendingIntent.getBroadcast(context, 0, new Intent(context, AlarmReceiver.class), 0);
        PendingIntent alarmReset = PendingIntent.getBroadcast(context, 1, new Intent(context, ResetNotificationsReceiver.class), 0);
        if (alarmMgr != null) {
            alarmMgr.cancel(alarmScheduler);
            alarmMgr.cancel(alarmReset);
        }
    }

    public static String getUserCountry(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String simCountry = tm.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) {
                return simCountry.toLowerCase(Locale.US);
            }
            if (tm.getPhoneType() != 2) {
                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) {
                    return networkCountry.toLowerCase(Locale.US);
                }
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }
        return null;
    }

    public static void setPreferences(ParseObject userObject, final Context context) {
        PrefManager.with(context).save(User.FIRST_NAME, userObject.getString(User.FIRST_NAME) != null ? userObject.getString(User.FIRST_NAME) : BuildConfig.FLAVOR);
        PrefManager.with(context).save(User.LAST_NAME, userObject.getString(User.LAST_NAME) != null ? userObject.getString(User.LAST_NAME) : BuildConfig.FLAVOR);
        PrefManager.with(context).save(PreferenceUtils.EMAIL, userObject.getString(PreferenceUtils.EMAIL) != null ? userObject.getString(PreferenceUtils.EMAIL) : BuildConfig.FLAVOR);
        PrefManager.with(context).save(PreferenceUtils.AVATAR_URL, userObject.getString(PreferenceUtils.AVATAR_URL) != null ? userObject.getString(PreferenceUtils.AVATAR_URL) : BuildConfig.FLAVOR);
        PrefManager.with(context).save(User.OBJECT_ID, userObject.getObjectId() != null ? userObject.getObjectId() : BuildConfig.FLAVOR);
        PrefManager.with(context).save(User.HAS_FITBIT, userObject.getBoolean(User.HAS_FITBIT));
        PrefManager.with(context).save(User.HAS_JAWBONE, userObject.getBoolean(User.HAS_JAWBONE));
        PrefManager.with(context).save(User.HAS_GOOGLEFIT, userObject.getBoolean(User.HAS_GOOGLEFIT));
        PrefManager.with(context).save(User.HAS_MISFIT, userObject.getBoolean(User.HAS_MISFIT));
        PrefManager.with(context).save(User.HAS_MOVES, userObject.getBoolean(User.HAS_MOVES));
        PrefManager.with(context).save(User.MOVES_TOKEN, userObject.getString(User.MOVES_TOKEN) != null ? userObject.getString(User.MOVES_TOKEN) : BuildConfig.FLAVOR);
        PrefManager.with(context).save(User.MOVES_REFRESH_TOKEN, userObject.getString(User.MOVES_REFRESH_TOKEN) != null ? userObject.getString(User.MOVES_REFRESH_TOKEN) : BuildConfig.FLAVOR);
        PrefManager.with(context).save(User.UNITS, userObject.getString(User.UNITS) != null ? userObject.getString(User.UNITS) : context.getString(R.string.pref_units_imperial));
        try {
            String[] fitbitToken = userObject.getString(User.FITBIT_TOKEN).split(",");
            if (fitbitToken.length > 0) {
                PrefManager.with(context).save(FitbitClient.FITBIT_TOKEN, fitbitToken[0]);
                PrefManager.with(context).save(FitbitClient.FITBIT_TOKEN_SECRET, fitbitToken[1]);
                PrefManager.with(context).save(FitbitClient.FITBIT_TOKEN_RAW, fitbitToken[2]);
            }
        } catch (Exception ex) {
            if (ex.getMessage() != null) {
                Log.d(LOG_TAG, ex.getMessage());
            } else {
                Log.d(LOG_TAG, "Exception!", ex);
            }
        }
        if (userObject.getBoolean(User.HAS_MOVES)) {
            MoveApiClient.getBaseRestAdapter(context).create(MoveApiClient.MovesConnector.class).getUser(new Callback<MovesUser>() {
                @Override
                public void success(MovesUser movesUser, Response response) {
                    PrefManager.with(context).save(MoveApiClient.MOVES_FIRST_DATE, movesUser.getProfile().getFirstDate());
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
        PrefManager.with(context).save(MisfitClient.MISFIT_TOKEN, userObject.getString(MisfitClient.MISFIT_TOKEN) != null ? userObject.getString(MisfitClient.MISFIT_TOKEN) : BuildConfig.FLAVOR);
        PrefManager.with(context).save(ServerProtocol.DIALOG_PARAM_ACCESS_TOKEN, userObject.getString(User.JAWBONE_TOKEN) != null ? userObject.getString(User.JAWBONE_TOKEN) : BuildConfig.FLAVOR);
        PrefManager.with(context).save(PreferenceUtils.WEEK_AVERAGE, String.valueOf(userObject.getInt(User.STEPS_AVERAGE)));
    }

    public static boolean signIn(final Context context, String username) {
        ParseQuery.getQuery("user").whereEqualTo(PreferenceUtils.EMAIL, username).getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (parseObject != null) {
                    NetworkUtils.setPreferences(parseObject, context);
                }
            }
        });
        return false;
    }
}
