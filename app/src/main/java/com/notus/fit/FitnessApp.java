package com.notus.fit;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.parse.Parse;
import com.squareup.picasso.Picasso;

import net.danlew.android.joda.JodaTimeAndroid;

public class FitnessApp extends Application {

    public static final String LOG_TAG = FitnessApp.class.getSimpleName();

    public FitnessApp() {
    }

    public Tracker getTracker() {
        Tracker tracker = GoogleAnalytics.getInstance(this).newTracker(R.xml.tracker);
        tracker.enableAdvertisingIdCollection(true);
        return tracker;
    }

    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "bZkpVQnX2Z5F9bPWvQwdg3h2W0aj2zunUi1O11SX", "x9betK202bbhMQZ11yJhjlQxnOPECXvvFR0pgK1G");
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
