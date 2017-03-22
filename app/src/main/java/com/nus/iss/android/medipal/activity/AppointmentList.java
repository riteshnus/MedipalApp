package com.nus.iss.android.medipal.activity;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.R;

/**
 * Created by Ritesh on 3/21/2017.
 */

public class AppointmentList extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int APPOINTMENT_LOADER =0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_log);

        ListView setApptTextView = (ListView) findViewById(R.id.apptList);
        View emptyViewAppt = findViewById(R.id.empty_view_appt);
        setApptTextView.setEmptyView(emptyViewAppt);

        FloatingActionButton fabAddAppt = (FloatingActionButton) findViewById(R.id.add_appt);
        fabAddAppt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppointmentList.this,AppointmentActivity.class);
                startActivity(intent);
            }
        });
        getLoaderManager().initLoader(APPOINTMENT_LOADER,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projectionForAppointment = {
                MedipalContract.PersonalEntry._ID,
                MedipalContract.PersonalEntry.APPOINTMENT_LOCATION,
                MedipalContract.PersonalEntry.APPOINTMENT_DATE_TIME,
                MedipalContract.PersonalEntry.APPOINTMENT_DESCRIPTION,
        } ;
        return new CursorLoader(this, MedipalContract.PersonalEntry.CONTENT_URI_APPOINTMENT,projectionForAppointment,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
