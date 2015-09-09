package com.notus.fit.fragments;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

import com.github.machinarius.preferencefragment.PreferenceFragment;
import com.notus.fit.BuildConfig;
import com.notus.fit.R;
import com.notus.fit.utils.CustomUtils;

public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
    private static String LOG_TAG = SettingsFragment.class.getSimpleName();
    private boolean mBindingPreference;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fragment_settings);
        bindPreferenceSummaryToValue(findPreference(getResources().getString(R.string.steps_goal)));
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        super.onPreferenceTreeClick(preferenceScreen, preference);
        return false;
    }

    private void bindPreferenceSummaryToValue(Preference preference) {
        this.mBindingPreference = true;
        preference.setOnPreferenceChangeListener(this);
        onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(), BuildConfig.FLAVOR));
        this.mBindingPreference = false;
    }

    public boolean onPreferenceChange(Preference preference, Object value) {
        String stringValue = value.toString();
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            if (preference instanceof EditTextPreference) {
                CustomUtils.hideSoftKeyboard(getActivity(), ((EditTextPreference) preference).getEditText());
            }
            if (preference.getKey().equals(getString(R.string.steps_goal))) {
                preference.setSummary(stringValue + " steps.");
            } else {
                preference.setSummary(stringValue);
            }
        }
        return true;
    }

}
