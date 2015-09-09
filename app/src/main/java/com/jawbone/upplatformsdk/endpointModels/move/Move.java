package com.jawbone.upplatformsdk.endpointModels.move;

import com.google.gson.annotations.SerializedName;
import com.jawbone.upplatformsdk.endpointModels.Endpoint;

public class Move extends Endpoint {
    @SerializedName("data")
    protected MoveData moveData;

    public MoveData getMoveData() {
        return this.moveData;
    }

    public void setMoveData(MoveData moveData) {
        this.moveData = moveData;
    }
}
