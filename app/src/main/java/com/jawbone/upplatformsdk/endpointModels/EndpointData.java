package com.jawbone.upplatformsdk.endpointModels;

import com.google.gson.annotations.SerializedName;
import com.jawbone.upplatformsdk.datamodel.Links;

public class EndpointData {
    @SerializedName("links")
    protected Links links;
    @SerializedName("size")
    protected Integer size;

    public Links getLinks() {
        return this.links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public Integer getSize() {
        return this.size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
