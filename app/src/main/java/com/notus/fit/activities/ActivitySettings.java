package com.notus.fit.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.notus.fit.BuildConfig;
import com.notus.fit.R;
import com.notus.fit.fragments.SettingsFragment;
import com.notus.fit.utils.CustomUtils;


public class ActivitySettings extends BaseActivity
        implements android.view.View.OnClickListener {

    public ActivitySettings() {
    }

    public int getLayoutResource() {
        return R.layout.activity_settings;
    }

    public void onClick(View v) {
        String url = BuildConfig.FLAVOR;
        Intent intent = new Intent("android.intent.action.VIEW");
        switch (v.getId()) {
            case R.id.dev_gplus:
                url = "https://plus.google.com/u/0/+MarcusViniciusAndreoGabilheri";
                break;
            case R.id.dev_in:
                url = "http://www.linkedin.com/in/marcusgabilheri/";
                break;
            case R.id.dev_git:
                url = "https://github.com/fnk0";
                break;
            case R.id.dev_web:
                url = "http://www.gabilheri.com/";
                break;
        }
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomUtils.addFragmentToContainer(new SettingsFragment(), R.id.container, this);
        ImageView devGit = (ImageView) findViewById(R.id.dev_git);
        ImageView devIn = (ImageView) findViewById(R.id.dev_in);
        ImageView devWeb = (ImageView) findViewById(R.id.dev_web);
        ((ImageView) findViewById(R.id.dev_gplus)).setOnClickListener(this);
        devGit.setOnClickListener(this);
        devIn.setOnClickListener(this);
        devWeb.setOnClickListener(this);
        enableBackNav();
        setTitle("Settings");
    }
}
