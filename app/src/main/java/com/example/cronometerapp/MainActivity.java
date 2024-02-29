package com.example.cronometerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private boolean isCountdownRunning = false;
    private TextView textViewInfo;
    private Chronometer chronometer;
    private Button buttonStart;
    private Button buttonStop;
    private Chronometer chronometer2;
    private Button buttonStartT;
    private Button buttonResetBaseTime;

    private int counter = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        textViewInfo = findViewById(R.id.textView_info);
        chronometer = findViewById(R.id.chronometerExample);
        buttonStart = findViewById(R.id.button_start);
        buttonStop = findViewById(R.id.button_stop);
        buttonResetBaseTime = findViewById(R.id.button_resetBaseTime);
        buttonStartT = findViewById(R.id.button_startt);
        chronometer2 = findViewById(R.id.chronometerCountDown);

        // Disable stop and reset buttons initially
        buttonStop.setEnabled(false);
        buttonResetBaseTime.setEnabled(false);

        // Start button for main chronometer
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doStart();
            }
        });

        // Start button for countdown chronometer
        buttonStartT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isCountdownRunning) {
                    // Start the countdown chronometer
                    doStartCountdown();
                    buttonStartT.setText("Pause");
                } else {
                    // Stop the countdown chronometer
                    doStopCountdown();
                    buttonStartT.setText("Start");
                }
            }
        });

        // Stop button
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doStop();
            }
        });

        // Reset base time button
        buttonResetBaseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doResetBaseTime();
            }
        });

        // Set initial text for countdown chronometer
        chronometer2.setText(String.valueOf(counter));

        // Set chronometer tick listener for countdown chronometer
        chronometer2.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                onChronometerTickHandler();
            }
        });
    }


    private void doStartCountdown() {
        // Start the countdown chronometer
        chronometer2.start();
        isCountdownRunning = true;
    }

    private void doStopCountdown() {
        // Stop the countdown chronometer
        chronometer2.stop();
        isCountdownRunning = false;
    }

    private void onChronometerTickHandler()  {
        long delta = SystemClock.elapsedRealtime() - this.chronometer2.getBase();

        int s = (int) ((delta / 1000) % 60);

        String customText = s +" seconds";

        this.chronometer2.setText(customText);
    }




    private void doStart() {
        // Returns milliseconds since system boot, including time spent in sleep.
        long elapsedRealtime = SystemClock.elapsedRealtime();
        // Set the time that the count-up timer is in reference to.
        chronometer.setBase(elapsedRealtime);
        chronometer.start();
        showInfo(elapsedRealtime);
        //
        buttonStart.setEnabled(false);
        buttonStop.setEnabled(true);
        buttonResetBaseTime.setEnabled(true);
    }

    private void doStop() {
        chronometer.stop();

        buttonStart.setEnabled(true);
        buttonStop.setEnabled(false);
        buttonResetBaseTime.setEnabled(false);
    }

    private void doResetBaseTime() {
        // Returns milliseconds since system boot, including time spent in sleep.
        long elapsedRealtime = SystemClock.elapsedRealtime();
        // Set the time that the count-up timer is in reference to.
        chronometer.setBase(elapsedRealtime);
        showInfo(elapsedRealtime);
    }

    // @totalMilliseconds: milliseconds since system boot, including time spent in sleep.
    private void showInfo(long totalMilliseconds) {
        // Seconds
        long totalSecs = totalMilliseconds / 1000;
        // Show Info
        long hours = totalSecs / 3600;
        long minutes = (totalSecs % 3600) / 60;
        long seconds = totalSecs % 60;

        textViewInfo.setText("Base Time: " + totalSecs + " ~ " + hours + " hours " + minutes + " minutes " + seconds + " seconds");
    }
}
