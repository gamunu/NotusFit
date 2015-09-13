package com.notus.fit.gameUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;


/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/3/2015 8:00 PM
 */
public abstract class BaseGameActivity extends ActionBarActivity implements GameHelper.GameHelperListener {
    public static final int CLIENT_ALL = 15;
    public static final int CLIENT_APPSTATE = 4;
    public static final int CLIENT_GAMES = 1;
    public static final int CLIENT_PLUS = 2;
    public static final int REQUEST_ACHIEVEMENTS = 4991;
    public static final int REQUEST_LEADERBOARD = 4990;
    private static final String TAG = "BaseGameActivity";
    protected boolean mDebugLog;
    protected GameHelper mHelper;
    protected int mRequestedClients;

    protected BaseGameActivity() {
        this.mRequestedClients = CLIENT_GAMES;
        this.mDebugLog = false;
    }

    protected BaseGameActivity(int requestedClients) {
        this.mRequestedClients = CLIENT_GAMES;
        this.mDebugLog = false;
        setRequestedClients(requestedClients);
    }

    protected void setRequestedClients(int requestedClients) {
        this.mRequestedClients = requestedClients;
    }

    public GameHelper getGameHelper() {
        if (this.mHelper == null) {
            this.mHelper = new GameHelper(this, this.mRequestedClients);
            this.mHelper.enableDebugLog(this.mDebugLog);
        }
        return this.mHelper;
    }

    protected void onCreate(Bundle b) {
        super.onCreate(b);
        if (this.mHelper == null) {
            getGameHelper();
        }
        this.mHelper.setup(this);
    }

    protected void onStart() {
        super.onStart();
        this.mHelper.onStart(this);
    }

    public void onStop() {
        super.onStop();
        this.mHelper.onStop();
    }

    public void onActivityResult(int request, int response, Intent data) {
        super.onActivityResult(request, response, data);
        this.mHelper.onActivityResult(request, response, data);
    }

    public GoogleApiClient getApiClient() {
        return this.mHelper.getApiClient();
    }

    protected boolean isSignedIn() {
        return this.mHelper.isSignedIn();
    }

    protected void beginUserInitiatedSignIn() {
        this.mHelper.beginUserInitiatedSignIn();
    }

    public void signOut() {
        this.mHelper.signOut();
    }

    protected void showAlert(String message) {
        this.mHelper.makeSimpleDialog(message).show();
    }

    protected void showAlert(String title, String message) {
        this.mHelper.makeSimpleDialog(title, message).show();
    }

    protected void enableDebugLog(boolean enabled) {
        this.mDebugLog = true;
        if (this.mHelper != null) {
            this.mHelper.enableDebugLog(enabled);
        }
    }

    @Deprecated
    protected void enableDebugLog(boolean enabled, String tag) {
        Log.w(TAG, "BaseGameActivity.enabledDebugLog(bool,String) is deprecated. Use enableDebugLog(boolean)");
        enableDebugLog(enabled);
    }

    protected String getInvitationId() {
        return this.mHelper.getInvitationId();
    }

    protected void reconnectClient() {
        this.mHelper.reconnectClient();
    }

    protected boolean hasSignInError() {
        return this.mHelper.hasSignInError();
    }

    protected GameHelper.SignInFailureReason getSignInError() {
        return this.mHelper.getSignInError();
    }
}
