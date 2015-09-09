package com.notus.fit.fragments;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class PostUserChallenge {
    @SerializedName("challenge")
    int challenge;
    @SerializedName("status")
    String status;
    @SerializedName("user_c")
    int user;

    public int getUser() {
        return this.user;
    }

    public PostUserChallenge setUser(int user) {
        this.user = user;
        return this;
    }

    public int getChallenge() {
        return this.challenge;
    }

    public PostUserChallenge setChallenge(int challenge) {
        this.challenge = challenge;
        return this;
    }

    public String getStatus() {
        return this.status;
    }

    public PostUserChallenge setStatus(String status) {
        this.status = status;
        return this;
    }
}