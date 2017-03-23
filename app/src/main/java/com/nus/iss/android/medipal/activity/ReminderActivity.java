package com.nus.iss.android.medipal.activity;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.dto.Medicine;


public class ReminderActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks{
    public static final String LOG_TAG = ReminderActivity.class.getSimpleName();
    private Uri medicineUri;
    private Button ignoreButton;
    private Button snoozeButton;
    private Button takeButton;
    private Medicine medicine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        Intent myIntent=getIntent();
        if(myIntent!=null){
            medicineUri = myIntent.getData();
            Log.v(LOG_TAG,"Uri passed from Notification " + medicineUri);
        }
        ignoreButton= (Button) findViewById(R.id.ignore_button);
        snoozeButton= (Button) findViewById(R.id.snooze_button);
        takeButton= (Button) findViewById(R.id.take_button);

        ignoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveConsumption(0);
            }
        });
        takeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity=1;
                saveConsumption(quantity);
            }
        });
        snoozeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity=1;
               snoozeAlarm();
            }
        });


    }

    private void snoozeAlarm() {
    }

    private void saveConsumption(int consumeQuantity) {
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
