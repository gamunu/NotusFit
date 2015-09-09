package com.notus.fit.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import java.util.Map;
import java.util.Set;

/**
 * Created by VBALAUD on 9/3/2015.
 */
public class PrefManager {
    static Editor editor;
    static SharedPreferences preferences;
    static PrefManager singleton;

    static {
        singleton = null;
    }

    PrefManager(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
    }

    PrefManager(Context context, String name, int mode) {
        preferences = context.getSharedPreferences(name, mode);
        editor = preferences.edit();
    }

    public static PrefManager with(Context context) {
        if (singleton == null) {
            singleton = new Builder(context, null, -1).build();
        }
        return singleton;
    }

    public static PrefManager with(Context context, String name, int mode) {
        if (singleton == null) {
            singleton = new Builder(context, name, mode).build();
        }
        return singleton;
    }

    public void save(String key, boolean value) {
        editor.putBoolean(key, value).apply();
    }

    public void save(String key, String value) {
        editor.putString(key, value).apply();
    }

    public void save(String key, int value) {
        editor.putInt(key, value).apply();
    }

    public void save(String key, float value) {
        editor.putFloat(key, value).apply();
    }

    public void save(String key, long value) {
        editor.putLong(key, value).apply();
    }

    public void save(String key, Set<String> value) {
        editor.putStringSet(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return preferences.getBoolean(key, defValue);
    }

    public String getString(String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return preferences.getFloat(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return preferences.getLong(key, defValue);
    }

    public Set<String> getStringSet(String key, Set<String> defValue) {
        return preferences.getStringSet(key, defValue);
    }

    public Map<String, ?> getAll() {
        return preferences.getAll();
    }

    public void remove(String key) {
        editor.remove(key).apply();
    }

    public void clear() {
        editor.clear().apply();
    }

    private static class Builder {
        private final Context context;
        private final int mode;
        private final String name;

        public Builder(Context context, String name, int mode) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            this.context = context.getApplicationContext();
            this.name = name;
            this.mode = mode;
        }

        public PrefManager build() {
            if (this.mode == -1 || this.name == null) {
                return new PrefManager(this.context);
            }
            return new PrefManager(this.context, this.name, this.mode);
        }
    }
}
