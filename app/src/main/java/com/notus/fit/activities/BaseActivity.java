package com.notus.fit.activities;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.AppEventsLogger;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.notus.fit.BuildConfig;
import com.notus.fit.FitnessApp;
import com.notus.fit.R;
import com.notus.fit.adapters.DashboardSpinnerAdapter;
import com.notus.fit.models.api_models.User;
import com.notus.fit.ui_elements.SpinnerItem;
import com.notus.fit.utils.CustomUtils;
import com.notus.fit.utils.Devices;
import com.notus.fit.utils.FitnessUtils;
import com.notus.fit.utils.PrefManager;
import com.notus.fit.utils.PreferenceUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/6/2015 1:12 PM
 */
public class BaseActivity extends AppCompatActivity {

    public static final int ALL_DEVICES = 0;
    public static final int ANDROID_WEAR = 1;
    public static final String APP_VERSION = "APP_VERSION";
    public static final String APP_VERSION_NUMBER = "25";
    public static final int FITBIT = 2;
    public static final int JAWBONE = 3;
    public static final int MISFIT = 4;
    public static final int MOVES = 5;
    public static final int REQUEST_OAUTH = 1;
    public static final String SAVED_PROGRESS = "sign_in_progress";
    public static final int SIGN_UP_CODE = 1311;
    public static final int STATE_DEFAULT = 0;
    public static final int STATE_IN_PROGRESS = 2;
    public static final int STATE_SIGN_IN = 1;
    protected static final List<String> PERMISSIONS;
    protected static final int PROFILE_PIC_SIZE = 300;
    protected static final int RC_SIGN_IN = 40410;
    private static final String AUTH_PENDING = "auth_state_pending";
    protected static String LOG_TAG = BaseActivity.class.getSimpleName();

    static {
        String[] strArr = new String[STATE_SIGN_IN];
        strArr[STATE_DEFAULT] = "publish_actions";
        PERMISSIONS = Arrays.asList(strArr);
    }

    public Session.StatusCallback statusCallback = new com.facebook.Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState sessionState, Exception e) {
            if (sessionState.isOpened()) {
                mFacebookSession = session;
            }
        }
    };

    protected boolean mAuthInProgress = false;
    protected boolean mConnectFit = false;
    protected FrameLayout mContainerLayout;
    protected Map<Integer, Integer> mDeviceOrder;
    protected DateTimeFormatter mDtf = DateTimeFormat.forPattern("yyyy-MMM-dd");
    protected Session mFacebookSession;
    protected Button mFitConnectButton;
    protected Button mFitDisconnectButton;
    protected Map<String, String> mFriendsList;
    protected boolean mHasFitbit = false;
    protected boolean mHasJawbone = false;
    protected boolean mHasMisfit = false;
    protected boolean mHasMoves = false;
    protected boolean mHasWearDevice = false;
    protected boolean mIsLoggedIn = false;
    protected ConnectionResult mConnectionResult;
    protected UiLifecycleHelper mFacebookHelper;
    protected GoogleApiClient mGoogleFitClient;
    protected GoogleApiClient mGooglePlusClient;
    protected boolean mIntentInProgress;
    protected boolean mSignInClicked;
    protected CharSequence mTitle;
    protected boolean mTitleEnabled = true;
    protected Toolbar mToolbar;
    protected ImageView mToolbarLogo;
    protected Spinner mToolbarSpinner;
    protected TextView mToolbarTitle;
    protected User user;
    private int mSignInProgress;


    public static void callFacebookLogout(Context context) {
        Session session = Session.getActiveSession();
        if (session != null) {
            if (!session.isClosed()) {
                session.closeAndClearTokenInformation();
                // clear your preferences if saved
            }
        } else {
            session = new Session(context);
            Session.setActiveSession(session);
            session.closeAndClearTokenInformation();
            // clear your preferences if saved
        }
    }

    private void buildGoogleApiClient() {
        mGooglePlusClient = new GoogleApiClient.Builder(getApplicationContext())
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    public void onConnected(Bundle bundle) {
                        mSignInClicked = false;
                        if (mGooglePlusClient != null) {
                            user = getProfileInformation();
                        }
                    }

                    public void onConnectionSuspended(int i) {
                        mGooglePlusClient.connect();
                    }

                }).addOnConnectionFailedListener(new com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener() {
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        mConnectionResult = connectionResult;
                        if (!connectionResult.hasResolution()) {
                            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), BaseActivity.this, BaseActivity.STATE_DEFAULT).show();
                        } else if (!mIntentInProgress && mSignInClicked) {
                            resolveSignInError();
                        }
                    }
                }).addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN).addScope(Plus.SCOPE_PLUS_PROFILE).build();
    }

    public void buildFitnessClient(Button button, Button button1) {
        mFitConnectButton = button;
        mFitDisconnectButton = button1;
        mConnectFit = true;
        if (mGoogleFitClient == null) {
            startFitnessClient();
        }
        if (!mGoogleFitClient.isConnected()) {
            mGoogleFitClient.connect();
        }
    }

    public void enableBackNav() {
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public GoogleApiClient getGoogleFitClient() {
        return mGoogleFitClient;
    }

    public int getLayoutResource() {
        return R.layout.activity_base;
    }

    public User getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(this.mGooglePlusClient) != null) {
                this.user = new User();
                Person mCurrentPerson = Plus.PeopleApi.getCurrentPerson(this.mGooglePlusClient);
                String personName = mCurrentPerson.getDisplayName();
                String personPhotoUrl = mCurrentPerson.getImage().getUrl();
                String personEmail = Plus.AccountApi.getAccountName(this.mGooglePlusClient);
                String[] fullName = personName.split(" ");
                this.user.setFirstName(fullName[STATE_DEFAULT]);
                try {
                    this.user.setLastName(fullName[fullName.length - 1]);
                } catch (ArrayIndexOutOfBoundsException ex) {
                    Log.e(LOG_TAG, ex.getMessage(), ex);
                }
                this.user.setEmail(personEmail);
                this.user.setUsername(personEmail);
                this.user.setAvatarUrl(personPhotoUrl.substring(STATE_DEFAULT, personPhotoUrl.length() - 2) + PROFILE_PIC_SIZE);
                if (this instanceof StartActivity) {
                    ((StartActivity) this).getSignUpFragment().executeLogin(this.user);
                }
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
        }
        return this.user;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode != -1) {
                this.mSignInClicked = false;
            }
            this.mIntentInProgress = false;
            if (!this.mGooglePlusClient.isConnecting()) {
                this.mGooglePlusClient.connect();
                PrefManager.with(this).save(PreferenceUtils.GPLUS_SIGNED_IN, true);
            }
        } else if (requestCode == STATE_SIGN_IN) {
            this.mAuthInProgress = false;
            if (resultCode != -1) {
                PrefManager.with(this).save(User.HAS_GOOGLEFIT, false);
            } else {
                if (!(this.mGoogleFitClient.isConnecting() || this.mGoogleFitClient.isConnected())) {
                    this.mGoogleFitClient.connect();
                    PrefManager.with(this).save(User.HAS_GOOGLEFIT, true);
                    this.mHasWearDevice = true;
                    if (this.mFitConnectButton != null) {
                        setConnected(this.mFitConnectButton, this.mFitDisconnectButton);
                    }
                    try {
                        ParseObject userObject = ParseQuery.getQuery(User.CLASS).whereEqualTo(User.OBJECT_ID, PrefManager.with(this).getString(User.OBJECT_ID, BuildConfig.FLAVOR)).getFirst();
                        userObject.put(User.HAS_GOOGLEFIT, true);
                        userObject.save();
                    } catch (ParseException ex) {
                        Log.e(LOG_TAG, ex.getMessage(), ex);
                    }
                }
            }
        }
        if (resultCode == SIGN_UP_CODE) {
            Log.d(LOG_TAG, "Result sign up!");
        }
        if (this.mFacebookHelper != null) {
            this.mFacebookHelper.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        CustomUtils.lockOrientation(this);
        if (this instanceof StartActivity) {
            this.mFacebookHelper = new UiLifecycleHelper(this, this.statusCallback);
            this.mFacebookHelper.onCreate(savedInstanceState);
        }
        this.mContainerLayout = (FrameLayout) findViewById(R.id.container);
        this.mDeviceOrder = new HashMap<>();
        PrefManager.with(this).save(APP_VERSION, APP_VERSION_NUMBER);
        try {
            this.mToolbar = (Toolbar) findViewById(R.id.toolbar_drawer);
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
        }
        Tracker t = ((FitnessApp) getApplication()).getTracker();
        t.setScreenName(getClass().getSimpleName());
        t.setAppVersion(APP_VERSION_NUMBER);
        t.send(new HitBuilders.ScreenViewBuilder().build());
        this.mHasFitbit = PrefManager.with(this).getBoolean(User.HAS_FITBIT, false);
        this.mHasWearDevice = PrefManager.with(this).getBoolean(User.HAS_GOOGLEFIT, false);
        this.mHasJawbone = PrefManager.with(this).getBoolean(User.HAS_JAWBONE, false);
        this.mHasMisfit = PrefManager.with(this).getBoolean(User.HAS_MISFIT, false);
        this.mHasMoves = PrefManager.with(this).getBoolean(User.HAS_MOVES, false);
        if (this.mToolbar == null) {
            try {
                this.mToolbar = (Toolbar) findViewById(R.id.toolbar_spinner);
                this.mToolbarSpinner = (Spinner) this.mToolbar.findViewById(R.id.spinner_toolbar);
                this.mTitleEnabled = false;
                List<SpinnerItem> spinnerItems = new ArrayList<>();
                List<String> spinnerTitles = new ArrayList<>();
                List<Integer> icons = new ArrayList<>();
                spinnerTitles.add(Devices.NOTUSFIT);
                icons.add(R.mipmap.ic_launcher);
                this.mDeviceOrder.put(STATE_DEFAULT, STATE_DEFAULT);
                int counter = STATE_DEFAULT + STATE_SIGN_IN;
                if (this.mHasWearDevice) {
                    spinnerTitles.add(Devices.GOOGLE_FIT);
                    icons.add(R.drawable.ic_fit);
                    this.mDeviceOrder.put(counter, STATE_SIGN_IN);
                    counter += STATE_SIGN_IN;
                }
                if (this.mHasFitbit) {
                    spinnerTitles.add(Devices.FITBIT);
                    icons.add(R.mipmap.ic_fitbit_logo);
                    this.mDeviceOrder.put(counter, STATE_IN_PROGRESS);
                    counter += STATE_SIGN_IN;
                }
                if (this.mHasJawbone) {
                    spinnerTitles.add(Devices.JAWBONE);
                    icons.add(R.mipmap.ic_jawbone_up);
                    this.mDeviceOrder.put(counter, JAWBONE);
                    counter += STATE_SIGN_IN;
                }
                if (this.mHasMisfit) {
                    spinnerTitles.add(Devices.MISFIT);
                    icons.add(R.drawable.ic_logo_misfit);
                    this.mDeviceOrder.put(counter, MISFIT);
                    counter += STATE_SIGN_IN;
                }
                if (this.mHasMoves) {
                    spinnerTitles.add(Devices.MOVES);
                    icons.add(R.mipmap.ic_moves_logo_s);
                    this.mDeviceOrder.put(counter, MOVES);
                }
                for (int i = STATE_DEFAULT; i < spinnerTitles.size(); i += STATE_SIGN_IN) {
                    spinnerItems.add(new SpinnerItem(spinnerTitles.get(i), icons.get(i).intValue()));
                }
                this.mToolbarSpinner.setAdapter(new DashboardSpinnerAdapter(this, spinnerItems));
            } catch (Exception ex) {
                Log.e(LOG_TAG, ex.getMessage(), ex);
            }
        }
        if (this.mToolbar == null) {
            this.mToolbar = (Toolbar) findViewById(R.id.toolbar);
            this.mToolbarTitle = (TextView) this.mToolbar.findViewById(R.id.text_title);
            this.mToolbarLogo = (ImageView) this.mToolbar.findViewById(R.id.logo_image);
            this.mToolbarLogo.setImageResource(R.mipmap.ic_launcher);
        }
        if (this.mToolbar != null) {
            setSupportActionBar(this.mToolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
                getSupportActionBar().setDisplayShowTitleEnabled(this.mTitleEnabled);
            }
        }
        if (savedInstanceState != null) {
            this.mAuthInProgress = savedInstanceState.getBoolean(AUTH_PENDING);
        }
        if ((this instanceof StartActivity) || PrefManager.with(this).getBoolean(PreferenceUtils.GPLUS_SIGNED_IN, false)) {
            buildGoogleApiClient();
        }
        if (this.mHasWearDevice) {
            buildFitnessClient(null, null);
            this.mHasWearDevice = true;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    protected void onDestroy() {
        super.onDestroy();
        if (mFacebookHelper != null) {
            mFacebookHelper.onDestroy();
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuitem) {
        menuitem.getItemId();
        return super.onOptionsItemSelected(menuitem);
    }

    protected void onPause() {
        super.onPause();
        if (mFacebookHelper != null) {
            mFacebookHelper.onPause();
            AppEventsLogger.deactivateApp(this);
        }
    }

    protected void onResume() {
        super.onResume();
        if (mFacebookHelper != null) {
            AppEventsLogger.activateApp(this);
            mFacebookHelper.onResume();
        }
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("sign_in_progress", mSignInProgress);
        bundle.putBoolean("auth_state_pending", mAuthInProgress);
        if (mFacebookHelper != null) {
            mFacebookHelper.onSaveInstanceState(bundle);
        }
    }

    protected void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
        if (mGooglePlusClient != null) {
            mGooglePlusClient.connect();
        }
        if (mHasWearDevice && mGoogleFitClient != null) {
            mGoogleFitClient.connect();
        }
    }

    public void onStop() {
        super.onStop();
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
        if (mGooglePlusClient != null && mGooglePlusClient.isConnected()) {
            mGooglePlusClient.disconnect();
        }
        if (mHasWearDevice && mGoogleFitClient != null && mGoogleFitClient.isConnected()) {
            mGoogleFitClient.disconnect();
        }
    }

    public void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException ex) {
                Log.e(LOG_TAG, ex.getMessage(), ex);
                mIntentInProgress = false;
                mGooglePlusClient.connect();
            }
        }
    }

    public void revokeGoogleFitAccess() {
        if (mGoogleFitClient != null && mGoogleFitClient.isConnected()) {
            Fitness.ConfigApi.disableFit(mGoogleFitClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(Status status) {
                    if (status.isSuccess()) {
                        Log.i(LOG_TAG, "Google Fit disabled");
                        mGoogleFitClient = null;
                    } else {
                        Log.e(LOG_TAG, "Google Fit wasn't disabled " + status.toString());
                    }
                }
            });
        }
    }

    public void revokeGplusAccess() {
        if (mGooglePlusClient != null && mGooglePlusClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGooglePlusClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGooglePlusClient)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            Log.i(LOG_TAG, "User access revoked");
                            mGooglePlusClient.connect();
                        }
                    });
        }
    }

    public void setConnected(Button button, Button button1) {
        if (button != null && button1 != null) {
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.green_500));
            button.setText("Connected!");
            button.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this, R.drawable.ic_done), null, null, null);
            button1.setVisibility(View.VISIBLE);
        }
    }

    public void setDisconnected(Button button, String s) {
        button.setBackgroundResource(FitnessUtils.getResourceForButton(this, s));
        button.setText(s);
        button.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this, R.drawable.ic_arrow_forward), null, null, null);
    }

    public void setTitle(CharSequence charsequence) {
        mTitle = charsequence;
        getSupportActionBar().setTitle(mTitle);
    }

    public void signInWithGplus() {
        Log.i(LOG_TAG, "Logging in...");
        if (!mGooglePlusClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }
    }

    public void signOut() {
        revokeGplusAccess();
        revokeGoogleFitAccess();
    }

    public void signOutFromGplus() {
        if (mGooglePlusClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGooglePlusClient);
            mGooglePlusClient.disconnect();
            mGooglePlusClient.connect();
        }
    }

    public void startFitnessClient() {
        Log.d(LOG_TAG, "Starting fitness client...");
        mGoogleFitClient = (new GoogleApiClient.Builder(getApplicationContext()))
                .addApi(Fitness.HISTORY_API)
                .addApi(Fitness.CONFIG_API)
                .addScope(new Scope("https://www.googleapis.com/auth/fitness.activity.write"))
                .addScope(new Scope("https://www.googleapis.com/auth/fitness.body.write"))
                .addConnectionCallbacks(new com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks() {
                    public void onConnected(Bundle bundle) {
                        if (mHasWearDevice) {
                            mGoogleFitClient.connect();
                        }
                    }

                    public void onConnectionSuspended(int i) {
                        // If your connection to the sensor gets lost at some point,
                        // you'll be able to determine the reason and react to it here.
                        if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST) {
                            Log.i(LOG_TAG, "Connection lost.  Cause: Network Lost.");
                        } else if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
                            Log.i(LOG_TAG, "Connection lost.  Reason: Service Disconnected");
                        }
                    }
                }).addOnConnectionFailedListener(new com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener() {
                    public void onConnectionFailed(ConnectionResult connectionresult) {
                        if (!mAuthInProgress) {
                            try {
                                Log.i(LOG_TAG, "Attempting to resolve failed connection");
                                mAuthInProgress = true;
                                connectionresult.startResolutionForResult(BaseActivity.this,
                                        REQUEST_OAUTH);
                            } catch (IntentSender.SendIntentException e) {
                                Log.e(LOG_TAG, "Exception while starting resolution activity", e);
                            }
                        }
                    }
                }).build();
    }
}
