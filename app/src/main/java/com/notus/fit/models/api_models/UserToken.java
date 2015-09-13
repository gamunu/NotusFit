package com.notus.fit.models.api_models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/3/2015 9:59 AM
 */

@Parcel
public class UserToken {
    @SerializedName("token")
    String token;

    public String getToken() {
        return this.token;
    }
}
