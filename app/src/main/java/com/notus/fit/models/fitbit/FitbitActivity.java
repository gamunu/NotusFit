package com.notus.fit.models.fitbit;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/3/2015 9:59 AM
 */
@Parcel
public class FitbitActivity {
    @SerializedName("distances")
    List<FitbitDistance> distances;
    @SerializedName("goals")
    FitbitGoals goals;
    @SerializedName("summary")
    FitbitSummary summary;

    public FitbitGoals getGoals() {
        return this.goals;
    }

    public void setGoals(FitbitGoals goals) {
        this.goals = goals;
    }

    public FitbitSummary getSummary() {
        return this.summary;
    }

    public void setSummary(FitbitSummary summary) {
        this.summary = summary;
    }

    public List<FitbitDistance> getDistances() {
        return this.distances;
    }

    public void setDistances(List<FitbitDistance> distances) {
        this.distances = distances;
    }

    public String toString() {
        return "FitbitActivity{goals=" + this.goals + ", summary=" + this.summary + ", distances=" + this.distances + '}';
    }
}
