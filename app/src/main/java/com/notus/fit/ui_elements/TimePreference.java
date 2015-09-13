package com.notus.fit.ui_elements;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

public class TimePreference extends DialogPreference {

    private int lastHour;
    private int lastMinute;
    private TimePicker picker;

    public TimePreference(Context context, AttributeSet attributeset) {
        super(context, attributeset);
        lastHour = 0;
        lastMinute = 0;
        picker = null;
        setPositiveButtonText("Set");
        setNegativeButtonText("Cancel");
    }

    public static int getHour(String s) {
        String as[] = s.split(":");
        s = as[0];
        String s1 = as[1].split(" ")[1];
        int j = Integer.parseInt(s);
        int i = j;
        if (s1.equals("PM")) {
            i = j + 12;
        }
        return i;
    }

    public static int getMinute(String s) {
        return Integer.parseInt(s.split(":")[1].split(" ")[0]);
    }

    protected void onBindDialogView(@NonNull View view) {
        super.onBindDialogView(view);
        picker.setCurrentHour(lastHour);
        picker.setCurrentMinute(lastMinute);
    }

    protected View onCreateDialogView() {
        picker = new TimePicker(getContext());
        return picker;
    }

    protected void onDialogClosed(boolean flag) {
        super.onDialogClosed(flag);
        if (flag) {
            lastHour = picker.getCurrentHour();
            lastMinute = picker.getCurrentMinute();
            String s = "AM";
            if (lastHour > 12) {
                lastHour = lastHour - 12;
                s = "PM";
            }
            s = (new StringBuilder()).append(String.format("%02d", new Object[]{
                    lastHour
            })).append(":").append(String.format("%02d", new Object[]{
                    lastMinute
            })).append(" ").append(s).toString();
            if (callChangeListener(s)) {
                persistString(s);
            }
        }
    }

    protected Object onGetDefaultValue(TypedArray typedarray, int i) {
        return typedarray.getString(i);
    }

    protected void onSetInitialValue(boolean flag, Object obj) {
        if (flag) {
            if (obj == null) {
                obj = getPersistedString("00:00 PM");
            } else {
                obj = getPersistedString(obj.toString());
            }
        } else {
            obj = obj.toString();
        }
        lastHour = getHour(((String) (obj)));
        lastMinute = getMinute(((String) (obj)));
    }
}
