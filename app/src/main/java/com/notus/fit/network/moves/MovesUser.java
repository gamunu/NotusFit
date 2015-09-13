package com.notus.fit.network.moves;

import com.google.gson.annotations.SerializedName;

public class MovesUser {
    @SerializedName("caloriesAvailable")
    boolean caloriesAvailable;
    @SerializedName("platform")
    String platform;
    @SerializedName("profile")
    MovesUserProfile profile;
    @SerializedName("userId")
    String userId;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public MovesUserProfile getProfile() {
        return this.profile;
    }

    public void setProfile(MovesUserProfile profile) {
        this.profile = profile;
    }

    public boolean isCaloriesAvailable() {
        return this.caloriesAvailable;
    }

    public void setCaloriesAvailable(boolean caloriesAvailable) {
        this.caloriesAvailable = caloriesAvailable;
    }

    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String toString() {
        return "MovesUser{userId='" + this.userId + '\'' + ", profile=" + this.profile + ", caloriesAvailable=" + this.caloriesAvailable + ", platform='" + this.platform + '\'' + '}';
    }
}