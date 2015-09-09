package com.notus.fit.activities;

import android.app.Activity;
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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.crashlytics.android.Crashlytics;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class DrawerActivity extends BaseActivity implements BillingProcessor.IBillingHandler {
    public static final int DRAWER_ACHIEVEMENTS = 4;
    public static final int DRAWER_CHALLENGES = 3;
    public static final int DRAWER_DASHBOARD = 0;
    public static final int DRAWER_DONATE = 6;
    public static final int DRAWER_HISTORY = 2;
    public static final int DRAWER_LEADERBOARD = 1;
    public static final int DRAWER_SETTINGS = 5;
    public static final int DRAWER_SIGN_OUT = 7;
    public static final int RC_GAMES_SIGN_IN = 13510;
    protected Context activityContext;
    protected GoogleApiClient mGamesClient;
    String billingID;
    BillingProcessor billingProcessor;
    MaterialDialog donateDialog;
    List<String> products;
    private boolean mAutoStartSignInflow;
    private boolean mResolvingConnectionFailure;
    private DrawerActivity thisActivity;


    public DrawerActivity() {
        this.mResolvingConnectionFailure = false;
        this.mAutoStartSignInflow = true;
        this.billingID = null;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.MaterialDrawerTheme_Light_DarkToolbar_ActionBar_TranslucentStatus);
        this.activityContext = this;
        this.thisActivity = this;
        this.billingProcessor = new BillingProcessor(this, getString(R.string.billing_key), this);
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
            if (now.getHourOfDay() > 18 || now.getHourOfDay() < DRAWER_DONATE) {
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
                        Picasso.with(DrawerActivity.this.thisActivity).load(avatarUrl).fit().centerCrop().into(circleImageView, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                Picasso.with(DrawerActivity.this.thisActivity).load(avatarUrl).fit().centerCrop().into(circleImageView);
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
        DrawerBuilder withHeader = new DrawerBuilder().withActivity(this).withToolbar(this.toolbar).withHeader(headerView);
        IDrawerItem[] iDrawerItemArr = new IDrawerItem[DRAWER_ACHIEVEMENTS];
        iDrawerItemArr[DRAWER_DASHBOARD] = new PrimaryDrawerItem().withName("Dashboard").withIcon(R.drawable.ic_dashboard_drawer).withIdentifier(DRAWER_DASHBOARD);
        iDrawerItemArr[DRAWER_LEADERBOARD] = new PrimaryDrawerItem().withName("Leaderboard").withIcon(R.drawable.ic_trophy)
                .withIconColor(getResources().getColor(R.color.secondary_text)).withSelectedIconColor(getResources().getColor(R.color.primary))
                .withIdentifier(DRAWER_LEADERBOARD);
        iDrawerItemArr[DRAWER_HISTORY] = new PrimaryDrawerItem().withName("Achievements").withIcon(R.drawable.ic_drawer_challenge).withIconColor(getResources().getColor(R.color.secondary_text))
                .withSelectedIconColor(getResources().getColor(R.color.primary)).withIdentifier(DRAWER_ACHIEVEMENTS);
        iDrawerItemArr[DRAWER_CHALLENGES] = new PrimaryDrawerItem().withName("Donate").withIcon(R.drawable.ic_donate).withIdentifier(DRAWER_DONATE);
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
                        iL = new Intent(DrawerActivity.this.thisActivity, MainActivity.class);
                        break;
                    case DrawerActivity.DRAWER_LEADERBOARD /*1*/:
                        if (PrefManager.with(DrawerActivity.this.thisActivity).getBoolean(DrawerActivity.this.getString(R.string.games_enabled), true) && DrawerActivity.this.mGamesClient != null) {
                            if (!DrawerActivity.this.mGamesClient.isConnected()) {
                                DrawerActivity.this.mGamesClient.connect();
                                break;
                            } else {
                                DrawerActivity.this.startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(DrawerActivity.this.mGamesClient), BaseGameActivity.REQUEST_LEADERBOARD);
                                break;
                            }
                        }
                    case DrawerActivity.DRAWER_HISTORY /*2*/:
                        iL = new Intent(DrawerActivity.this.thisActivity, ActivityHistory.class);
                        break;
                    case DrawerActivity.DRAWER_CHALLENGES /*3*/:
                        iL = new Intent(DrawerActivity.this.thisActivity, ChallengesActivity.class);
                        break;
                    case DrawerActivity.DRAWER_ACHIEVEMENTS /*4*/:
                        if (PrefManager.with(DrawerActivity.this.thisActivity).getBoolean(DrawerActivity.this.getString(R.string.games_enabled), true) && DrawerActivity.this.mGamesClient != null) {
                            if (!DrawerActivity.this.mGamesClient.isConnected()) {
                                DrawerActivity.this.mGamesClient.connect();
                                break;
                            } else {
                                DrawerActivity.this.startActivityForResult(Games.Achievements.getAchievementsIntent(DrawerActivity.this.mGamesClient), BaseGameActivity.REQUEST_ACHIEVEMENTS);
                                break;
                            }
                        }
                    case DrawerActivity.DRAWER_SETTINGS /*5*/:
                        DrawerActivity.this.startActivity(new Intent(DrawerActivity.this.thisActivity, ActivitySettings.class));
                        break;
                    case DrawerActivity.DRAWER_DONATE /*6*/:
                        DrawerActivity.this.showDonateDialog();
                        break;
                    case DrawerActivity.DRAWER_SIGN_OUT /*7*/:
                        new DoLogout().execute(new Void[DrawerActivity.DRAWER_DASHBOARD]);
                        break;
                }
                if (iL != null) {
                    iL.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    DrawerActivity.this.startActivity(iL);
                    DrawerActivity.this.finish();
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
                    if (DrawerActivity.this.mGamesClient != null) {
                        DrawerActivity.this.mGamesClient.connect();
                    }
                }
            }).addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                @Override
                public void onConnectionFailed(ConnectionResult connectionResult) {
                    if (!DrawerActivity.this.mResolvingConnectionFailure && DrawerActivity.this.mAutoStartSignInflow) {
                        DrawerActivity.this.mAutoStartSignInflow = false;
                        DrawerActivity.this.mSignInClicked = false;
                        DrawerActivity.this.mResolvingConnectionFailure = true;
                        if (!BaseGameUtils.resolveConnectionFailure(DrawerActivity.this.thisActivity, DrawerActivity.this.mGamesClient, connectionResult, DrawerActivity.RC_GAMES_SIGN_IN, DrawerActivity.this.getString(R.string.sign_in_failed))) {
                            DrawerActivity.this.mResolvingConnectionFailure = false;
                        }
                    }
                }
            }).addApi(Games.API).addScope(Games.SCOPE_GAMES).build();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!this.billingProcessor.handleActivityResult(requestCode, resultCode, data)) {
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
    }

    public void showDonateDialog() {
        this.donateDialog = new MaterialDialog.Builder(this).title("Donate")
                .icon(getResources().getDrawable(R.drawable.ic_donate_cart))
                .customView(R.layout.donate_dialog, true).negativeText("Cancel")
                .negativeColor(getResources().getColor(R.color.accent_color))
                .positiveText("Donate").positiveColor(getResources().getColor(R.color.primary_dark))
                .build();
        View v = this.donateDialog.getCustomView();
        if (v != null) {
            RadioButton support = (RadioButton) v.findViewById(R.id.development);
            RadioButton coffee = (RadioButton) v.findViewById(R.id.coffee);
            RadioButton beer = (RadioButton) v.findViewById(R.id.beer);
            RadioButton pizza = (RadioButton) v.findViewById(R.id.pizza);
            RadioButton dinner = (RadioButton) v.findViewById(R.id.dinner);
            RadioButton college = (RadioButton) v.findViewById(R.id.college);
            final ArrayList<RadioButton> buttons = new ArrayList();
            buttons.add(support);
            buttons.add(coffee);
            buttons.add(beer);
            buttons.add(pizza);
            buttons.add(dinner);
            buttons.add(college);
            this.products = this.billingProcessor.listOwnedProducts();
            Iterator it = buttons.iterator();
            while (it.hasNext()) {
                ((RadioButton) it.next()).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Iterator it = buttons.iterator();
                        while (it.hasNext()) {
                            ((RadioButton) it.next()).setChecked(false);
                        }
                        ((RadioButton) v).setChecked(true);
                        DrawerActivity.this.billingID = DrawerActivity.this.getBillingId(v.getId());
                    }
                });
            }
            this.donateDialog.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (DrawerActivity.this.billingID != null && (DrawerActivity.this.activityContext instanceof Activity)) {
                        DrawerActivity.this.billingProcessor.purchase((Activity) DrawerActivity.this.activityContext, DrawerActivity.this.billingID);
                    }
                }
            });
        }
        this.donateDialog.show();
    }

    public String getBillingId(int id) {
        switch (id) {
            case R.id.development:
                return getResources().getString(R.string.encourage_development);
            case R.id.coffee:
                return getResources().getString(R.string.coffee);
            case R.id.beer:
                return getResources().getString(R.string.beer);
            case R.id.pizza:
                return getResources().getString(R.string.pizza);
            case R.id.dinner:
                return getResources().getString(R.string.dinner);
            case R.id.college:
                return getResources().getString(R.string.college);
            default:
                return null;
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
        if (this.billingProcessor != null) {
            this.billingProcessor.release();
        }
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void onProductPurchased(String purchaseId, TransactionDetails transactionDetails) {
        if (this.donateDialog != null && this.donateDialog.isShowing()) {
            this.donateDialog.dismiss();
        }
        boolean purchased = false;
        for (String s : this.products) {
            if (s.equals(purchaseId)) {
                purchased = true;
                Toast.makeText(this, "You can only buy this item once!", Toast.LENGTH_LONG).show();
            }
        }
        if (!purchased) {
            Toast.makeText(this, "Thank you!! You rock!", Toast.LENGTH_LONG).show();
        }
    }

    public void onPurchaseHistoryRestored() {
    }

    public void onBillingError(int i, Throwable throwable) {
        if (this.donateDialog != null && this.donateDialog.isShowing()) {
            this.donateDialog.dismiss();
        }
        new MaterialDialog.Builder(this).title("Error").content("An error occurred while processing your purchase.").positiveText("Dismiss").positiveColor(getResources().getColor(R.color.accent_color)).build().show();
    }

    public void onBillingInitialized() {
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
            this.pd = new ProgressDialog(DrawerActivity.this.activityContext);
            this.pd.setTitle("Signing out...");
            this.pd.setCancelable(false);
            this.pd.setIndeterminate(true);
            this.pd.show();
        }

        protected Boolean doInBackground(Void... params) {
            try {
                Thread.sleep(300);
                PrefManager.with(DrawerActivity.this.thisActivity).clear();
                if (DrawerActivity.this.facebookSession != null) {
                    DrawerActivity.this.facebookSession.closeAndClearTokenInformation();
                }
                BaseActivity.callFacebookLogout(DrawerActivity.this.activityContext);
                Session.setActiveSession(null);
                NetworkUtils.cancelAlarms(DrawerActivity.this.activityContext);
                DrawerActivity.this.signOut();
                Intent iL = new Intent(DrawerActivity.this.activityContext, MainActivity.class);
                iL.addFlags(335544320);
                DrawerActivity.this.startActivity(iL);
                DrawerActivity.this.finish();
                return Boolean.valueOf(true);
            } catch (Exception ex) {
                Log.d(BaseActivity.LOG_TAG, ex.getMessage());
                Crashlytics.logException(ex);
                return Boolean.valueOf(false);
            }
        }

        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (!aBoolean) {
                Toast.makeText(DrawerActivity.this.activityContext, "There was an error signing out...", Toast.LENGTH_LONG).show();
            }
            this.pd.dismiss();
        }
    }
}
