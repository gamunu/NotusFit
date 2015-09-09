package com.notus.fit.network.moves;


public class MovesUserProfile {

    String firstDate;

    public MovesUserProfile() {
    }

    public String getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(String s) {
        firstDate = s;
    }

    public String toString() {
        return (new StringBuilder()).append("MovesUserProfile{firstDate='").append(firstDate).append('\'').append('}').toString();
    }
}
