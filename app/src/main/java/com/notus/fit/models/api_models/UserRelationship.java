package com.notus.fit.models.api_models;

import org.parceler.Parcel;

/**
 * Created by VBALAUD on 9/3/2015.
 */

@Parcel
public class UserRelationship {
    public static final String DECLINED = "declined";
    public static final String FRIEND = "friend";
    public static final String REQUEST = "request";
    User friend;
    long id;
    String status;
    User user;
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
