package com.jawbone.upplatformsdk.endpointModels.body;

import com.google.gson.annotations.SerializedName;
import com.jawbone.upplatformsdk.endpointModels.DefaultItem;

public class PostBodyComposition extends DefaultItem {
    @SerializedName("share")
    Boolean share;

    public void setShare(Boolean share) {
        this.share = share;
    }
}
