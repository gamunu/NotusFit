package com.notus.fit.network.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.facebook.internal.ServerProtocol;
import com.notus.fit.BuildConfig;
import com.notus.fit.models.api_models.User;
import com.notus.fit.network.fitbit.FitbitClient;
import com.notus.fit.network.misfit.MisfitClient;
import com.notus.fit.utils.PrefManager;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class UpdateDeviceToken extends IntentService {

    public static final String LOG_TAG = UpdateDeviceToken.class.getSimpleName();

    public UpdateDeviceToken() {
        super(UpdateDeviceToken.class.getSimpleName());
    }

    protected void onHandleIntent(Intent intent) {
        String id = PrefManager.with(this).getString(User.OBJECT_ID, null);
        if (id != null) {
            try {
                ParseObject userObject = ParseQuery.getQuery(User.CLASS).whereEqualTo(User.OBJECT_ID, id).getFirst();
                String fitbitToken = (PrefManager.with(this).getString(FitbitClient.FITBIT_TOKEN, BuildConfig.FLAVOR) + "," + PrefManager.with(this).getString(FitbitClient.FITBIT_TOKEN_SECRET, BuildConfig.FLAVOR)) + "," + PrefManager.with(this).getString(FitbitClient.FITBIT_TOKEN_RAW, BuildConfig.FLAVOR);
                String jawboneToken = PrefManager.with(this).getString(ServerProtocol.DIALOG_PARAM_ACCESS_TOKEN, BuildConfig.FLAVOR);
                String misfitToken = PrefManager.with(this).getString(MisfitClient.MISFIT_TOKEN, BuildConfig.FLAVOR);
                userObject.put(User.FITBIT_TOKEN, fitbitToken);
                userObject.put(User.JAWBONE_TOKEN, jawboneToken);
                userObject.put(MisfitClient.MISFIT_TOKEN, misfitToken);
                userObject.put(User.HAS_FITBIT, PrefManager.with(this).getBoolean(User.HAS_FITBIT, false));
                userObject.put(User.HAS_JAWBONE, PrefManager.with(this).getBoolean(User.HAS_JAWBONE, false));
                userObject.put(User.HAS_MISFIT, PrefManager.with(this).getBoolean(User.HAS_MISFIT, false));
                userObject.put(User.HAS_GOOGLEFIT, PrefManager.with(this).getBoolean(User.HAS_GOOGLEFIT, false));
                userObject.put(User.HAS_MOVES, PrefManager.with(this).getBoolean(User.HAS_MOVES, false));
                userObject.put(User.MOVES_TOKEN, PrefManager.with(this).getString(User.MOVES_TOKEN, BuildConfig.FLAVOR));
                userObject.put(User.MOVES_REFRESH_TOKEN, PrefManager.with(this).getString(User.MOVES_REFRESH_TOKEN, BuildConfig.FLAVOR));
                userObject.save();
            } catch (ParseException ex) {
                Log.d(LOG_TAG, ex.getMessage());
            }
        }
    }
}
