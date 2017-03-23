package com.nus.iss.android.medipal.Activity;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.nus.iss.android.medipal.Data.MedipalContract;
import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.dao.AppointmentDAO;
import com.nus.iss.android.medipal.helper.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ritesh on 3/23/2017.
 */

public class appointmentAction extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Uri mCurrentApptUri;
    private TextView textViewPlace;
    private TextView textViewDateNTime;
    private TextView textViewApptDesc;

    private static final int APPOINTMENT_LOADER = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_view);
        Intent intent = getIntent();
        mCurrentApptUri = intent.getData();
        textViewPlace = (TextView) findViewById(R.id.tv_doctorOrClinic);
        textViewDateNTime = (TextView) findViewById(R.id.tv_dateTime);
        textViewApptDesc = (TextView) findViewById(R.id.tv_appt_desc);

        getLoaderManager().initLoader(APPOINTMENT_LOADER, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appoinment_view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                deletePopup();
                return true;
            case R.id.action_edit:
                Intent myIntent = new Intent(this, addAppointmentActivity.class);
                myIntent.setData(mCurrentApptUri);
                startActivity(myIntent);
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case APPOINTMENT_LOADER:
                String[] projectionForAppointment = {MedipalContract.PersonalEntry.APPOINTMENT_LOCATION,
                        MedipalContract.PersonalEntry.APPOINTMENT_DATE_TIME,
                        MedipalContract.PersonalEntry.APPOINTMENT_DESCRIPTION,};
                return new CursorLoader(this, mCurrentApptUri, projectionForAppointment, null, null, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Date time;
        switch (loader.getId()) {
            case APPOINTMENT_LOADER:
                //boolean reminderForMedicine = false;
                if (cursor.moveToFirst()) {

                    String apptLocation = cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.APPOINTMENT_LOCATION));
                    String apptDescription = cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.APPOINTMENT_DESCRIPTION));
                    textViewApptDesc.setText(apptDescription);
                    textViewPlace.setText(apptLocation);

                    String dateFormat = "EEE, d MMM, HH:mm a";
                    SimpleDateFormat sdfDate = new SimpleDateFormat(dateFormat);
                    try {
                        time = Utils.converStringToDate(cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.APPOINTMENT_DATE_TIME)));
                        textViewDateNTime.setText(sdfDate.format(time));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void deletePopup() {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            deleteEntry();
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }

        public void deleteEntry(){
            int rowsAffected;
            AppointmentDAO appointmentDAO = new AppointmentDAO(this);
            rowsAffected = appointmentDAO.delete(mCurrentApptUri);
            if (rowsAffected == 0) {
                Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Record deleted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,AppointmentList.class);
                startActivity(intent);
            }
        }
}
