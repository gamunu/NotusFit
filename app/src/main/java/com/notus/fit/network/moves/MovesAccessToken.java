package com.notus.fit.network.moves;


import com.google.gson.annotations.SerializedName;

public class MovesAccessToken {
    @SerializedName("access_token")
    String accessToken;
    @SerializedName("expires_in")
    long expiresIn;
    @SerializedName("refresh_token")
    String refreshToken;
    @SerializedName("token_type")
    String tokenType;
    @SerializedName("user_id")
    long userId;

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return this.tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public long getExpiresIn() {
        return this.expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
