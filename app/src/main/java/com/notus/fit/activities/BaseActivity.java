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

import com.crashlytics.android.Crashlytics;
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
import com.google.android.gms.common.api.Result;
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

/**
 * Created by VBALAUD on 9/6/2015.
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
        LOG_TAG = BaseActivity.class.getSimpleName();
    }

    public Session.StatusCallback statusCallback;
    protected boolean authInProgress;
    protected boolean connectFit;
    protected FrameLayout containerLayout;
    protected HashMap<Integer, Integer> deviceOrder;
    protected DateTimeFormatter dtf;
    protected Session facebookSession;
    protected Button fitConnectButton;
    protected Button fitDisconnectButton;
    protected HashMap<String, String> friendsList;
    protected boolean hasFitbit;
    protected boolean hasJawbone;
    protected boolean hasMisfit;
    protected boolean hasMoves;
    protected boolean hasWearDevice;
    protected boolean isLoggedIn;
    protected ConnectionResult mConnectionResult;
    protected UiLifecycleHelper mFacebookHelper;
    protected GoogleApiClient mGoogleFitClient;
    protected GoogleApiClient mGooglePlusClient;
    protected boolean mIntentInProgress;
    protected boolean mSignInClicked;
    protected CharSequence mTitle;
    protected boolean titleEnabled;
    protected Toolbar toolbar;
    protected ImageView toolbarLogo;
    protected Spinner toolbarSpinner;
    protected TextView toolbarTitle;
    protected User user;
    private int mSignInProgress;

    public BaseActivity() {
        this.authInProgress = false;
        this.mGoogleFitClient = null;
        this.hasFitbit = false;
        this.hasJawbone = false;
        this.hasWearDevice = false;
        this.hasMisfit = false;
        this.hasMoves = false;
        this.connectFit = false;
        this.dtf = DateTimeFormat.forPattern("yyyy-MMM-dd");
        this.isLoggedIn = false;
        this.titleEnabled = true;
        statusCallback = new com.facebook.Session.StatusCallback() {
            @Override
            public void call(Session session, SessionState sessionState, Exception e) {
                if (sessionState.isOpened()) {
                    BaseActivity.this.facebookSession = session;
                }
            }
        };
    }

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
        mGooglePlusClient = (new GoogleApiClient.Builder(getApplicationContext()))
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    public void onConnected(Bundle bundle) {
                        BaseActivity.this.mSignInClicked = false;
                        if (BaseActivity.this.mGooglePlusClient != null) {
                            BaseActivity.this.user = BaseActivity.this.getProfileInformation();
                        }
                    }

                    public void onConnectionSuspended(int i) {
                        BaseActivity.this.mGooglePlusClient.connect();
                    }

                }).addOnConnectionFailedListener(new com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener() {
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        BaseActivity.this.mConnectionResult = connectionResult;
                        if (!connectionResult.hasResolution()) {
                            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), BaseActivity.this, BaseActivity.STATE_DEFAULT).show();
                        } else if (!BaseActivity.this.mIntentInProgress && BaseActivity.this.mSignInClicked) {
                            BaseActivity.this.resolveSignInError();
                        }
                    }
                }).addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN).addScope(Plus.SCOPE_PLUS_PROFILE).build();
    }

    public void buildFitnessClient(Button button, Button button1) {
        fitConnectButton = button;
        fitDisconnectButton = button1;
        connectFit = true;
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
                String personGooglePlusProfile = mCurrentPerson.getUrl();
                String personEmail = Plus.AccountApi.getAccountName(this.mGooglePlusClient);
                String[] fullName = personName.split(" ");
                this.user.setFirstName(fullName[STATE_DEFAULT]);
                try {
                    this.user.setLastName(fullName[fullName.length - 1]);
                } catch (ArrayIndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                }
                this.user.setEmail(personEmail);
                this.user.setUsername(personEmail);
                this.user.setAvatarUrl(personPhotoUrl.substring(STATE_DEFAULT, personPhotoUrl.length() - 2) + PROFILE_PIC_SIZE);
                if (this instanceof StartActivity) {
                    ((StartActivity) this).getSignUpFragment().executeLogin(this.user);
                }
            }
        } catch (Exception ex2) {
            ex2.printStackTrace();
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
            this.authInProgress = false;
            if (resultCode != -1) {
                PrefManager.with(this).save(User.HAS_GOOGLEFIT, false);
            } else if (!(this.mGoogleFitClient.isConnecting() || this.mGoogleFitClient.isConnected())) {
                this.mGoogleFitClient.connect();
                PrefManager.with(this).save(User.HAS_GOOGLEFIT, true);
                this.hasWearDevice = true;
                if (this.fitConnectButton != null) {
                    setConnected(this.fitConnectButton, this.fitDisconnectButton);
                }
                try {
                    ParseObject userObject = ParseQuery.getQuery(User.CLASS).whereEqualTo(User.OBJECT_ID, PrefManager.with(this).getString(User.OBJECT_ID, BuildConfig.FLAVOR)).getFirst();
                    userObject.put(User.HAS_GOOGLEFIT, Boolean.valueOf(true));
                    userObject.save();
                } catch (ParseException ex) {
                    Log.d(LOG_TAG, ex.getMessage());
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
        this.containerLayout = (FrameLayout) findViewById(R.id.container);
        this.deviceOrder = new HashMap();
        PrefManager.with(this).save(APP_VERSION, APP_VERSION_NUMBER);
        try {
            this.toolbar = (Toolbar) findViewById(R.id.toolbar_drawer);
        } catch (Exception ex) {
            Log.d(LOG_TAG, ex.getMessage());
        }
        Tracker t = ((FitnessApp) getApplication()).getTracker();
        t.setScreenName(getClass().getSimpleName());
        t.setAppVersion(APP_VERSION_NUMBER);
        t.send(new HitBuilders.ScreenViewBuilder().build());
        this.hasFitbit = PrefManager.with(this).getBoolean(User.HAS_FITBIT, false);
        this.hasWearDevice = PrefManager.with(this).getBoolean(User.HAS_GOOGLEFIT, false);
        this.hasJawbone = PrefManager.with(this).getBoolean(User.HAS_JAWBONE, false);
        this.hasMisfit = PrefManager.with(this).getBoolean(User.HAS_MISFIT, false);
        this.hasMoves = PrefManager.with(this).getBoolean(User.HAS_MOVES, false);
        if (this.toolbar == null) {
            try {
                this.toolbar = (Toolbar) findViewById(R.id.toolbar_spinner);
                this.toolbarSpinner = (Spinner) this.toolbar.findViewById(R.id.spinner_toolbar);
                this.titleEnabled = false;
                ArrayList<SpinnerItem> spinnerItems = new ArrayList();
                ArrayList<String> spinnerTitles = new ArrayList();
                ArrayList<Integer> icons = new ArrayList();
                spinnerTitles.add(Devices.FITHUB);
                icons.add(Integer.valueOf(R.mipmap.ic_launcher));
                this.deviceOrder.put(Integer.valueOf(STATE_DEFAULT), Integer.valueOf(STATE_DEFAULT));
                int counter = STATE_DEFAULT + STATE_SIGN_IN;
                if (this.hasWearDevice) {
                    spinnerTitles.add(Devices.GOOGLE_FIT);
                    icons.add(Integer.valueOf(R.drawable.ic_fit));
                    this.deviceOrder.put(Integer.valueOf(counter), Integer.valueOf(STATE_SIGN_IN));
                    counter += STATE_SIGN_IN;
                }
                if (this.hasFitbit) {
                    spinnerTitles.add(Devices.FITBIT);
                    icons.add(Integer.valueOf(R.mipmap.ic_fitbit_logo));
                    this.deviceOrder.put(Integer.valueOf(counter), Integer.valueOf(STATE_IN_PROGRESS));
                    counter += STATE_SIGN_IN;
                }
                if (this.hasJawbone) {
                    spinnerTitles.add(Devices.JAWBONE);
                    icons.add(Integer.valueOf(R.mipmap.ic_jawbone_up));
                    this.deviceOrder.put(Integer.valueOf(counter), Integer.valueOf(JAWBONE));
                    counter += STATE_SIGN_IN;
                }
                if (this.hasMisfit) {
                    spinnerTitles.add(Devices.MISFIT);
                    icons.add(Integer.valueOf(R.drawable.ic_logo_misfit));
                    this.deviceOrder.put(Integer.valueOf(counter), Integer.valueOf(MISFIT));
                    counter += STATE_SIGN_IN;
                }
                if (this.hasMoves) {
                    spinnerTitles.add(Devices.MOVES);
                    icons.add(Integer.valueOf(R.mipmap.ic_moves_logo_s));
                    this.deviceOrder.put(Integer.valueOf(counter), Integer.valueOf(MOVES));
                }
                for (int i = STATE_DEFAULT; i < spinnerTitles.size(); i += STATE_SIGN_IN) {
                    spinnerItems.add(new SpinnerItem((String) spinnerTitles.get(i), ((Integer) icons.get(i)).intValue()));
                }
                this.toolbarSpinner.setAdapter(new DashboardSpinnerAdapter(this, spinnerItems));
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }
        }
        if (this.toolbar == null) {
            this.toolbar = (Toolbar) findViewById(R.id.toolbar);
            this.toolbarTitle = (TextView) this.toolbar.findViewById(R.id.text_title);
            this.toolbarLogo = (ImageView) this.toolbar.findViewById(R.id.logo_image);
            this.toolbarLogo.setImageResource(R.mipmap.ic_launcher);
        }
        if (this.toolbar != null) {
            setSupportActionBar(this.toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
                getSupportActionBar().setDisplayShowTitleEnabled(this.titleEnabled);
            }
        }
        if (savedInstanceState != null) {
            this.authInProgress = savedInstanceState.getBoolean(AUTH_PENDING);
        }
        if ((this instanceof StartActivity) || PrefManager.with(this).getBoolean(PreferenceUtils.GPLUS_SIGNED_IN, false)) {
            buildGoogleApiClient();
        }
        if (this.hasWearDevice) {
            buildFitnessClient(null, null);
            this.hasWearDevice = true;
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
        bundle.putBoolean("auth_state_pending", authInProgress);
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
        if (hasWearDevice && mGoogleFitClient != null) {
            mGoogleFitClient.connect();
        }
    }

    public void onStop() {
        super.onStop();
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
        if (mGooglePlusClient != null && mGooglePlusClient.isConnected()) {
            mGooglePlusClient.disconnect();
        }
        if (hasWearDevice && mGoogleFitClient != null && mGoogleFitClient.isConnected()) {
            mGoogleFitClient.disconnect();
        }
    }

    public void resolveSignInError() {
        if (mConnectionResult == null || !mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGooglePlusClient.connect();
            }
        }
    }

    public void revokeGoogleFitAccess() {
        if (mGoogleFitClient != null && mGoogleFitClient.isConnected()) {
            Fitness.ConfigApi.disableFit(mGoogleFitClient).setResultCallback(new ResultCallback() {
                @Override
                public void onResult(Result result) {
                    Status status = (Status) result;
                    if (status.isSuccess()) {
                        Log.i(BaseActivity.LOG_TAG, "Google Fit disabled");
                        mGoogleFitClient = null;
                        return;
                    } else {
                        Log.e(BaseActivity.LOG_TAG, (new StringBuilder()).append("Google Fit wasn't disabled ").append(status).toString());
                        return;
                    }
                }
            });
        }
    }

    public void revokeGplusAccess() {
        if (mGooglePlusClient != null && mGooglePlusClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGooglePlusClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGooglePlusClient)
                    .setResultCallback(new ResultCallback() {
                        @Override
                        public void onResult(Result result) {
                            Log.i(BaseActivity.LOG_TAG, "User access revoked");
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
                        if (hasWearDevice) {
                            mGoogleFitClient.connect();
                        }
                    }

                    public void onConnectionSuspended(int i) {
                        // If your connection to the sensor gets lost at some point,
                        // you'll be able to determine the reason and react to it here.
                        if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST) {
                            Log.i(BaseActivity.LOG_TAG, "Connection lost.  Cause: Network Lost.");
                        } else if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
                            Log.i(BaseActivity.LOG_TAG, "Connection lost.  Reason: Service Disconnected");
                        }
                    }
                }).addOnConnectionFailedListener(new com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener() {
                    public void onConnectionFailed(ConnectionResult connectionresult) {
                        if (!authInProgress) {
                            try {
                                Log.i(BaseActivity.LOG_TAG, "Attempting to resolve failed connection");
                                authInProgress = true;
                                connectionresult.startResolutionForResult(BaseActivity.this,
                                        REQUEST_OAUTH);
                            } catch (IntentSender.SendIntentException e) {
                                Crashlytics.logException(e);
                                Log.e(BaseActivity.LOG_TAG, "Exception while starting resolution activity", e);
                            }
                        }
                    }
                }).build();
    }
}
