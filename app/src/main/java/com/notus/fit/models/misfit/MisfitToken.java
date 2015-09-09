package com.notus.fit.models.misfit;

import org.parceler.Parcel;

/**
 * Created by VBALAUD on 9/3/2015.
 */
@Parcel
public class MisfitToken {
    String token;
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
