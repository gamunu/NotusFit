package com.notus.fit.models.api_models;

import com.google.gson.annotations.SerializedName;
import com.notus.fit.BuildConfig;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.parceler.Parcel;
import org.parceler.Transient;

/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/3/2015 9:59 AM
 */

@Parcel
public class User {
    @Transient
    public static final String AVATAR_URL = "avatar_url";
    @Transient
    public static final String BIRTHDAY = "birthday";
    @Transient
    public static final String CLASS = "Users";
    @Transient
    public static final String COUNTRY = "country";
    @Transient
    public static final String EMAIL = "email";
    @Transient
    public static final String FIRST_NAME = "firstName";
    @Transient
    public static final String FITBIT_TOKEN = "fitbit_token";
    @Transient
    public static final String GENDER = "gender";
    @Transient
    public static final String HAS_FITBIT = "has_fitbit";
    @Transient
    public static final String HAS_GOOGLEFIT = "has_googlefit";
    @Transient
    public static final String HAS_JAWBONE = "has_jawbone";
    @Transient
    public static final String HAS_MISFIT = "has_misfit";
    @Transient
    public static final String HAS_MOVES = "has_moves";
    @Transient
    public static final String HEIGHT = "height";
    @Transient
    public static final String JAWBONE_TOKEN = "jawbone_token";
    @Transient
    public static final String LAST_NAME = "lastName";
    @Transient
    public static final String MISFIT_TOKEN = "misfit_token";
    @Transient
    public static final String MOVES_REFRESH_TOKEN = "moves_refresh_token";
    @Transient
    public static final String MOVES_TOKEN = "moves_token";
    @Transient
    public static final String OBJECT_ID = "objectId";
    @Transient
    public static final String STEPS_AVERAGE = "steps_average";
    @Transient
    public static final String TIME_ZONE = "time_zone";
    @Transient
    public static final String UNITS = "units";
    @Transient
    public static final String USERNAME = "username";
    @Transient
    public static final String WEIGHT = "weight";

    String avatarUrl;
    @SerializedName("birthday")
    String birthday;
    @SerializedName("country")
    String country;
    @SerializedName("email")
    String email;
    @SerializedName("first_name")
    String firstName;
    String fitbitToken;
    @SerializedName("gender")
    String gender;
    boolean hasFitbit;
    boolean hasGoogleFit;
    boolean hasJawbone;
    boolean hasMisfit;
    boolean hasMoves;
    @SerializedName("height")
    String height;
    String jawboneToken;
    @SerializedName("last_name")
    String lastName;
    String misfitToken;
    String movesToken;
    @SerializedName("objectId")
    String objectId;
    String refreshMovesToken;
    int stepsAverage;
    @SerializedName("time_zone")
    String timeZone;
    @SerializedName("units")
    String units;
    @SerializedName("username")
    String username;
    @SerializedName("weight")
    String weight;

    public static User build(ParseObject object) {
        User user = new User();
        user.setObjectId(object.getString(OBJECT_ID) != null ? object.getString(OBJECT_ID) : BuildConfig.FLAVOR);
        user.setUsername(object.getString(USERNAME) != null ? object.getString(USERNAME) : BuildConfig.FLAVOR);
        user.setEmail(object.getString(EMAIL) != null ? object.getString(EMAIL) : BuildConfig.FLAVOR);
        user.setFirstName(object.getString(FIRST_NAME) != null ? object.getString(FIRST_NAME) : BuildConfig.FLAVOR);
        user.setLastName(object.getString(LAST_NAME) != null ? object.getString(LAST_NAME) : BuildConfig.FLAVOR);
        user.setAvatarUrl(object.getString(AVATAR_URL) != null ? object.getString(AVATAR_URL) : BuildConfig.FLAVOR);
        user.setHasFitbit(object.getBoolean(HAS_FITBIT));
        user.setHasJawbone(object.getBoolean(HAS_JAWBONE));
        user.setHasGoogleFit(object.getBoolean(HAS_GOOGLEFIT));
        user.setHasMisfit(object.getBoolean(HAS_MISFIT));
        user.setHasMoves(object.getBoolean(HAS_MOVES));
        user.setBirthday(object.getString(BIRTHDAY) != null ? object.getString(BIRTHDAY) : BuildConfig.FLAVOR);
        user.setCountry(object.getString(COUNTRY) != null ? object.getString(COUNTRY) : BuildConfig.FLAVOR);
        user.setHeight(object.getString(HEIGHT) != null ? object.getString(HEIGHT) : BuildConfig.FLAVOR);
        user.setWeight(object.getString(WEIGHT) != null ? object.getString(WEIGHT) : BuildConfig.FLAVOR);
        user.setUnits(object.getString(UNITS) != null ? object.getString(UNITS) : BuildConfig.FLAVOR);
        user.setGender(object.getString(GENDER) != null ? object.getString(GENDER) : BuildConfig.FLAVOR);
        user.setStepsAverage(object.getInt(STEPS_AVERAGE));
        user.setJawboneToken(object.getString(JAWBONE_TOKEN) != null ? object.getString(JAWBONE_TOKEN) : BuildConfig.FLAVOR);
        user.setFitbitToken(object.getString(FITBIT_TOKEN) != null ? object.getString(FITBIT_TOKEN) : BuildConfig.FLAVOR);
        user.setMisfitToken(object.getString(MISFIT_TOKEN) != null ? object.getString(MISFIT_TOKEN) : BuildConfig.FLAVOR);
        user.setMovesToken(object.getString(MOVES_TOKEN) != null ? object.getString(MOVES_TOKEN) : BuildConfig.FLAVOR);
        user.setRefreshMovesToken(object.getString(MOVES_REFRESH_TOKEN) != null ? object.getString(MOVES_REFRESH_TOKEN) : BuildConfig.FLAVOR);
        return user;
    }

    public static ParseObject build(User user) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(CLASS);
        query.fromLocalDatastore();
        try {
            ParseObject object = query.get(user.getObjectId());
            object.put(USERNAME, user.getUsername() != null ? user.getUsername() : BuildConfig.FLAVOR);
            object.put(EMAIL, user.getEmail() != null ? user.getEmail() : BuildConfig.FLAVOR);
            object.put(FIRST_NAME, user.getFirstName() != null ? user.getFirstName() : BuildConfig.FLAVOR);
            object.put(LAST_NAME, user.getLastName() != null ? user.getLastName() : BuildConfig.FLAVOR);
            object.put(AVATAR_URL, user.getAvatarUrl() != null ? user.getAvatarUrl() : BuildConfig.FLAVOR);
            object.put(HAS_FITBIT, user.hasFitbit());
            object.put(HAS_JAWBONE, user.hasJawbone());
            object.put(HAS_GOOGLEFIT, user.hasGoogleFit());
            object.put(HAS_MOVES, user.hasMoves());
            object.put(HAS_MISFIT, user.hasMisfit());
            object.put(BIRTHDAY, user.getBirthday() != null ? user.getBirthday() : BuildConfig.FLAVOR);
            object.put(COUNTRY, user.getCountry() != null ? user.getCountry() : BuildConfig.FLAVOR);
            object.put(HEIGHT, user.getHeight() != null ? user.getHeight() : BuildConfig.FLAVOR);
            object.put(WEIGHT, user.getWeight() != null ? user.getWeight() : BuildConfig.FLAVOR);
            object.put(UNITS, user.getUnits() != null ? user.getUnits() : BuildConfig.FLAVOR);
            object.put(GENDER, user.getGender() != null ? user.getGender() : BuildConfig.FLAVOR);
            object.put(STEPS_AVERAGE, user.getStepsAverage());
            object.put(JAWBONE_TOKEN, user.getJawboneToken() != null ? user.getJawboneToken() : BuildConfig.FLAVOR);
            object.put(FITBIT_TOKEN, user.getFitbitToken() != null ? user.getFitbitToken() : BuildConfig.FLAVOR);
            object.put(MISFIT_TOKEN, user.getMisfitToken() != null ? user.getMisfitToken() : BuildConfig.FLAVOR);
            object.put(MOVES_TOKEN, user.getMovesToken() != null ? user.getMovesToken() : BuildConfig.FLAVOR);
            object.put(MOVES_REFRESH_TOKEN, user.getMovesToken() != null ? user.getRefreshMovesToken() : BuildConfig.FLAVOR);
            return object;
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String getObjectId() {
        return this.objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFitbitToken() {
        return this.fitbitToken;
    }

    public void setFitbitToken(String fitbitToken) {
        this.fitbitToken = fitbitToken;
    }

    public String getMisfitToken() {
        return this.misfitToken;
    }

    public void setMisfitToken(String misfitToken) {
        this.misfitToken = misfitToken;
    }

    public String getJawboneToken() {
        return this.jawboneToken;
    }

    public void setJawboneToken(String jawboneToken) {
        this.jawboneToken = jawboneToken;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public boolean hasMisfit() {
        return this.hasMisfit;
    }

    public void setHasMisfit(boolean hasMisfit) {
        this.hasMisfit = hasMisfit;
    }

    public boolean hasFitbit() {
        return this.hasFitbit;
    }

    public void setHasFitbit(boolean hasFitbit) {
        this.hasFitbit = hasFitbit;
    }

    public boolean hasGoogleFit() {
        return this.hasGoogleFit;
    }

    public void setHasGoogleFit(boolean hasGoogleFit) {
        this.hasGoogleFit = hasGoogleFit;
    }

    public boolean hasJawbone() {
        return this.hasJawbone;
    }

    public void setHasJawbone(boolean hasJawbone) {
        this.hasJawbone = hasJawbone;
    }

    public int getStepsAverage() {
        return this.stepsAverage;
    }

    public void setStepsAverage(int stepsAverage) {
        this.stepsAverage = stepsAverage;
    }

    public String getWeight() {
        return this.weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return this.height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUnits() {
        return this.units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getTimeZone() {
        return this.timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMovesToken() {
        return this.movesToken;
    }

    public User setMovesToken(String movesToken) {
        this.movesToken = movesToken;
        return this;
    }

    public String getRefreshMovesToken() {
        return this.refreshMovesToken;
    }

    public User setRefreshMovesToken(String refreshMovesToken) {
        this.refreshMovesToken = refreshMovesToken;
        return this;
    }

    public boolean hasMoves() {
        return this.hasMoves;
    }

    public User setHasMoves(boolean hasMoves) {
        this.hasMoves = hasMoves;
        return this;
    }

    public String toString() {
        return "User{objectId='" + this.objectId + '\'' + ", username='" + this.username + '\'' + ", firstName='" + this.firstName + '\'' + ", lastName='" + this.lastName + '\'' + ", email='" + this.email + '\'' + ", fitbitToken='" + this.fitbitToken + '\'' + ", misfitToken='" + this.misfitToken + '\'' + ", jawboneToken='" + this.jawboneToken + '\'' + ", avatarUrl='" + this.avatarUrl + '\'' + ", weight='" + this.weight + '\'' + ", height='" + this.height + '\'' + ", country='" + this.country + '\'' + ", units='" + this.units + '\'' + ", timeZone='" + this.timeZone + '\'' + ", birthday='" + this.birthday + '\'' + ", gender='" + this.gender + '\'' + ", mHasMisfit=" + this.hasMisfit + ", mHasFitbit=" + this.hasFitbit + ", hasGoogleFit=" + this.hasGoogleFit + ", mHasJawbone=" + this.hasJawbone + ", stepsAverage=" + this.stepsAverage + '}';
    }
}
