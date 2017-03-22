package com.nus.iss.android.medipal.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.nus.iss.android.medipal.R;


public class ReminderActivity extends AppCompatActivity {
    public static final String LOG_TAG = ReminderActivity.class.getSimpleName();
    private Uri medicineUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        Intent myIntent=getIntent();
        if(myIntent!=null){
            medicineUri = myIntent.getData();
            Log.v(LOG_TAG,"Uri passed from Notification " + medicineUri);
        }
    }
}
