package com.jawbone.upplatformsdk.utils;

public class UpPlatformSdkConstants {

    public enum RestApiRequestType {
        GET_MEALS_EVENTS_LIST("Get Meals Events List"),
        GET_MEALS_EVENT("Get Meals Event"),
        DELETE_MEAL("Delete Meal"),
        CREATE_MEAL("Create Meal"),
        UPDATE_MEAL("Update Meal"),
        GET_MOVES_EVENTS_LIST("Get Moves Events List"),
        GET_MOVES_EVENT("Get Moves Event"),
        GET_MOVES_GRAPH("Get Moves Graph"),
        GET_MOVES_TICKS("Get Moves Ticks"),
        GET_CUSTOM_EVENTS_LIST("Get Custom Events List"),
        CREATE_CUSTOM_EVENT("Create Custom Event"),
        UPDATE_CUSTOM_EVENT("Update Custom Event"),
        DELETE_CUSTOM_EVENT("Delete Custom Event"),
        GET_WORKOUTS_EVENTS_LIST("Get Workouts Events List"),
        GET_WORKOUTS_EVENT("Get Workouts Event"),
        GET_WORKOUTS_GRAPH("Get Workouts Graph"),
        GET_WORKOUTS_TICKS("Get Workouts Ticks"),
        CREATE_WORKOUT_EVENT("Create Workout Event"),
        UPDATE_WORKOUT_EVENT("Update Workout Event"),
        DELETE_WORKOUT_EVENT("Delete Workout Event"),
        GET_SLEEP_EVENTS_LIST("Get Sleep Events List"),
        GET_SLEEP_EVENT("Get Sleep Event"),
        GET_SLEEP_GRAPH("Get Sleep Graph"),
        GET_SLEEP_TICKS("Get Sleep Ticks"),
        CREATE_SLEEP_EVENT("Create Sleep Event"),
        DELETE_SLEEP_EVENT("Delete Sleep Event"),
        GET_BODY_EVENTS_LIST("Get Body Events List"),
        GET_BODY_EVENT("Get Body Event"),
        CREATE_BODY_EVENT("Create Body Event"),
        DELETE_BODY_EVENT("Delete Body Event"),
        GET_BAND_EVENTS("Get Band Events"),
        GET_GOALS("Get Goals"),
        CREATE_OR_UPDATE_GOALS("Create or Update Goals"),
        GET_MOOD_EVENTS_LIST("Get Moods Events List"),
        GET_MOOD_EVENT("Get Mood Event"),
        CREATE_MOOD_EVENT("Create Mood Event"),
        DELETE_MOOD_EVENT("Delete Mood Event"),
        GET_REFRESH_TOKEN("Get Refresh Token"),
        GET_SETTINGS("Get Settings"),
        GET_TIME_ZONE("Get Time Zone"),
        GET_TRENDS("Get Trends"),
        GET_USER("Get User"),
        GET_USERS_FRIENDS("Get Users Friends");

        public static final int size;

        static {
            size = values().length;
        }

        private String displayTitle;

        private RestApiRequestType(String s) {
            this.displayTitle = s;
        }

        public String toString() {
            return this.displayTitle;
        }
    }

    public enum UpPlatformAuthScope {
        BASIC_READ,
        EXTENDED_READ,
        LOCATION_READ,
        FRIENDS_READ,
        MOOD_READ,
        MOOD_WRITE,
        MOVE_READ,
        MOVE_WRITE,
        SLEEP_READ,
        SLEEP_WRITE,
        MEAL_READ,
        MEAL_WRITE,
        WEIGHT_READ,
        WEIGHT_WRITE,
        CARDIAC_READ,
        CARDIAC_WRITE,
        GENERIC_EVENT_READ,
        GENERIC_EVENT_WRITE,
        ALL
    }
}
