package com.notus.fit.network.moves;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovesActivity {
    @SerializedName("date")
    String date;
    @SerializedName("summary")
    List<MovesSummary> summary;

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<MovesSummary> getSummary() {
        return this.summary;
    }

    public void setSummary(List<MovesSummary> summary) {
        this.summary = summary;
    }

    public String toString() {
        return "MovesActivity{date='" + this.date + '\'' + ", summary=" + this.summary + '}';
    }
}
