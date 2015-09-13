package com.notus.fit.network.misfit;

import com.notus.fit.models.misfit.MisfitSummary;
import com.notus.fit.models.misfit.MisfitToken;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface MisfitApiInterface {
    @POST("/auth/tokens/exchange")
    void getAccessToken(@Body RequestTokenBody requestTokenBody, Callback<MisfitToken> callback);

    @GET("/move/resource/v1/user/me/activity/summary")
    MisfitSummary getSummary(@Query("start_date") String str, @Query("end_date") String str2, @Query("detail") boolean z);
}
