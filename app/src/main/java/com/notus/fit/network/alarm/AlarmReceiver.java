package com.notus.fit.network.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.notus.fit.network.services.ScheduleService;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    private PendingIntent alarmIntent;
    private AlarmManager alarmMgr;

    public void onReceive(Context context, Intent intent) {
        WakefulBroadcastReceiver.startWakefulService(context, new Intent(context, ScheduleService.class));
    }

    public void setAlarm(Context context) {
        this.alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        this.alarmIntent = PendingIntent.getBroadcast(context, 0, new Intent(context, AlarmReceiver.class), 0);
        this.alarmMgr.setInexactRepeating(3, 0, 1800000, this.alarmIntent);
        context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, BootReceiver.class), 1, 1);
    }

    public void cancelAlarm(Context context) {
        if (this.alarmMgr != null) {
            this.alarmMgr.cancel(this.alarmIntent);
        }
        context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, BootReceiver.class), 2, 1);
    }
}
