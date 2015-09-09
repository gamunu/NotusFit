package com.notus.fit.network.moves;


public class MovesAccessToken {

    String accessToken;
    long expiresIn;
    String refreshToken;
    String tokenType;
    long userId;

    public MovesAccessToken() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String s) {
        accessToken = s;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long l) {
        expiresIn = l;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String s) {
        refreshToken = s;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String s) {
        tokenType = s;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long l) {
        userId = l;
    }
}
