package com.jawbone.upplatformsdk.oauth;

import android.net.Uri.Builder;
import android.util.Log;

import com.facebook.internal.ServerProtocol;
import com.jawbone.upplatformsdk.utils.UpPlatformSdkConstants.UpPlatformAuthScope;

import org.joda.time.DateTimeConstants;
import org.joda.time.MutableDateTime;
import org.joda.time.TimeOfDay;
import org.joda.time.YearMonthDay;
import org.joda.time.chrono.IslamicChronology;

import java.util.List;

import it.neokree.materialtabs.R;

public class OauthUtils {
    private static final String TAG = OauthUtils.class.getSimpleName();

    static {

    }

    public static Builder setOauthParameters(String clientId, String callbackUrl, List<UpPlatformAuthScope> scope) {
        Builder builder = setBaseParameters();
        builder.appendPath("auth");
        builder.appendPath("oauth2");
        builder.appendPath("auth");
        builder.appendQueryParameter(ServerProtocol.DIALOG_PARAM_RESPONSE_TYPE, "code");
        builder.appendQueryParameter(ServerProtocol.DIALOG_PARAM_CLIENT_ID, clientId);
        builder = setOauthScopeParameters(scope, builder);
        builder.appendQueryParameter(ServerProtocol.DIALOG_PARAM_REDIRECT_URI, callbackUrl);
        return builder;
    }

    public static Builder setOauthScopeParameters(List<UpPlatformAuthScope> scopeArrayList, Builder builder) {
        StringBuilder scopeValues = new StringBuilder();
        for (UpPlatformAuthScope scope : scopeArrayList) {
            switch (Scope.UpPlatformSdkConstants[scope.ordinal()]) {
                case IslamicChronology.AH /*1*/:
                    scopeValues.append("basic_read ");
                    break;
                case YearMonthDay.DAY_OF_MONTH /*2*/:
                    scopeValues.append("extended_read ");
                    break;
                case TimeOfDay.MILLIS_OF_SECOND /*3*/:
                    scopeValues.append("location_read ");
                    break;
                case MutableDateTime.ROUND_HALF_CEILING /*4*/:
                    scopeValues.append("friends_read ");
                    break;
                case MutableDateTime.ROUND_HALF_EVEN /*5*/:
                    scopeValues.append("mood_read ");
                    break;
                case DateTimeConstants.SATURDAY /*6*/:
                    scopeValues.append("mood_write ");
                    break;
                case DateTimeConstants.SUNDAY /*7*/:
                    scopeValues.append("move_read ");
                    break;
                case DateTimeConstants.AUGUST /*8*/:
                    scopeValues.append("move_write ");
                    break;
                case DateTimeConstants.SEPTEMBER /*9*/:
                    scopeValues.append("sleep_read ");
                    break;
                case DateTimeConstants.OCTOBER /*10*/:
                    scopeValues.append("sleep_write ");
                    break;
                case DateTimeConstants.NOVEMBER /*11*/:
                    scopeValues.append("meal_read ");
                    break;
                case DateTimeConstants.DECEMBER /*12*/:
                    scopeValues.append("meal_write ");
                    break;
                case R.styleable.Toolbar_titleMarginEnd /*13*/:
                    scopeValues.append("weight_read ");
                    break;
                case R.styleable.Toolbar_titleMarginTop /*14*/:
                    scopeValues.append("weight_write ");
                    break;
                case R.styleable.Toolbar_titleMarginBottom /*15*/:
                    scopeValues.append("cardiac_read ");
                    break;
                case R.styleable.Toolbar_maxButtonHeight /*16*/:
                    scopeValues.append("cardiac_write ");
                    break;
                case R.styleable.Toolbar_collapseIcon /*17*/:
                    scopeValues.append("generic_event_read ");
                    break;
                case R.styleable.Toolbar_collapseContentDescription /*18*/:
                    scopeValues.append("generic_event_write ");
                    break;
                case R.styleable.Toolbar_navigationIcon /*19*/:
                    scopeValues.append("basic_read ");
                    scopeValues.append("extended_read ");
                    scopeValues.append("location_read ");
                    scopeValues.append("friends_read ");
                    scopeValues.append("mood_read ");
                    scopeValues.append("mood_write ");
                    scopeValues.append("move_read ");
                    scopeValues.append("move_write ");
                    scopeValues.append("sleep_read ");
                    scopeValues.append("sleep_write ");
                    scopeValues.append("meal_read ");
                    scopeValues.append("meal_write ");
                    scopeValues.append("weight_read ");
                    scopeValues.append("weight_write ");
                    scopeValues.append("cardiac_read ");
                    scopeValues.append("cardiac_write ");
                    scopeValues.append("generic_event_read ");
                    scopeValues.append("generic_event_write ");
                    break;
                default:
                    scopeValues = null;
                    Log.e(TAG, "unknown scope:" + scope + ", setting it to null");
                    break;
            }
        }
        if (scopeValues != null && scopeValues.length() > 0) {
            scopeValues.setLength(scopeValues.length() - 1);
            builder.appendQueryParameter(ServerProtocol.DIALOG_PARAM_SCOPE, scopeValues.toString());
        }
        return builder;
    }

    public static Builder setBaseParameters() {
        Builder builder = new Builder();
        builder.scheme("https");
        builder.authority("jawbone.com");
        return builder;
    }

    static class Scope {
        static final int[] UpPlatformSdkConstants;

        static {
            UpPlatformSdkConstants = new int[UpPlatformAuthScope.values().length];
            try {
                UpPlatformSdkConstants[UpPlatformAuthScope.BASIC_READ.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                UpPlatformSdkConstants[UpPlatformAuthScope.EXTENDED_READ.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                UpPlatformSdkConstants[UpPlatformAuthScope.LOCATION_READ.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                UpPlatformSdkConstants[UpPlatformAuthScope.FRIENDS_READ.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                UpPlatformSdkConstants[UpPlatformAuthScope.MOOD_READ.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                UpPlatformSdkConstants[UpPlatformAuthScope.MOOD_WRITE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                UpPlatformSdkConstants[UpPlatformAuthScope.MOVE_READ.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                UpPlatformSdkConstants[UpPlatformAuthScope.MOVE_WRITE.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                UpPlatformSdkConstants[UpPlatformAuthScope.SLEEP_READ.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                UpPlatformSdkConstants[UpPlatformAuthScope.SLEEP_WRITE.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                UpPlatformSdkConstants[UpPlatformAuthScope.MEAL_READ.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                UpPlatformSdkConstants[UpPlatformAuthScope.MEAL_WRITE.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                UpPlatformSdkConstants[UpPlatformAuthScope.WEIGHT_READ.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                UpPlatformSdkConstants[UpPlatformAuthScope.WEIGHT_WRITE.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                UpPlatformSdkConstants[UpPlatformAuthScope.CARDIAC_READ.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
            try {
                UpPlatformSdkConstants[UpPlatformAuthScope.CARDIAC_WRITE.ordinal()] = 16;
            } catch (NoSuchFieldError e16) {
            }
            try {
                UpPlatformSdkConstants[UpPlatformAuthScope.GENERIC_EVENT_READ.ordinal()] = 17;
            } catch (NoSuchFieldError e17) {
            }
            try {
                UpPlatformSdkConstants[UpPlatformAuthScope.GENERIC_EVENT_WRITE.ordinal()] = 18;
            } catch (NoSuchFieldError e18) {
            }
            try {
                UpPlatformSdkConstants[UpPlatformAuthScope.ALL.ordinal()] = 19;
            } catch (NoSuchFieldError e19) {
            }
        }
    }
}
