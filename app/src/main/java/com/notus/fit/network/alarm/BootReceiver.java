package com.notus.fit.network.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

// Referenced classes of package com.notus.fit.network.alarm:
//            AlarmReceiver, ResetNotificationsReceiver

public class BootReceiver extends BroadcastReceiver {

    public BootReceiver() {
    }

    public void onReceive(Context context, Intent intent) {
        AlarmReceiver alarmreceiver = new AlarmReceiver();
        ResetNotificationsReceiver resetnotificationsreceiver = new ResetNotificationsReceiver();
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            alarmreceiver.setAlarm(context);
            resetnotificationsreceiver.setAlarm(context);
        }
    }
}
