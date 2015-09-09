package com.notus.fit.utils;

import android.content.Context;
import android.text.format.Time;

import com.facebook.internal.AnalyticsEvents;
import com.notus.fit.R;
import com.notus.fit.models.api_models.User;
import com.parse.ParseException;

import org.joda.time.LocalDateTime;

import java.text.SimpleDateFormat;

/**
 * Created by VBALAUD on 9/8/2015.
 */
public class WeatherUtils {
    public static String getDayName(Context context, long dateInMillis) {
        Time t = new Time();
        t.setToNow();
        int julianDay = Time.getJulianDay(dateInMillis, t.gmtoff);
        int currentJulianDay = Time.getJulianDay(System.currentTimeMillis(), t.gmtoff);
        if (julianDay == currentJulianDay) {
            return context.getString(R.string.today);
        }
        if (julianDay == currentJulianDay + 1) {
            return context.getString(R.string.tomorrow);
        }
        new Time().setToNow();
        return new SimpleDateFormat("EEEE").format(Long.valueOf(dateInMillis));
    }

    public static String getFormattedWind(Context context, float windSpeed, float degrees) {
        int windFormat;
        if (isMetric(context)) {
            windFormat = R.string.format_wind_kmh;
        } else {
            windFormat = R.string.format_wind_mph;
            windSpeed *= 0.6213712f;
        }
        String direction = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        if (((double) degrees) >= 337.5d || ((double) degrees) < 22.5d) {
            direction = "N";
        } else if (((double) degrees) >= 22.5d && ((double) degrees) < 67.5d) {
            direction = "NE";
        } else if (((double) degrees) >= 67.5d && ((double) degrees) < 112.5d) {
            direction = "E";
        } else if (((double) degrees) >= 112.5d && ((double) degrees) < 157.5d) {
            direction = "SE";
        } else if (((double) degrees) >= 157.5d && ((double) degrees) < 202.5d) {
            direction = "S";
        } else if (((double) degrees) >= 202.5d && ((double) degrees) < 247.5d) {
            direction = "SW";
        } else if (((double) degrees) >= 247.5d && ((double) degrees) < 292.5d) {
            direction = "W";
        } else if (((double) degrees) >= 292.5d && ((double) degrees) < 337.5d) {
            direction = "NW";
        }
        return String.format(context.getString(windFormat), new Object[]{Float.valueOf(windSpeed), direction});
    }

    public static int getIconResourceForWeatherCondition(int weatherId) {
        if (weatherId >= ParseException.USERNAME_MISSING && weatherId <= 232) {
            return R.drawable.ic_storm;
        }
        if (weatherId >= 300 && weatherId <= 321) {
            return R.drawable.ic_light_rain;
        }
        if (weatherId >= 500 && weatherId <= 504) {
            return R.drawable.ic_rain;
        }
        if (weatherId == 511) {
            return R.drawable.ic_snow;
        }
        if (weatherId >= 520 && weatherId <= 531) {
            return R.drawable.ic_rain;
        }
        if (weatherId >= 600 && weatherId <= 622) {
            return R.drawable.ic_snow;
        }
        if (weatherId >= 701 && weatherId <= 761) {
            return R.drawable.ic_fog;
        }
        if (weatherId == 761 || weatherId == 781) {
            return R.drawable.ic_storm;
        }
        if (weatherId == 800) {
            return R.drawable.ic_clear;
        }
        if (weatherId == 801) {
            return R.drawable.ic_light_clouds;
        }
        if (weatherId < 802 || weatherId > 804) {
            return -1;
        }
        return R.drawable.ic_cloudy;
    }

    public static boolean isMetric(Context context) {
        return PrefManager.with(context).getString(User.UNITS, context.getString(R.string.pref_units_imperial)).equals(context.getString(R.string.pref_units_metric));
    }

    public static int getArtResourceForWeatherCondition(int weatherId) {
        if (weatherId >= ParseException.USERNAME_MISSING && weatherId <= 232) {
            return R.drawable.art_storm;
        }
        if (weatherId >= 300 && weatherId <= 321) {
            return R.drawable.art_light_rain;
        }
        if (weatherId >= 500 && weatherId <= 504) {
            return R.drawable.art_rain;
        }
        if (weatherId == 511) {
            return R.drawable.art_snow;
        }
        if (weatherId >= 520 && weatherId <= 531) {
            return R.drawable.art_rain;
        }
        if (weatherId >= 600 && weatherId <= 622) {
            return R.drawable.art_snow;
        }
        if (weatherId >= 701 && weatherId <= 761) {
            return R.drawable.art_fog;
        }
        if (weatherId == 761 || weatherId == 781) {
            return R.drawable.art_storm;
        }
        if (weatherId == 800) {
            LocalDateTime now = LocalDateTime.now();
            if (now.getHourOfDay() > 18 || now.getHourOfDay() < 7) {
                return R.drawable.ic_moon;
            }
            return R.drawable.art_clear;
        } else if (weatherId == 801) {
            return R.drawable.art_light_clouds;
        } else {
            if (weatherId < 802 || weatherId > 804) {
                return -1;
            }
            return R.drawable.art_clouds;
        }
    }
}