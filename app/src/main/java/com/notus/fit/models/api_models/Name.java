package com.notus.fit.models.api_models;


import com.notus.fit.BuildConfig;

/**
 * Created by VBALAUD on 9/3/2015.
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

