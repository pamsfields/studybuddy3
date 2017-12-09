package com.pam.studybuddy3;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timerTextView;
    Button timerButton;
    boolean countdownOn = false;
    CountDownTimer countdownTimer;


    public void resetTimer() {

        timerTextView.setText("25:00");
        timerSeekBar.setProgress(1500);
        countdownTimer.cancel();
        timerButton.setText("GO!");
        timerSeekBar.setEnabled(true);
        countdownOn = false;
    }
    public void updateTimer(int secondsLeft) {

        int minutes = (int) secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;
        String secondString = Integer.toString(seconds);


        if (seconds <= 9) {
            if (minutes == 10) {

                timerTextView.setText(Integer.toString(minutes) + ":" + 0 + "" + Integer.toString(seconds));

            } else {

                timerTextView.setText(0 + "" + Integer.toString(minutes) + ":" + 0 + "" + Integer.toString(seconds));

            }
        } else {
            timerTextView.setText(0 + "" + Integer.toString(minutes) + ":" + Integer.toString(seconds));


        }
    }
    public void timerButtonOn (View view) {

        System.out.println("tapped button");

        if (countdownOn == false) {

            countdownOn = true;
            timerSeekBar.setEnabled(false);
            timerButton.setText("STOP");


            countdownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    updateTimer((int) millisUntilFinished / 1000);
                    if(millisUntilFinished % 1500000==0){
                        sendNoti();
                    }
                }

                @Override
                public void onFinish() {


                    resetTimer();
                    sendNoti();
                    System.out.println("timer is done!");

                }
            }.start();
        } else {

            resetTimer();
        }


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = (SeekBar) findViewById(R.id.TimerSeekBar);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        timerButton = (Button) findViewById(R.id.timerButton);

        timerSeekBar.setMax(14400);
        timerSeekBar.setProgress(1500);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
    public void sendNoti() {
        int tag = 12345;
        NotificationCompat.Builder notif = new NotificationCompat.Builder(this);
        notif.setAutoCancel(true);
        notif.setSmallIcon(R.mipmap.ic_launcher);
        notif.setWhen(System.currentTimeMillis());
        notif.setContentTitle("REMINDER");
        notif.setContentText("Take a Break!");
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pend = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notif.setContentIntent(pend);
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.notify(tag, notif.build());

    }
}