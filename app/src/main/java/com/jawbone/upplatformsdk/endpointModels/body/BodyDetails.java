package com.jawbone.upplatformsdk.endpointModels.body;

import com.google.gson.annotations.SerializedName;

public class BodyDetails {
    @SerializedName("tz")
    String timeZone;

    public String getTimeZone() {
        return this.timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String toString() {
        return "BodyDetails {\n\ttimeZone='" + this.timeZone + '\'' + "\n}\n";
    }
}
