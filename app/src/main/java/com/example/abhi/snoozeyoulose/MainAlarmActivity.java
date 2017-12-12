package com.example.abhi.snoozeyoulose;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainAlarmActivity extends AppCompatActivity {

    public static final int AMOUNT_TO_PAY = 10;

    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TimePicker alarmTimePicker;
    private static MainAlarmActivity inst;
    private TextView alarmTextView;
    private TextView snoozeCounter;
    private TextView totalPaid;
    private Button on;
    private Button off;
    private Button snooze;
    private Button setSnooze;
    private static int snoozeCount = 0;

    public Button getSnooze(){
        return snooze;
    }

    public static MainAlarmActivity instance(){
        return inst;
    }

    @Override
    protected void onStart() {
        super.onStart();
        inst = this;
        snoozeCount = 0;
        Log.i("Start","MainAlarmActivity Started");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_page);
        alarmTimePicker = (TimePicker) findViewById(R.id.alarmTimePicker);
        alarmTextView = (TextView) findViewById(R.id.alarmText);
        on = (Button) findViewById(R.id.on);
        off = (Button) findViewById(R.id.off);
        snooze = (Button) findViewById(R.id.snooze);
        setSnooze = (Button) findViewById(R.id.setSnooze);
        snoozeCounter = (TextView) findViewById(R.id.snoozeCount);
        snoozeCounter.setTextColor(Color.RED);
        snoozeCounter.setText("Snooze Count: " + snoozeCount);
        totalPaid = (TextView) findViewById(R.id.totalPaid);
        totalPaid.setTextColor(Color.RED);
        totalPaid.setText("Total Paid: $" + snoozeCount*AMOUNT_TO_PAY);
        snooze.setBackgroundColor(Color.RED);
        snooze.setVisibility(View.GONE);
        snooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snooze Alarm and create another alarm for x minutes ahead
                alarmTextView.setText("Snoozed");
                snoozeCounter.setText("Snooze Count: " + ++snoozeCount);
                totalPaid.setText("Total Paid: $" + snoozeCount*AMOUNT_TO_PAY);
                alarmTextView.setText("Snoozed\nPaid: $" + AMOUNT_TO_PAY);
                AlarmReceiver.getRingtone().stop();
                Intent intent = new Intent(MainAlarmActivity.this, AlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(MainAlarmActivity.this, 0, intent, 0);
                alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + SetSnoozeIntervalActivity.getSnoozeMillis(), pendingIntent);

                //Payment Stuff

            }}
            );
        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MyActivity", "Alarm On");
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getHour());
                calendar.set(Calendar.MINUTE, alarmTimePicker.getMinute());
                Intent sendAlarm = new Intent(MainAlarmActivity.this, AlarmReceiver.class);
                alarmTextView.setText("Alarm Set");
                pendingIntent = PendingIntent.getBroadcast(MainAlarmActivity.this, 0, sendAlarm, 0);
                alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
            }
        });
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alarmManager.cancel(pendingIntent);
                setAlarmText("");
                alarmTextView.setText("Alarm Off\n\n$" + snoozeCount*AMOUNT_TO_PAY + " Paid!!!");
                Log.d("MyActivity", "Alarm Off");
                AlarmReceiver.getRingtone().stop();
                snooze.setVisibility(View.GONE);
            }
        });
        setSnooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setSnoozeIntent = new Intent(MainAlarmActivity.inst, SetSnoozeIntervalActivity.class);
                startActivity(setSnoozeIntent);
            }
        });
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    public int getSnoozeCount(){
        return snoozeCount;
    }

    public void setAlarmText(String alarmText) {
        alarmTextView.setText(alarmText);
    }
}
