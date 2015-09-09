package com.notus.fit.network.misfit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri.Builder;
import android.util.Log;

import com.facebook.internal.ServerProtocol;
import com.notus.fit.BuildConfig;
import com.notus.fit.models.api_models.User;
import com.notus.fit.models.misfit.MisfitToken;
import com.notus.fit.network.Client;
import com.notus.fit.utils.PrefManager;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import retrofit.Callback;
import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class MisfitClient {
    public static final String ACCESS_CODE = "misfit_access_code";
    public static final String API_URL = "https://api.misfitwearables.com";
    public static final String AUTH_URI = "auth_uri";
    public static final String MISFIT_APP_KEY = "ngfYaXSjJorI1dA4";
    public static final String MISFIT_APP_SECRET = "SYhk7fVPsFnGSVhVBjUeC9DcDldfMagq";
    public static final int MISFIT_AUTORIZE_ACCES_CODE = 18723;
    public static final String MISFIT_TOKEN = "misfit_token";
    public static final String REDIRECTION_URI = "http://gabilheri.com/fithub?";
    public static final String RESPONSE_TYPE = "token";
    public static final String TAG = MisfitClient.class.getSimpleName();
    private static RestAdapter restAdapter;
    private static MisfitApiInterface restApiInterface;
    private Callback<MisfitToken> accessTokenRequestListener;
    private Context context;

    public MisfitClient(Context context) {
        this.accessTokenRequestListener = new Callback<MisfitToken>() {
            @Override
            public void success(MisfitToken result, Response response) {
                if (result.getToken() != null) {
                    try {
                        ParseObject userObject = ParseQuery.getQuery(User.CLASS).whereEqualTo(User.OBJECT_ID, PrefManager.with(MisfitClient.this.context).getString(User.OBJECT_ID, BuildConfig.FLAVOR)).getFirst();
                        userObject.put(User.HAS_MISFIT, Boolean.valueOf(true));
                        userObject.put(MisfitClient.MISFIT_TOKEN, result.getToken());
                        userObject.save();
                    } catch (ParseException ex) {
                        Log.d(MisfitClient.TAG, ex.getMessage());
                    }
                    PrefManager.with(MisfitClient.this.context).save(User.HAS_MISFIT, true);
                    PrefManager.with(MisfitClient.this.context).save(MisfitClient.MISFIT_TOKEN, result.getToken());
                    return;
                }
                Log.e(MisfitClient.TAG, "accessToken not returned by Oauth call, exiting...");

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.e(MisfitClient.TAG, "failed to get misfit accessToken:" + retrofitError.getMessage());
            }
        };
        this.context = context;
    }

    public static Builder setOauthParameters(String clientId, String callbackUrl, String scope) {
        Builder builder = setBaseParameters();
        builder.appendPath("auth");
        builder.appendPath("dialog");
        builder.appendPath("authorize");
        builder.appendQueryParameter(ServerProtocol.DIALOG_PARAM_RESPONSE_TYPE, "code");
        builder.appendQueryParameter(ServerProtocol.DIALOG_PARAM_CLIENT_ID, clientId);
        builder.appendQueryParameter(ServerProtocol.DIALOG_PARAM_SCOPE, scope);
        builder.appendQueryParameter(ServerProtocol.DIALOG_PARAM_REDIRECT_URI, callbackUrl);
        return builder;
    }

    public static RestAdapter getBaseRestAdapter() {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint(API_URL);
        builder.setErrorHandler(new CustomErrorHandler());
        return builder.build();
    }

    public static RestAdapter getBaseRestAdapter(RequestInterceptor interceptor, Context context) {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setClient(new OkClient(Client.getInstance(context)));
        builder.setEndpoint(API_URL);
        builder.setRequestInterceptor(interceptor);
        return builder.build();
    }

    public static MisfitApiInterface getRestApiInterface() {
        RestAdapter restAdapter = getBaseRestAdapter();
        if (restApiInterface == null) {
            restApiInterface = (MisfitApiInterface) restAdapter.create(MisfitApiInterface.class);
        }
        return restApiInterface;
    }

    public static Builder setBaseParameters() {
        Builder builder = new Builder();
        builder.scheme("https");
        builder.authority("api.misfitwearables.com");
        return builder;
    }

    public Intent getIntentForWebView() {
        Builder builder = setOauthParameters(MISFIT_APP_KEY, REDIRECTION_URI, "public,birthday,email");
        Intent intent = new Intent(MisfitOauthActivity.class.getName());
        intent.putExtra(AUTH_URI, builder.build());
        return intent;
    }

    public MisfitApiInterface getAuthenticatedInterface() {
        RestAdapter restAdapter = getBaseRestAdapter(new MisfitInterceptor(this.context), this.context);
        if (restApiInterface == null) {
            restApiInterface = (MisfitApiInterface) restAdapter.create(MisfitApiInterface.class);
        }
        return restApiInterface;
    }

    public Callback<MisfitToken> getAccessTokenRequestListener() {
        return this.accessTokenRequestListener;
    }

    private static class CustomErrorHandler implements ErrorHandler {
        private CustomErrorHandler() {
        }

        public Throwable handleError(RetrofitError cause) {
            Response r = cause.getResponse();
            if (r == null || r.getStatus() != 401) {
                return cause;
            }
            return cause.getCause();
        }
    }
}
