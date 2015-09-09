package com.jawbone.upplatformsdk.endpointModels.body;

import com.google.gson.annotations.SerializedName;
import com.jawbone.upplatformsdk.endpointModels.EndpointData;

import java.util.List;

public class BodyData extends EndpointData {
    @SerializedName("items")
    List<BodyItem> bodyItems;

    public List<BodyItem> getBodyItems() {
        return this.bodyItems;
    }

    public void setBodyItems(List<BodyItem> bodyItems) {
        this.bodyItems = bodyItems;
    }
}
