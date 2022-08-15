package com.example.nfcalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button start;
    Button scan;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.startButton);
        scan = findViewById(R.id.scanButton);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAlert();
            }
        });

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { stopAlert(); }
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
    }

    // disable muting the alarm!

    public void stopAlert() {
        alarmManager.cancel(this.pendingIntent);
        MyBroadcastReceiver.makeQuiet();
        Toast.makeText(this, "Alarm stopped",Toast.LENGTH_LONG).show();
    }

}