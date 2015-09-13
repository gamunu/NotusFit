package com.notus.fit.models.misfit;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/3/2015 4:59 PM
 */
@Parcel
public class MisfitSummary {
    @SerializedName("summary")
    List<MisfitDay> summary;

    public List<MisfitDay> getSummary() {
        return this.summary;
    }

    public void setSummary(List<MisfitDay> summary) {
        this.summary = summary;
    }
}
