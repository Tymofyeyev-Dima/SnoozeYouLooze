package com.example.abhi.snoozeyoulose;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.view.View;

/**
 * Created by Abhi on 12/11/2017.
 */

public class AlarmReceiver extends WakefulBroadcastReceiver {

    private static Ringtone ringtone;

    //Getter for Ringtone
    public static Ringtone getRingtone(){
        return ringtone;
    }

    @Override
    public void onReceive(final Context context, Intent intent){
        MainAlarmActivity inst = MainAlarmActivity.instance();
        inst.getSnooze().setVisibility(View.VISIBLE);
        inst.setAlarmText("Alarm! Wake up! Wake up!");

        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();

        //this will send a notification message
        ComponentName comp = new ComponentName(context.getPackageName(),
                AlarmService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
//
//        try {
//            Thread.sleep(10000);
//            ringtone.stop();
//        } catch (InterruptedException e){
//            e.printStackTrace();
//        }

    }
}
