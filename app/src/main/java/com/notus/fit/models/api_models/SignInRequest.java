package com.notus.fit.models.api_models;

import org.parceler.Parcel;

/**
 * Created by VBALAUD on 9/3/2015.
 */

@Parcel
public class SignInRequest {
    String password;
    String username;

    public SignInRequest() {
    }

    public SignInRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
