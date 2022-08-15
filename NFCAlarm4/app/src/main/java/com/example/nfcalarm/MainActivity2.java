package com.example.nfcalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    Button scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        scan = findViewById(R.id.scanButton);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { stopAlert(); }
        });
    }

    public void stopAlert() {
        MainActivity.stopAlert(this);

        Intent switchIntent = new Intent(MainActivity2.this, MainActivity.class);
        startActivity(switchIntent);
    }
}