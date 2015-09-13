package com.notus.fit.models.misfit;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/3/2015 4:59 PM
 */
@Parcel
public class MisfitToken {
    @SerializedName("access_token")
    String token;
    @SerializedName("token_type")
    String tokenType;

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return this.tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
