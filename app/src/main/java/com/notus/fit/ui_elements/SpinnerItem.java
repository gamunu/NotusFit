package com.notus.fit.ui_elements;

/**
 * Created by VBALAUD on 9/3/2015.
 */
public class SpinnerItem {
    private int drawableResourceId;
    private String text;

    public SpinnerItem(String s, int i) {
        drawableResourceId = i;
        text = s;
    }

    public int getDrawableResourceId() {
        return drawableResourceId;
    }

    public void setDrawableResourceId(int i) {
        drawableResourceId = i;
    }

    public String getText() {
        return text;
    }

    public void setText(String s) {
        text = s;
    }
}
