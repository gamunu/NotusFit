package com.notus.fit.models.fitbit;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/3/2015 10:00 AM
 */
@Parcel
public class FitbitUser {
    @SerializedName("avatar")
    String avatar;
    @SerializedName("avatar150")
    String avatar150;
    @SerializedName("dateOfBirth")
    String dateOfBirth;
    @SerializedName("encodedId")
    String encodedId;
    @SerializedName("fullName")
    String fullName;
    @SerializedName("gender")
    String gender;
    @SerializedName("height")
    String height;
    @SerializedName("heightUnit")
    String heightUnit;
    @SerializedName("memberSince")
    String memberSince;
    @SerializedName("displayName")
    String name;
    @SerializedName("weight")
    String weight;
    @SerializedName("weightUnit")
    String weightUnit;

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar150() {
        return this.avatar150;
    }

    public void setAvatar150(String avatar150) {
        this.avatar150 = avatar150;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMemberSince() {
        return this.memberSince;
    }

    public void setMemberSince(String memberSince) {
        this.memberSince = memberSince;
    }

    public String getHeight() {
        return this.height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return this.weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeightUnit() {
        return this.heightUnit;
    }

    public void setHeightUnit(String heightUnit) {
        this.heightUnit = heightUnit;
    }

    public String getWeightUnit() {
        return this.weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public String getEncodedId() {
        return this.encodedId;
    }

    public void setEncodedId(String encodedId) {
        this.encodedId = encodedId;
    }

    public String toString() {
        return "FitbitUser{avatar='" + this.avatar + '\'' + ", avatar150='" + this.avatar150 + '\'' + ", dateOfBirth='" + this.dateOfBirth + '\'' + ", name='" + this.name + '\'' + ", fullName='" + this.fullName + '\'' + ", gender='" + this.gender + '\'' + ", memberSince='" + this.memberSince + '\'' + ", height='" + this.height + '\'' + ", weight='" + this.weight + '\'' + ", heightUnit='" + this.heightUnit + '\'' + ", weightUnit='" + this.weightUnit + '\'' + ", encodedId='" + this.encodedId + '\'' + '}';
    }
}
