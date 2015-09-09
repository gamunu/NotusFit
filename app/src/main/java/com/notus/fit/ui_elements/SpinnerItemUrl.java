package com.notus.fit.ui_elements;

/**
 * Created by VBALAUD on 9/3/2015.
 */
public class SpinnerItemUrl {

    String text;
    String url;

    public SpinnerItemUrl(String s, String s1) {
        text = s;
        url = s1;
    }

    public String getText() {
        return text;
    }

    public SpinnerItemUrl setText(String s) {
        text = s;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public SpinnerItemUrl setUrl(String s) {
        url = s;
        return this;
    }

}
