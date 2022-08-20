package com.example.nfcalarm;

import static android.Manifest.permission.NFC;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.SCHEDULE_EXACT_ALARM;
import static android.Manifest.permission.VIBRATE;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    Button start;
    Button scan;
    Button check;
    Button request;
    private static final int PERMISSION_REQUEST_CODE = 200;
    public static PendingIntent pendingIntent;
    public static AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.startButton);
        scan = findViewById(R.id.scanButton);
        check = findViewById(R.id.check_permission);
        request = findViewById(R.id.request_permission);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Listen to buttons
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAlert();
            }
        });

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopAlert(MainActivity.this);
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request();
            }
        });
    }

    public void startAlert() {
        EditText text = findViewById(R.id.time);
        int i = Integer.parseInt(text.getText().toString());

        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        this.pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 234324243, intent, 0);

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (i * 1000), pendingIntent);
        Toast.makeText(this, "Alarm set in " + i + " seconds", Toast.LENGTH_LONG).show();

        Intent switchIntent = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(switchIntent);
    }

    // disable muting the alarm!

    public static void stopAlert(Context context) {
        alarmManager.cancel(pendingIntent);
        MyBroadcastReceiver.makeQuiet();
        Toast.makeText(context, "Alarm stopped",Toast.LENGTH_LONG).show();
    }

    public void check() {
        if (checkPermission()) {
            Toast.makeText(this, "Permission already granted.",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Please request permission.",Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkPermission() {
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), VIBRATE);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), SCHEDULE_EXACT_ALARM);
        int result3 = ContextCompat.checkSelfPermission(getApplicationContext(), NFC);

        return (result1 == PackageManager.PERMISSION_GRANTED) & (result2 == PackageManager.PERMISSION_GRANTED)
                & (result3 == PackageManager.PERMISSION_GRANTED);
    }

    public void request() {
        if (true) { // (!checkPermission()) {
            requestPermission();
        } else {
            Toast.makeText(this, "Permission already granted.",Toast.LENGTH_LONG).show();
        }
    }

    private void requestPermission() {
        // ActivityCompat.requestPermissions(this, new String[]{CAMERA}, PERMISSION_REQUEST_CODE);
        ActivityCompat.requestPermissions(this, new String[]{SCHEDULE_EXACT_ALARM}, PERMISSION_REQUEST_CODE);
        ActivityCompat.requestPermissions(this, new String[]{NFC}, PERMISSION_REQUEST_CODE);
    }

}