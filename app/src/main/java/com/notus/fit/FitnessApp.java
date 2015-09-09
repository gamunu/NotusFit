package com.notus.fit;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.parse.Parse;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.picasso.Picasso;

import net.danlew.android.joda.JodaTimeAndroid;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;

public class FitnessApp extends Application {

    public static final String LOG_TAG = FitnessApp.class.getSimpleName();

    public FitnessApp() {
    }

    public Tracker getTracker() {
        Tracker tracker = GoogleAnalytics.getInstance(this).newTracker(0x7f060003);
        tracker.enableAdvertisingIdCollection(true);
        return tracker;
    }

    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Kit[]{
                new Crashlytics()
        });
        JodaTimeAndroid.init(this);
        LeakCanary.install(this);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "PV9mMaJEOsBPHMonhbZKh3v7H6qDxvpI0GGRDARb", "3bv40mF10tmfm2Kc70SxPcfeNeJWznfWavasks9k");
        DrawerImageLoader.init(new com.mikepenz.materialdrawer.util.DrawerImageLoader.IDrawerImageLoader() {
            public void cancel(ImageView imageview) {
                Picasso.with(imageview.getContext()).cancelRequest(imageview);
            }

            public Drawable placeholder(Context context) {
                return ContextCompat.getDrawable(context, R.drawable.ic_person);
            }

            public void set(ImageView imageview, Uri uri, Drawable drawable) {
                Picasso.with(imageview.getContext()).load(uri).placeholder(drawable).into(imageview);
            }
        });
    }

}
