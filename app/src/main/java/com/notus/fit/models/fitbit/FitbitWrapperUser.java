package com.notus.fit.models.fitbit;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/3/2015 10:00 AM
 */
@Parcel
public class FitbitWrapperUser {
    @SerializedName("user")
    FitbitUser user;

    public FitbitWrapperUser() {
    }

    public FitbitWrapperUser(FitbitUser user) {
        this.user = user;
    }

    public FitbitUser getUser() {
        return this.user;
    }

    public void setUser(FitbitUser user) {
        this.user = user;
    }
}
