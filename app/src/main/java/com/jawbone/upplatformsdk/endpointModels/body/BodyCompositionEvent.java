package com.jawbone.upplatformsdk.endpointModels.body;

import com.google.gson.annotations.SerializedName;
import com.jawbone.upplatformsdk.endpointModels.Endpoint;

public class BodyCompositionEvent extends Endpoint {
    @SerializedName("data")
    BodyItem data;

    public BodyItem getData() {
        return this.data;
    }

    public void setData(BodyItem data) {
        this.data = data;
    }
}
