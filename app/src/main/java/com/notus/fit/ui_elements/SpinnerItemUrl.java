package com.notus.fit.ui_elements;

/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/3/2015 5:00 PM
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
