package com.example.abhi.snoozeyoulose;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SetSnoozeIntervalActivity extends AppCompatActivity {

    private static int minutes = 5;
    private TextView instruction;
    private TextView confirmation;
    private EditText editText;
    private Button set;
    //private Button back;


    public static int getSnoozeMillis(){
        return minutes * 60000;
    }

    public static int getSnoozeMinutes(){
        return minutes;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_set_snooze_inteval);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editText = (EditText) findViewById(R.id.editText2);
        confirmation = (TextView) findViewById(R.id.confirmset);
        set = (Button) findViewById(R.id.set);
        //back = (Button) findViewById(R.id.goBack);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minutes = Integer.parseInt(editText.getText().toString());
                Log.i("SnoozeIntervalActivity", "Set Snooze Interval");
                confirmation.setText("Set Snooze Interval");
            }
        });
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

}
