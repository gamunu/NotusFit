package com.notus.fit.network.jawbone;

import android.content.Context;
import android.content.Intent;
import android.net.Uri.Builder;
import android.util.Log;

import com.facebook.internal.ServerProtocol;
import com.jawbone.upplatformsdk.api.response.OauthAccessTokenResponse;
import com.jawbone.upplatformsdk.oauth.OauthUtils;
import com.jawbone.upplatformsdk.oauth.OauthWebViewActivity;
import com.jawbone.upplatformsdk.utils.UpPlatformSdkConstants.UpPlatformAuthScope;
import com.notus.fit.BuildConfig;
import com.notus.fit.models.api_models.User;
import com.notus.fit.network.misfit.MisfitClient;
import com.notus.fit.utils.PrefManager;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class JawboneAPI {
    public static final String CLIENT_ID = "lHnpDIKZwqc";
    public static final String CLIENT_SECRET = "6a3ed230737e1f58139de3d32f428e9dafd99705";
    public static final String OAUTH_CALLBACK_URL = "http://localhost/fitness_telematics?";
    private static final String TAG = JawboneAPI.class.getSimpleName();
    private Callback<OauthAccessTokenResponse> accessTokenRequestListener;
    private List<UpPlatformAuthScope> authScope;
    private Context context;
    private Class destinationActivity;

    private Callback<OauthAccessTokenResponse> TokenCallback = new Callback<OauthAccessTokenResponse>() {

        @Override
        public void success(OauthAccessTokenResponse result, Response response) {
            if (result.access_token != null) {
                try {
                    ParseObject userObject = ParseQuery.getQuery(User.CLASS).whereEqualTo(User.OBJECT_ID, PrefManager.with(JawboneAPI.this.context).getString(User.OBJECT_ID, BuildConfig.FLAVOR)).getFirst();
                    userObject.put(User.HAS_JAWBONE, true);
                    userObject.put(User.JAWBONE_TOKEN, result.access_token);
                    userObject.save();
                } catch (ParseException ex) {
                    Log.d(JawboneAPI.TAG, ex.getMessage());
                }
                PrefManager.with(JawboneAPI.this.context).save(ServerProtocol.DIALOG_PARAM_ACCESS_TOKEN, result.access_token);
                PrefManager.with(JawboneAPI.this.context).save("refresh_token", result.refresh_token);
                return;
            }
            Log.e(JawboneAPI.TAG, "accessToken not returned by Oauth call, exiting...");
        }

        @Override
        public void failure(RetrofitError retrofitError) {
            Log.e(JawboneAPI.TAG, "failed to get accessToken:" + retrofitError.getMessage());
        }
    };

    public JawboneAPI(Context context) {
        this.accessTokenRequestListener = TokenCallback;
        this.context = context;
    }

    public JawboneAPI(Context context, Class destinationActivity) {
        this.accessTokenRequestListener = TokenCallback;
        this.context = context;
        this.destinationActivity = destinationActivity;
        this.authScope = new ArrayList<>();
        this.authScope.add(UpPlatformAuthScope.ALL);
    }

    public Context getContext() {
        return this.context;
    }

    public Callback<OauthAccessTokenResponse> getAccessTokenRequestListener() {
        return this.accessTokenRequestListener;
    }

    public List<UpPlatformAuthScope> getAuthScope() {
        return this.authScope;
    }

    public Intent getIntentForWebView() {
        Builder builder = OauthUtils.setOauthParameters(CLIENT_ID, OAUTH_CALLBACK_URL, this.authScope);
        Intent intent = new Intent(context, OauthWebViewActivity.class);
        intent.putExtra(MisfitClient.AUTH_URI, builder.build());
        return intent;
    }

    public String getAccessToken() {
        return PrefManager.with(this.context).getString(ServerProtocol.DIALOG_PARAM_ACCESS_TOKEN, null);
    }

    public HashMap<String, Integer> getWeekMoveEventsListRequestParams() {
        HashMap<String, Integer> queryHashMap = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        queryHashMap.put("start_time", now.withDayOfWeek(1).withTime(0, 0, 0, 0).getMillisOfSecond());
        queryHashMap.put("end_time", now.getMillisOfSecond());
        return queryHashMap;
    }

    public HashMap<String, Integer> getPreviousWeekMoveEventsListRequestParams() {
        HashMap<String, Integer> queryHashMap = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endDate = now.withDayOfWeek(7).minusWeeks(1).withTime(23, 59, 59, 999);
        queryHashMap.put("start_time", now.withDayOfWeek(1).withTime(0, 0, 0, 0).minusWeeks(1).getMillisOfSecond());
        queryHashMap.put("end_time", endDate.getMillisOfSecond());
        return queryHashMap;
    }
}
