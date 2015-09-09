package com.jawbone.upplatformsdk.api;

import com.jawbone.upplatformsdk.api.response.OauthAccessTokenResponse;
import com.jawbone.upplatformsdk.endpointModels.Endpoint;
import com.jawbone.upplatformsdk.endpointModels.body.Body;
import com.jawbone.upplatformsdk.endpointModels.body.BodyCompositionEvent;
import com.jawbone.upplatformsdk.endpointModels.move.Move;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

public interface RestApiInterface {
    @POST("/nudge/api/{version}/users/@me/body_events")
    @Multipart
    void createBodyEvent(@Path("version") String str, @PartMap HashMap<String, Object> hashMap, Callback<BodyCompositionEvent> callback);

    @POST("/nudge/api/{version}/users/@me/generic_events")
    @Multipart
    void createCustomEvent(@Path("version") String str, @PartMap HashMap<String, Object> hashMap, Callback<Object> callback);

    @POST("/nudge/api/{version}/users/@me/meals")
    @Multipart
    void createMealEvent(@Path("version") String str, @PartMap HashMap<String, Object> hashMap, Callback<Object> callback);

    @POST("/nudge/api/{version}/users/@me/mood")
    @Multipart
    void createMoodEvent(@Path("version") String str, @PartMap HashMap<String, Object> hashMap, Callback<Object> callback);

    @POST("/nudge/api/{version}/users/@me/goals")
    @Multipart
    void createOrUpdateUsersGoals(@Path("version") String str, @PartMap HashMap<String, Object> hashMap, Callback<Object> callback);

    @POST("/nudge/api/{version}/users/@me/sleeps")
    @Multipart
    void createSleepEvent(@Path("version") String str, @PartMap HashMap<String, Object> hashMap, Callback<Object> callback);

    @POST("/nudge/api/{version}/users/@me/workouts")
    @Multipart
    void createWorkoutEvent(@Path("version") String str, @PartMap HashMap<String, Object> hashMap, Callback<Object> callback);

    @DELETE("/nudge/api/{version}/body_events/{xid}")
    void deleteBodyEvent(@Path("version") String str, @Path("xid") String str2, Callback<Endpoint> callback);

    @DELETE("/nudge/api/{version}/generic_events/{xid}")
    void deleteCustomEvent(@Path("version") String str, @Path("xid") String str2, Callback<Object> callback);

    @DELETE("/nudge/api/{version}/meals/{xid}")
    void deleteMealEvent(@Path("version") String str, @Path("xid") String str2, Callback<Object> callback);

    @DELETE("/nudge/api/{version}/mood/{xid}")
    void deleteMoodEvent(@Path("version") String str, @Path("xid") String str2, Callback<Object> callback);

    @DELETE("/nudge/api/{version}/sleeps/{xid}")
    void deleteSleepEvent(@Path("version") String str, @Path("xid") String str2, Callback<Object> callback);

    @DELETE("/nudge/api/{version}/workouts/{xid}")
    void deleteWorkoutEvent(@Path("version") String str, @Path("xid") String str2, Callback<Object> callback);

    @GET("/auth/oauth2/token?grant_type=authorization_code")
    void getAccessToken(@Query("client_id") String str, @Query("client_secret") String str2, @Query("code") String str3, Callback<OauthAccessTokenResponse> callback);

    @GET("/nudge/api/{version}/users/@me/bandevents")
    void getBandEvents(@Path("version") String str, @QueryMap HashMap<String, Integer> hashMap, Callback<Object> callback);

    @GET("/nudge/api/{version}/body_events/{xid}")
    void getBodyEvent(@Path("version") String str, @Path("xid") String str2, Callback<BodyCompositionEvent> callback);

    @GET("/nudge/api/{version}/users/@me/body_events")
    void getBodyEventsList(@Path("version") String str, @QueryMap HashMap<String, Integer> hashMap, Callback<Body> callback);

    @GET("/nudge/api/{version}/users/@me/generic_events")
    void getCustomEventsList(@Path("version") String str, @QueryMap HashMap<String, Integer> hashMap, Callback<Object> callback);

    @GET("/nudge/api/{version}/meals/{xid}")
    void getMealEvent(@Path("version") String str, @Path("xid") String str2, Callback<Object> callback);

    @GET("/nudge/api/{version}/users/@me/meals")
    void getMealEventsList(@Path("version") String str, @QueryMap HashMap<String, Integer> hashMap, Callback<Object> callback);

    @GET("/nudge/api/{version}/mood/{xid}")
    void getMoodEvent(@Path("version") String str, @Path("xid") String str2, Callback<Object> callback);

    @GET("/nudge/api/{version}/users/@me/mood")
    void getMoodEventsList(@Path("version") String str, @Query("date") String str2, Callback<Object> callback);

    @GET("/nudge/api/{version}/moves/{xid}")
    void getMoveEvent(@Path("version") String str, @Path("xid") String str2, Callback<Object> callback);

    @GET("/nudge/api/{version}/users/@me/moves")
    Move getMoveEventsList(@Path("version") String str, @QueryMap HashMap<String, Integer> hashMap);

    @GET("/nudge/api/{version}/users/@me/moves")
    void getMoveEventsList(@Path("version") String str, @QueryMap HashMap<String, Integer> hashMap, Callback<Move> callback);

    @GET("/nudge/api/{version}/moves/{xid}/image")
    void getMoveGraph(@Path("version") String str, @Path("xid") String str2, Callback<Object> callback);

    @GET("/nudge/api/{version}/moves/{xid}/ticks")
    void getMoveTicks(@Path("version") String str, @Path("xid") String str2, Callback<Object> callback);

    @POST("/nudge/api/{version}/users/@me/refreshToken")
    @Multipart
    void getRefreshToken(@Path("version") String str, @Part("secret") String str2, Callback<Object> callback);

    @GET("/nudge/api/{version}/sleeps/{xid}")
    void getSleepEvent(@Path("version") String str, @Path("xid") String str2, Callback<Object> callback);

    @GET("/nudge/api/{version}/users/@me/sleeps")
    void getSleepEventsList(@Path("version") String str, @QueryMap HashMap<String, Integer> hashMap, Callback<Object> callback);

    @GET("/nudge/api/{version}/sleeps/{xid}/image")
    void getSleepGraph(@Path("version") String str, @Path("xid") String str2, Callback<Object> callback);

    @GET("/nudge/api/{version}/sleeps/{xid}/ticks")
    void getSleepPhases(@Path("version") String str, @Path("xid") String str2, Callback<Object> callback);

    @GET("/nudge/api/{version}/users/@me/timezone")
    void getTimeZone(@Path("version") String str, @QueryMap HashMap<String, Integer> hashMap, Callback<Object> callback);

    @GET("/nudge/api/{version}/users/@me/trends")
    void getTrends(@Path("version") String str, @QueryMap HashMap<String, Object> hashMap, Callback<Object> callback);

    @GET("/nudge/api/{version}/users/@me")
    void getUser(@Path("version") String str, Callback<Object> callback);

    @GET("/nudge/api/{version}/users/@me/settings")
    void getUserSettings(@Path("version") String str, Callback<Object> callback);

    @GET("/nudge/api/{version}/users/@me/friends")
    void getUsersFriends(@Path("version") String str, Callback<Object> callback);

    @GET("/nudge/api/{version}/users/@me/goals")
    void getUsersGoals(@Path("version") String str, Callback<Object> callback);

    @GET("/nudge/api/{version}/workouts/{xid}")
    void getWorkoutEvent(@Path("version") String str, @Path("xid") String str2, Callback<Object> callback);

    @GET("/nudge/api/{version}/users/@me/workouts")
    void getWorkoutEventList(@Path("version") String str, @QueryMap HashMap<String, Integer> hashMap, Callback<Object> callback);

    @GET("/nudge/api/{version}/workouts/{xid}/image")
    void getWorkoutGraph(@Path("version") String str, @Path("xid") String str2, Callback<Object> callback);

    @GET("/nudge/api/{version}/workouts/{xid}/ticks")
    void getWorkoutTicks(@Path("version") String str, @Path("xid") String str2, Callback<Object> callback);

    @POST("/nudge/api/{version}/generic_events/{xid}/partialUpdate")
    @Multipart
    void updateCustomEvent(@Path("version") String str, @PartMap HashMap<String, Object> hashMap, Callback<Object> callback);

    @POST("/nudge/api/{version}/meals/{xid}/partialUpdate")
    @Multipart
    void updateMealEvent(@Path("version") String str, @Path("xid") String str2, @PartMap HashMap<String, Object> hashMap, Callback<Object> callback);

    @POST("/nudge/api/{version}/workouts/{xid}/partialUpdate")
    @Multipart
    void updateWorkoutEvent(@Path("version") String str, @PartMap HashMap<String, Object> hashMap, Callback<Object> callback);
}
