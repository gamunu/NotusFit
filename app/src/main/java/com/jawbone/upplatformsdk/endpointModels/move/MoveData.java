package com.jawbone.upplatformsdk.endpointModels.move;

import com.google.gson.annotations.SerializedName;
import com.jawbone.upplatformsdk.endpointModels.EndpointData;

import java.util.List;

public class MoveData extends EndpointData {
    @SerializedName("items")
    List<MoveItem> moveItems;

    public List<MoveItem> getMoveItems() {
        return this.moveItems;
    }

    public void setMoveItems(List<MoveItem> moveItems) {
        this.moveItems = moveItems;
    }
}
