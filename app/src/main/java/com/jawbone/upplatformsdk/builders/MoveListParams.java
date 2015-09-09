package com.jawbone.upplatformsdk.builders;

import java.util.HashMap;

public class MoveListParams extends Params {
    private Builder mBuilder;

    public HashMap<String, Integer> getMoveListParams() {
        return this.mBuilder.build();
    }
}
