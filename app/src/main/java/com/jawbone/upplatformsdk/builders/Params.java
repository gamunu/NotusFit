package com.jawbone.upplatformsdk.builders;

import java.util.HashMap;

public class Params {

    public static class Builder {
        Integer date;
        Integer endTime;
        Integer pageToken;
        Integer startTime;
        Integer updatedAfter;

        public Builder setStartTime(Integer startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder setEndTime(Integer endTime) {
            this.endTime = endTime;
            return this;
        }

        public HashMap<String, Integer> build() {
            HashMap<String, Integer> queryHashMap = new HashMap();
            if (this.date != null) {
                queryHashMap.put("date", this.date);
            }
            if (this.pageToken != null) {
                queryHashMap.put("page_token", this.pageToken);
            }
            if (this.startTime != null) {
                queryHashMap.put("start_time", this.startTime);
            }
            if (this.endTime != null) {
                queryHashMap.put("end_time", this.endTime);
            }
            if (this.updatedAfter != null) {
                queryHashMap.put("updated_after", this.updatedAfter);
            }
            return queryHashMap;
        }
    }
}
