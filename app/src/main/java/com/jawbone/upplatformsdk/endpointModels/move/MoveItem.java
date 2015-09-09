package com.jawbone.upplatformsdk.endpointModels.move;

import com.google.gson.annotations.SerializedName;
import com.jawbone.upplatformsdk.endpointModels.DefaultItem;

public class MoveItem extends DefaultItem {
    @SerializedName("details")
    MoveDetails details;
    @SerializedName("snapshot_image")
    String snapshotImage;

    public String getSnapshotImage() {
        return this.snapshotImage;
    }

    public void setSnapshotImage(String snapshotImage) {
        this.snapshotImage = snapshotImage;
    }

    public MoveDetails getDetails() {
        return this.details;
    }

    public void setDetails(MoveDetails details) {
        this.details = details;
    }
}
