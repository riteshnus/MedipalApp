package com.nus.iss.android.medipal.Activity;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nus.iss.android.medipal.Adapter.AppointmentAdapter;
import com.nus.iss.android.medipal.Data.MedipalContract;
import com.nus.iss.android.medipal.R;

/**
 * Created by Ritesh on 3/21/2017.
 */

public class AppointmentList extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final int APPOINTMENT_LOADER =0;
    public AppointmentAdapter appointmentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_list_view);

        ListView setApptTextView = (ListView) findViewById(R.id.apptList);
        View emptyViewAppt = findViewById(R.id.empty_view_appt);
        setApptTextView.setEmptyView(emptyViewAppt);

        appointmentAdapter=new AppointmentAdapter(this,null);
        setApptTextView.setAdapter(appointmentAdapter);

        FloatingActionButton fabAddAppt = (FloatingActionButton) findViewById(R.id.add_appt);
        fabAddAppt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppointmentList.this,addAppointmentActivity.class);
                startActivity(intent);
            }
        });

        /*ImageView imageViewEdit = (ImageView) findViewById(R.id.iv_edit);
        imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        setApptTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AppointmentList.this,appointmentAction.class);
                Uri currentPetUri = ContentUris.withAppendedId(MedipalContract.PersonalEntry.CONTENT_URI_APPOINTMENT,id);
                intent.setData(currentPetUri);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        appointmentAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        appointmentAdapter.swapCursor(null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
