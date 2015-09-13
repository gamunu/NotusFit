package com.notus.fit.models.api_models;


import com.notus.fit.BuildConfig;

/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/3/2015 9:59 AM
 */

public class Name {
    private String firstName;
    private String lastName;

    public Name() {
        this.firstName = BuildConfig.FLAVOR;
        this.lastName = BuildConfig.FLAVOR;
    }

    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

