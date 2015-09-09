package com.notus.fit.network.moves;


public class MovesSummary {

    String activity;
    int calories;
    int distance;
    int duration;
    String group;
    int steps;

    public MovesSummary() {
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String s) {
        activity = s;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int i) {
        calories = i;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int i) {
        distance = i;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int i) {
        duration = i;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String s) {
        group = s;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int i) {
        steps = i;
    }

    public String toString() {
        return (new StringBuilder()).append("MovesSummary{activity='").append(activity).append('\'').append(", group='").append(group).append('\'').append(", duration=").append(duration).append(", distance=").append(distance).append(", steps=").append(steps).append(", calories=").append(calories).append('}').toString();
    }
}
