package com.notus.fit.models.generic;

/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/3/2015 5:00 PM
 */
public class UnitValue {
    private String unit;
    private float value;

    public UnitValue() {
    }

    public UnitValue(float f, String s) {
        value = f;
        unit = s;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String s) {
        unit = s;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float f) {
        value = f;
    }
}
