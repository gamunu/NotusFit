package com.jawbone.upplatformsdk.endpointModels;

import com.google.gson.annotations.SerializedName;
import com.jawbone.upplatformsdk.datamodel.Meta;

public class Endpoint {
    @SerializedName("meta")
    protected Meta meta;

    public Meta getMeta() {
        return this.meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
