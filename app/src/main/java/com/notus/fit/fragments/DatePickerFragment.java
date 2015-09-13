package com.notus.fit.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener {
    int id;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DATE);
        if (getArguments() != null) {
            try {
                Bundle b = getArguments();
                year = b.getInt("year");
                month = b.getInt("month");
                day = b.getInt("day");
            } catch (NullPointerException ex) {
                Log.e(DatePickerFragment.class.getName(), ex.getMessage(), ex);
            }
        }
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
    }
}
