package com.jawbone.upplatformsdk.endpointModels;

import com.google.gson.annotations.SerializedName;

public class DefaultItem {
    @SerializedName("date")
    protected Integer date;
    @SerializedName("time_completed")
    protected Long timeCompleted;
    @SerializedName("time_created")
    protected Long timeCreated;
    @SerializedName("title")
    protected String title;
    @SerializedName("type")
    protected String type;
    @SerializedName("xid")
    protected String xid;

    public String getXid() {
        return this.xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Long timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Long getTimeCompleted() {
        return this.timeCompleted;
    }

    public void setTimeCompleted(Long timeCompleted) {
        this.timeCompleted = timeCompleted;
    }

    public Integer getDate() {
        return this.date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }
}
