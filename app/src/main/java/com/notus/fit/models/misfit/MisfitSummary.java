package com.notus.fit.models.misfit;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by VBALAUD on 9/3/2015.
 */
@Parcel
public class MisfitSummary {
    List<MisfitDay> summary;

    public List<MisfitDay> getSummary() {
        return this.summary;
    }

    public void setSummary(List<MisfitDay> summary) {
        this.summary = summary;
    }
}
