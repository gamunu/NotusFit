package com.jawbone.upplatformsdk.endpointModels.body;

import com.google.gson.annotations.SerializedName;
import com.jawbone.upplatformsdk.endpointModels.DefaultItem;

public class BodyItem extends DefaultItem {
    @SerializedName("bmi")
    protected Float bmi;
    @SerializedName("body_fat")
    protected Float bodyFat;
    @SerializedName("details")
    protected BodyDetails details;
    @SerializedName("lean_mass")
    protected Float leanMass;
    @SerializedName("note")
    protected String note;
    @SerializedName("place_acc")
    protected Integer placeAcc;
    @SerializedName("place_lat")
    protected Float placeLat;
    @SerializedName("place_lon")
    protected Float placeLon;
    @SerializedName("place_name")
    protected String placeName;
    @SerializedName("weight")
    protected Float weight;

    public Float getPlaceLat() {
        return this.placeLat;
    }

    public void setPlaceLat(Float placeLat) {
        this.placeLat = placeLat;
    }

    public Float getPlaceLon() {
        return this.placeLon;
    }

    public void setPlaceLon(Float placeLon) {
        this.placeLon = placeLon;
    }

    public Integer getPlaceAcc() {
        return this.placeAcc;
    }

    public void setPlaceAcc(Integer placeAcc) {
        this.placeAcc = placeAcc;
    }

    public String getPlaceName() {
        return this.placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Float getLeanMass() {
        return this.leanMass;
    }

    public void setLeanMass(Float leanMass) {
        this.leanMass = leanMass;
    }

    public Float getWeight() {
        return this.weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getBodyFat() {
        return this.bodyFat;
    }

    public void setBodyFat(Float bodyFat) {
        this.bodyFat = bodyFat;
    }

    public Float getBmi() {
        return this.bmi;
    }

    public void setBmi(Float bmi) {
        this.bmi = bmi;
    }

    public BodyDetails getDetails() {
        return this.details;
    }

    public void setDetails(BodyDetails details) {
        this.details = details;
    }
}
