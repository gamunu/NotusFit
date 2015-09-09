package com.notus.fit.models.generic;

/**
 * Created by VBALAUD on 9/3/2015.
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
