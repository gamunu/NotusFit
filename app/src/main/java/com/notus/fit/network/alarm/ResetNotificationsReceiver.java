package com.notus.fit.network.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.notus.fit.network.services.ResetAlarmService;

import java.util.Calendar;

public class ResetNotificationsReceiver extends WakefulBroadcastReceiver {
    private PendingIntent alarmIntent;
    private AlarmManager alarmMgr;

    public void onReceive(Context context, Intent intent) {
        WakefulBroadcastReceiver.startWakefulService(context, new Intent(context, ResetAlarmService.class));
    }

    public void setAlarm(Context context) {
        this.alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        this.alarmIntent = PendingIntent.getBroadcast(context, 1, new Intent(context, ResetNotificationsReceiver.class), 0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 1);
        calendar.set(Calendar.SECOND, 0);
        this.alarmMgr.setInexactRepeating(0, calendar.getTimeInMillis(), 86400000, this.alarmIntent);
        context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, BootReceiver.class), 1, 1);
    }
}
