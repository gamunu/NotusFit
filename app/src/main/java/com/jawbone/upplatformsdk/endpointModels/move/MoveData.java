package com.jawbone.upplatformsdk.endpointModels.move;

import com.google.gson.annotations.SerializedName;
import com.jawbone.upplatformsdk.endpointModels.EndpointData;
import com.jawbone.upplatformsdk.utils.UpPlatformSdkUtils;

import java.util.List;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 2/13/15.
 */
public class MoveData extends EndpointData {

    @SerializedName("items")
    List<MoveItem> moveItems;

    public MoveData() {
    }

    public List<MoveItem> getMoveItems() {
        return moveItems;
    }

    public void setMoveItems(List<MoveItem> moveItems) {
        this.moveItems = moveItems;
    }

    @Override
    public String toString() {
        return UpPlatformSdkUtils.toJson(this);
    }
}
