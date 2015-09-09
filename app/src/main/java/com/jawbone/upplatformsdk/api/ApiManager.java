package com.jawbone.upplatformsdk.api;

import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import retrofit.RestAdapter.LogLevel;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ApiManager {
    private static RestAdapter restAdapter;
    private static ApiHeaders restApiHeaders;
    private static RestApiInterface restApiInterface;

    private static RestAdapter getRestAdapter() {
        if (restAdapter == null) {
            restAdapter = new Builder().setRequestInterceptor(getRequestInterceptor()).setLogLevel(LogLevel.FULL).setErrorHandler(new CustomErrorHandler()).setEndpoint("https://jawbone.com").build();
        }
        return restAdapter;
    }

    public static RestApiInterface getRestApiInterface() {
        restAdapter = getRestAdapter();
        if (restApiInterface == null) {
            restApiInterface = (RestApiInterface) restAdapter.create(RestApiInterface.class);
        }
        return restApiInterface;
    }

    public static ApiHeaders getRequestInterceptor() {
        if (restApiHeaders == null) {
            restApiHeaders = new ApiHeaders();
        }
        return restApiHeaders;
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
