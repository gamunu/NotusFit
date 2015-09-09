package com.notus.fit.ui_elements;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.AppEventsConstants;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.notus.fit.BuildConfig;
import com.notus.fit.R;
import com.notus.fit.models.api_models.User;
import com.notus.fit.utils.FitnessUtils;
import com.notus.fit.utils.PrefManager;
import com.notus.fit.utils.PreferenceUtils;
import com.notus.fit.utils.WeatherUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.joda.time.LocalDateTime;

public class DrawerHeader implements IDrawerItem {

    boolean enabled;
    int identifier;

    public DrawerHeader() {
        this.identifier = 0;
        this.enabled = true;
    }

    public int getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public Object getTag() {
        return null;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public String getType() {
        return "HEADER_ITEM";
    }

    public int getLayoutRes() {
        return R.layout.profile_drawer_item;
    }

    public View convertView(LayoutInflater layoutInflater, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            final Context context = viewGroup.getContext();
            convertView = layoutInflater.inflate(getLayoutRes(), viewGroup, false);
            String temp = PrefManager.with(context).getString("temp", AppEventsConstants.EVENT_PARAM_VALUE_NO);
            String units = PrefManager.with(context).getString(User.UNITS, FitnessUtils.UNIT_IMPERIAL);
            temp = temp + "\u00b0 ";
            if (units.equals(FitnessUtils.UNIT_IMPERIAL)) {
                temp = temp + "F";
            } else {
                temp = temp + "C";
            }
            TextView weatherText = (TextView) convertView.findViewById(R.id.weather_text);
            weatherText.setText(temp);
            weatherText.setCompoundDrawablesWithIntrinsicBounds(WeatherUtils.getArtResourceForWeatherCondition(Integer.parseInt(PrefManager.with(context).getString("weather_id", "800"))), 0, 0, 0);
            LocalDateTime now = LocalDateTime.now();
            LinearLayout background = (LinearLayout) convertView.findViewById(R.id.background);
            if (now.getHourOfDay() > 18 || now.getHourOfDay() < 6) {
                background.setBackgroundResource(R.drawable.wall_night);
            } else {
                background.setBackgroundResource(R.drawable.wall_day_sunny);
            }
            final ImageView circleImageView = (ImageView) convertView.findViewById(R.id.profile_image);
            final String avatarUrl = PrefManager.with(context).getString(PreferenceUtils.AVATAR_URL, BuildConfig.FLAVOR);
            if (!avatarUrl.equals(BuildConfig.FLAVOR)) {
                Picasso.with(context).load(avatarUrl).into(circleImageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(context).load(avatarUrl).fit().centerCrop().into(circleImageView, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                Picasso.with(context).load(avatarUrl).fit().centerCrop().into(circleImageView);
                            }
                        });
                    }
                });
            }
            TextView name = (TextView) convertView.findViewById(R.id.drawer_name);
            String firstName = PrefManager.with(context).getString(User.FIRST_NAME, BuildConfig.FLAVOR);
            String lastName = PrefManager.with(context).getString(User.LAST_NAME, BuildConfig.FLAVOR);
            name.setText(firstName + " " + lastName);
            TextView drawerSteps = (TextView) convertView.findViewById(R.id.drawer_steps);
            int steps = Integer.parseInt(PrefManager.with(context).getString(PreferenceUtils.WEEK_AVERAGE, AppEventsConstants.EVENT_PARAM_VALUE_NO));
            drawerSteps.setText("Week Average: " + steps + " steps.");
        }
        return convertView;
    }
}
