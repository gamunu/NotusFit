package com.jawbone.upplatformsdk.endpointModels.body;

import com.google.gson.annotations.SerializedName;
import com.jawbone.upplatformsdk.endpointModels.Endpoint;

public class Body extends Endpoint {
    @SerializedName("data")
    BodyData data;

    public BodyData getData() {
        return this.data;
    }

    public void setData(BodyData data) {
        this.data = data;
    }
}
