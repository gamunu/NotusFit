package com.notus.fit.gameUtils;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.IntentSender.SendIntentException;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.notus.fit.R;

/**
 * Created by VBALAUD on 9/3/2015.
 */
public class BaseGameUtils {
    public static void showAlert(Activity activity, String message) {
        new Builder(activity).setMessage(message).setNeutralButton(android.R.string.ok, null).create().show();
    }

    public static boolean resolveConnectionFailure(Activity activity, GoogleApiClient client, ConnectionResult result, int requestCode, String fallbackErrorMessage) {
        if (result.hasResolution()) {
            try {
                result.startResolutionForResult(activity, requestCode);
                return true;
            } catch (SendIntentException e) {
                client.connect();
                return false;
            }
        }
        Dialog dialog = GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), activity, requestCode);
        if (dialog != null) {
            dialog.show();
            return false;
        }
        showAlert(activity, fallbackErrorMessage);
        return false;
    }

    public static boolean verifySampleSetup(Activity activity, int... resIds) {
        StringBuilder problems = new StringBuilder();
        boolean problemFound = false;
        problems.append("The following set up problems were found:\n\n");
        if (activity.getPackageName().startsWith("com.google.example.games")) {
            problemFound = true;
            problems.append("- Package name cannot be com.google.*. You need to change the sample's package name to your own package.").append("\n");
        }
        for (int i : resIds) {
            if (activity.getString(i).toLowerCase().contains("replaceme")) {
                problemFound = true;
                problems.append("- You must replace all placeholder IDs in the ids.xml file by your project's IDs.").append("\n");
                break;
            }
        }
        if (!problemFound) {
            return true;
        }
        problems.append("\n\nThese problems may prevent the app from working properly.");
        showAlert(activity, problems.toString());
        return false;
    }

    public static void showActivityResultError(Activity activity, int requestCode, int actResp, int errorDescription) {
        if (activity == null) {
            Log.e("BaseGameUtils", "*** No Activity. Can't show failure dialog!");
            return;
        }
        Dialog errorDialog;
        switch (actResp) {
            case 10002:
                errorDialog = makeSimpleDialog(activity, activity.getString(R.string.sign_in_failed));
                break;
            case 10003:
                errorDialog = makeSimpleDialog(activity, activity.getString(R.string.license_failed));
                break;
            case 10004:
                errorDialog = makeSimpleDialog(activity, activity.getString(R.string.app_misconfigured));
                break;
            default:
                errorDialog = GooglePlayServicesUtil.getErrorDialog(GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity), activity, requestCode, null);
                if (errorDialog == null) {
                    Log.e("BaseGamesUtils", "No standard error dialog available. Making fallback dialog.");
                    errorDialog = makeSimpleDialog(activity, activity.getString(errorDescription));
                    break;
                }
                break;
        }
        errorDialog.show();
    }

    public static Dialog makeSimpleDialog(Activity activity, String text) {
        return new Builder(activity).setMessage(text).setNeutralButton(android.R.string.ok, null).create();
    }

    public static Dialog makeSimpleDialog(Activity activity, String title, String text) {
        return new Builder(activity).setTitle(title).setMessage(text).setNeutralButton(android.R.string.ok, null).create();
    }
}