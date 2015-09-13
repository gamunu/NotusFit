package com.notus.fit.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.facebook.AppEventsConstants;
import com.notus.fit.BuildConfig;
import com.notus.fit.R;
import com.notus.fit.models.api_models.Name;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/3/2015 8:09 PM
 */
public class CustomUtils {
    public static String getMediumDate(Date date, Context context) {
        return DateFormat.getMediumDateFormat(context).format(date);
    }

    public static String getMediumDate(String str, Context context) {
        return getMediumDate(getDateFromString(str), context);
    }

    public static String formatDate(int year, int month, int day) {
        return (month + 1 > 9 ? BuildConfig.FLAVOR + (month + 1) : AppEventsConstants.EVENT_PARAM_VALUE_NO + (month + 1)) + "/" + day + "/" + year;
    }

    public static Date getDate(String dateString) {
        String[] components = dateString.split("/");
        return getDate(Integer.parseInt(components[2]), Integer.parseInt(components[0]) - 1, Integer.parseInt(components[1]));
    }

    public static Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    public static Date getDateFromString(String dateString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String formatLongDate(Context context, String dateString) {
        String[] dates = dateString.split("/");
        return context.getResources().getStringArray(R.array.months)[Integer.parseInt(dates[0]) - 1] + ", " + getFancyDayNumber(dates[1]) + " " + dates[2];
    }

    public static String getFancyDayNumber(String day) {
        if (Integer.parseInt(day) < 10) {
            if (day.endsWith(AppEventsConstants.EVENT_PARAM_VALUE_YES)) {
                return day + "st";
            }
            if (day.endsWith("2")) {
                return day + "nd";
            }
            if (day.endsWith("3")) {
                return day + "rd";
            }
        }
        return day + "th";
    }

    public static boolean isValidEmailAddress(String email) {
        return Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$").matcher(email).matches();
    }

    public static double roundDouble(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        long factor = (long) Math.pow(10.0d, (double) places);
        return ((double) Math.round(value * ((double) factor))) / ((double) factor);
    }

    public static java.sql.Date getSqlDate(Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static String getDateToSubmit(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).format(date);
    }

    public static String getDateNow() {
        Calendar c = Calendar.getInstance();
        return formatDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
    }

    public static void lockOrientation(Activity context) {
        context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
    }

    public static void addFragmentToContainer(Fragment fragment, int containerID, FragmentActivity activity) {
        activity.getSupportFragmentManager().beginTransaction().replace(containerID, fragment).addToBackStack(null).commit();
    }

    public static Name getFullName(String name) {
        Name n = new Name();
        if (!(name == null || name.isEmpty())) {
            String[] fullName = name.split(" ");
            n.setFirstName(fullName[0]);
            if (fullName.length > 1) {
                n.setLastName(fullName[fullName.length - 1]);
            }
        }
        return n;
    }

    public static boolean hasNumber(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static void hideSoftKeyboard(Context context, View view) {
        try {
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        } catch (Exception ex) {
            Log.w("HIDE KEYBOARD", ex.toString());
        }
    }
}
