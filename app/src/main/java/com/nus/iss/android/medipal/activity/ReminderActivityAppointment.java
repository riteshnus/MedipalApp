package com.nus.iss.android.medipal.activity;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nus.iss.android.medipal.R;

/**
 * Created by Ritesh on 3/25/2017.
 */

public class ReminderActivityAppointment extends AppCompatActivity implements LoaderManager.LoaderCallbacks{

    public static final String LOG_TAG = ReminderActivityAppointment.class.getSimpleName();
    private Uri appointmentUri;
    private Button OkButton;
    private TextView reminderId;
    private TextView appPlace;
    private TextView appointmentTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_appointment);
        reminderId = (TextView) findViewById(R.id.appointment_id);
        appointmentTime = (TextView) findViewById(R.id.reminder_time_text);
        appPlace = (TextView) findViewById(R.id.appointment_place);
        Intent myIntent=getIntent();
        if(myIntent!=null){
            appointmentUri = myIntent.getData();
            reminderId.setText(myIntent.getStringExtra("appointment"));
            appPlace.setText(myIntent.getStringExtra("place"));
            appointmentTime.setText(myIntent.getStringExtra("timeAppt"));
            Log.i(LOG_TAG,"Uri passed from Notification " + appointmentUri);
        }
        OkButton= (Button) findViewById(R.id.ok_button);
        OkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
