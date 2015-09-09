package com.notus.fit.models.fitbit;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by VBALAUD on 9/3/2015.
 */
@Parcel
public class FitbitActivity {
    List<FitbitDistance> distances;
    FitbitGoals goals;
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
