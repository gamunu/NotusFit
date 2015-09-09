package com.notus.fit.network.moves;

import java.util.List;

public class MovesActivity {

    String date;
    List<MovesSummary> summary;

    public MovesActivity() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String s) {
        date = s;
    }

    public List<MovesSummary> getSummary() {
        return summary;
    }

    public void setSummary(List<MovesSummary> list) {
        summary = list;
    }

    public String toString() {
        return (new StringBuilder()).append("MovesActivity{date='").append(date).append('\'').append(", summary=").append(summary).append('}').toString();
    }
}
