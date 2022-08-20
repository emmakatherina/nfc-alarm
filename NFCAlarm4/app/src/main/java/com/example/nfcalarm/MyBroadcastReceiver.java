package com.example.nfcalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;

public class MyBroadcastReceiver extends BroadcastReceiver {
    public static Vibrator vibrator = null;
    public static Ringtone ringtone = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        // we will use vibrator first
        // TODO prolong vibration
        vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        vibrator.vibrate(4000);

        Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

        // setting default ringtone
        ringtone = RingtoneManager.getRingtone(context, alarmUri);

        // play ringtone
        ringtone.play();
    }

    public static void makeQuiet() {
        if (vibrator != null && ringtone != null) {
            vibrator.cancel();
            ringtone.stop();
        }
    }
}