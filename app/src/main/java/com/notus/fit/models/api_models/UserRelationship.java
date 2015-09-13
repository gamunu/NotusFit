package com.notus.fit.models.api_models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.Transient;

/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/3/2015 9:59 AM
 */

@Parcel
public class UserRelationship {
    @Transient
    public static final String DECLINED = "declined";
    @Transient
    public static final String FRIEND = "friend";
    @Transient
    public static final String REQUEST = "request";
    @SerializedName("friend")
    User friend;
    @SerializedName("id")
    long id;
    @SerializedName("status")
    String status;
    @SerializedName("user")
    User user;
    @SerializedName("username")
    String username;

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriend() {
        return this.friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
