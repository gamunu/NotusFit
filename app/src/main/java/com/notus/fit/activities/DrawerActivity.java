package com.notus.fit.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.AppEventsConstants;
import com.facebook.Session;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.games.Games;
import com.mikepenz.google_material_typeface_library.GoogleMaterial.Icon;
import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.notus.fit.BuildConfig;
import com.notus.fit.MainActivity;
import com.notus.fit.R;
import com.notus.fit.gameUtils.BaseGameActivity;
import com.notus.fit.gameUtils.BaseGameUtils;
import com.notus.fit.models.api_models.User;
import com.notus.fit.utils.FitnessUtils;
import com.notus.fit.utils.NetworkUtils;
import com.notus.fit.utils.PrefManager;
import com.notus.fit.utils.PreferenceUtils;
import com.notus.fit.utils.WeatherUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.joda.time.LocalDateTime;

import java.util.List;

public abstract class DrawerActivity extends BaseActivity {
    public static final int DRAWER_ACHIEVEMENTS = 4;
    public static final int DRAWER_CHALLENGES = 3;
    public static final int DRAWER_DASHBOARD = 0;
    //public static final int DRAWER_DONATE = 6;
    public static final int DRAWER_HISTORY = 2;
    public static final int DRAWER_LEADERBOARD = 1;
    public static final int DRAWER_SETTINGS = 5;
    public static final int DRAWER_SIGN_OUT = 7;
    public static final int RC_GAMES_SIGN_IN = 13510;
    protected Context activityContext;
    protected GoogleApiClient mGamesClient;
    private boolean mAutoStartSignInflow = true;
    private boolean mResolvingConnectionFailure = false;
    private DrawerActivity thisActivity;


    public DrawerActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.MaterialDrawerTheme_Light_DarkToolbar_ActionBar_TranslucentStatus);
        this.activityContext = this;
        this.thisActivity = this;
        this.mTitle = getTitle();
        View headerView = getLayoutInflater().inflate(R.layout.profile_drawer_item, null, false);
        if (headerView != null) {
            String temp = PrefManager.with(this).getString("temp", AppEventsConstants.EVENT_PARAM_VALUE_NO);
            String units = PrefManager.with(this).getString(User.UNITS, FitnessUtils.UNIT_IMPERIAL);
            temp = temp + "\u00b0 ";
            if (units.equals(FitnessUtils.UNIT_IMPERIAL)) {
                temp = temp + "F";
            } else {
                temp = temp + "C";
            }
            TextView weatherText = (TextView) headerView.findViewById(R.id.weather_text);
            weatherText.setText(temp);
            weatherText.setCompoundDrawablesWithIntrinsicBounds(WeatherUtils.getArtResourceForWeatherCondition(Integer.parseInt(PrefManager.with(this).getString("weather_id", "800"))), DRAWER_DASHBOARD, DRAWER_DASHBOARD, DRAWER_DASHBOARD);
            LocalDateTime now = LocalDateTime.now();
            LinearLayout background = (LinearLayout) headerView.findViewById(R.id.background);
            if (now.getHourOfDay() > 18 || now.getHourOfDay() < DRAWER_SETTINGS) {
                background.setBackgroundResource(R.drawable.wall_night);
            } else {
                background.setBackgroundResource(R.drawable.wall_day_sunny);
            }
            final ImageView circleImageView = (ImageView) headerView.findViewById(R.id.profile_image);
            final String avatarUrl = PrefManager.with(this).getString(PreferenceUtils.AVATAR_URL, BuildConfig.FLAVOR);
            if (!avatarUrl.equals(BuildConfig.FLAVOR)) {
                Picasso.with(this).load(avatarUrl).into(circleImageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(thisActivity).load(avatarUrl).fit().centerCrop().into(circleImageView, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                Picasso.with(thisActivity).load(avatarUrl).fit().centerCrop().into(circleImageView);
                            }
                        });
                    }
                });
            }
            TextView name = (TextView) headerView.findViewById(R.id.drawer_name);
            String firstName = PrefManager.with(this).getString(User.FIRST_NAME, BuildConfig.FLAVOR);
            String lastName = PrefManager.with(this).getString(User.LAST_NAME, BuildConfig.FLAVOR);
            name.setText(firstName + " " + lastName);
            TextView drawerSteps = (TextView) headerView.findViewById(R.id.drawer_steps);
            int steps = Integer.parseInt(PrefManager.with(this).getString(PreferenceUtils.WEEK_AVERAGE, AppEventsConstants.EVENT_PARAM_VALUE_NO));
            drawerSteps.setText("Week Average: " + steps + " steps.");
        }
        DrawerBuilder withHeader = new DrawerBuilder().withActivity(this).withToolbar(this.mToolbar).withHeader(headerView);
        IDrawerItem[] iDrawerItemArr = new IDrawerItem[DRAWER_ACHIEVEMENTS];
        iDrawerItemArr[DRAWER_DASHBOARD] = new PrimaryDrawerItem().withName("Dashboard").withIcon(R.drawable.ic_dashboard_drawer).withIdentifier(DRAWER_DASHBOARD);
        iDrawerItemArr[DRAWER_LEADERBOARD] = new PrimaryDrawerItem().withName("Leaderboard").withIcon(R.drawable.ic_trophy)
                .withIconColor(getResources().getColor(R.color.secondary_text)).withSelectedIconColor(getResources().getColor(R.color.primary))
                .withIdentifier(DRAWER_LEADERBOARD);
        iDrawerItemArr[DRAWER_HISTORY] = new PrimaryDrawerItem().withName("Achievements").withIcon(R.drawable.ic_drawer_challenge).withIconColor(getResources().getColor(R.color.secondary_text))
                .withSelectedIconColor(getResources().getColor(R.color.primary)).withIdentifier(DRAWER_ACHIEVEMENTS);
       iDrawerItemArr[DRAWER_CHALLENGES] = new PrimaryDrawerItem().withName("Challenges").withIcon(R.drawable.ic_drawer_challenge).withIdentifier(DRAWER_CHALLENGES);
        withHeader = withHeader.addDrawerItems(iDrawerItemArr);
        iDrawerItemArr = new IDrawerItem[DRAWER_HISTORY];
        iDrawerItemArr[DRAWER_DASHBOARD] = new PrimaryDrawerItem().withName("Settings").withIcon((IIcon) Icon.gmd_settings).withIdentifier(DRAWER_SETTINGS);
        iDrawerItemArr[DRAWER_LEADERBOARD] = new PrimaryDrawerItem().withName("Sign Out").withIcon((IIcon) Icon.gmd_exit_to_app).withIdentifier(DRAWER_SIGN_OUT);
        withHeader.addStickyDrawerItems(iDrawerItemArr).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                Intent iL = null;
                //   Log.d(BaseActivity.LOG_TAG, "Pos: " + position + " -- ID: " + id + " -- Identifier: " + iDrawerItem.getIdentifier());
                switch (iDrawerItem.getIdentifier()) {
                    case DrawerActivity.DRAWER_DASHBOARD /*0*/:
                        iL = new Intent(thisActivity, MainActivity.class);
                        break;
                    case DrawerActivity.DRAWER_LEADERBOARD /*1*/:
                        if (PrefManager.with(thisActivity).getBoolean(getString(R.string.games_enabled), true) && mGamesClient != null) {
                            if (!mGamesClient.isConnected()) {
                                mGamesClient.connect();
                                break;
                            } else {
                                startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(mGamesClient), BaseGameActivity.REQUEST_LEADERBOARD);
                                break;
                            }
                        }
                    case DrawerActivity.DRAWER_HISTORY /*2*/:
                        iL = new Intent(thisActivity, ActivityHistory.class);
                        break;
                    case DrawerActivity.DRAWER_CHALLENGES /*3*/:
                        iL = new Intent(thisActivity, ChallengesActivity.class);
                        break;
                    case DrawerActivity.DRAWER_ACHIEVEMENTS /*4*/:
                        if (PrefManager.with(thisActivity).getBoolean(getString(R.string.games_enabled), true) && mGamesClient != null) {
                            if (!mGamesClient.isConnected()) {
                                mGamesClient.connect();
                                break;
                            } else {
                                startActivityForResult(Games.Achievements.getAchievementsIntent(mGamesClient), BaseGameActivity.REQUEST_ACHIEVEMENTS);
                                break;
                            }
                        }
                    case DrawerActivity.DRAWER_SETTINGS /*5*/:
                        startActivity(new Intent(thisActivity, ActivitySettings.class));
                        break;
                    case DrawerActivity.DRAWER_SIGN_OUT /*7*/:
                        new DoLogout().execute();
                        break;
                }
                if (iL != null) {
                    iL.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(iL);
                    finish();
                    return true;
                }
                return false;
            }
        }).build();
        if (PrefManager.with(this).getBoolean(getString(R.string.games_enabled), true)) {
            this.mGamesClient = new Builder(getApplicationContext()).addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                @Override
                public void onConnected(Bundle bundle) {

                }

                @Override
                public void onConnectionSuspended(int i) {
                    if (mGamesClient != null) {
                        mGamesClient.connect();
                    }
                }
            }).addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                @Override
                public void onConnectionFailed(ConnectionResult connectionResult) {
                    if (!mResolvingConnectionFailure && mAutoStartSignInflow) {
                        mAutoStartSignInflow = false;
                        mSignInClicked = false;
                        mResolvingConnectionFailure = true;
                        if (!BaseGameUtils.resolveConnectionFailure(thisActivity, mGamesClient, connectionResult, DrawerActivity.RC_GAMES_SIGN_IN, getString(R.string.sign_in_failed))) {
                            mResolvingConnectionFailure = false;
                        }
                    }
                }
            }).addApi(Games.API).addScope(Games.SCOPE_GAMES).build();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GAMES_SIGN_IN) {
            this.mSignInClicked = false;
            this.mResolvingConnectionFailure = false;
            if (resultCode == -1) {
                this.mGamesClient.connect();
            } else {
                BaseGameUtils.showActivityResultError(this, requestCode, resultCode, R.string.sign_in_failed);
            }
        }
    }

    public void signOut() {
        super.signOut();
        if (this.mGamesClient != null && this.mGamesClient.isConnected()) {
            Games.signOut(this.mGamesClient);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    protected void onStart() {
        super.onStart();
        if (this.mGamesClient != null) {
            this.mGamesClient.connect();
        }
    }

    public void onStop() {
        super.onStop();
        if (this.mGamesClient != null) {
            this.mGamesClient.disconnect();
        }
    }

    public int getLayoutResource() {
        return R.layout.activity_drawer;
    }

    public GoogleApiClient getGamesClient() {
        return this.mGamesClient;
    }

    class DoLogout extends AsyncTask<Void, Void, Boolean> {
        ProgressDialog pd;

        DoLogout() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.pd = new ProgressDialog(activityContext);
            this.pd.setTitle("Signing out...");
            this.pd.setCancelable(false);
            this.pd.setIndeterminate(true);
            this.pd.show();
        }

        protected Boolean doInBackground(Void... params) {
            try {
                Thread.sleep(300);
                PrefManager.with(thisActivity).clear();
                if (mFacebookSession != null) {
                    mFacebookSession.closeAndClearTokenInformation();
                }
                BaseActivity.callFacebookLogout(activityContext);
                Session.setActiveSession(null);
                NetworkUtils.cancelAlarms(activityContext);
                signOut();
                Intent iL = new Intent(activityContext, MainActivity.class);
                iL.addFlags(335544320);
                startActivity(iL);
                finish();
                return true;
            } catch (Exception ex) {
                Log.e(LOG_TAG, ex.getMessage(), ex);
                return false;
            }
        }

        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (!aBoolean) {
                Toast.makeText(activityContext, "There was an error signing out...", Toast.LENGTH_LONG).show();
            }
            this.pd.dismiss();
        }
    }
}
