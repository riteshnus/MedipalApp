package com.nus.iss.android.medipal.activity;

import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.app.TimePickerDialog;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.dto.Appointment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ritesh on 3/18/2017.
 */

public class AppointmentActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener,LoaderManager.LoaderCallbacks<Cursor> {

    private Spinner mDoctorSpinner;
    private String mDoctor = null;
    private TextView dateTextView;
    private TextView timeTextView;
    private SimpleDateFormat sdfDate;
    private SimpleDateFormat sdfTime;
    private TextView ReminderTextView;
    private Switch toggleReminder;
    private EditText mApptTittleEditText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_detail_form);

        ReminderTextView = (TextView) findViewById(R.id.app_reminder);
        toggleReminder = (Switch) findViewById(R.id.ReminderSwitch);
        mDoctorSpinner = (Spinner) findViewById(R.id.select_doctor);
        setupSpinner();

        mApptTittleEditText = (EditText)findViewById(R.id.ev_appt_tittle);

        dateTextView = (TextView) findViewById(R.id.app_date);
        timeTextView = (TextView) findViewById(R.id.app_time);
        //to set the current date in the textfield
        String dateFormat = "EEE, d MMM yyyy";
        sdfDate = new SimpleDateFormat(dateFormat);
        final String currentDate = sdfDate.format(new Date());
        dateTextView.setText(currentDate);
        String timeFormat = "h:mm a";
        sdfTime = new SimpleDateFormat(timeFormat);
        timeTextView.setText(sdfTime.format(new Date().getTime()));
 // for list pop up on edit text reminder

        toggleReminder.setOnCheckedChangeListener(this);

    //for the datepicker
        dateTextView.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               new DatePickerDialog(AppointmentActivity.this, date, myCalendarDate.get(Calendar.YEAR), myCalendarDate.get(Calendar.MONTH),myCalendarDate.get(Calendar.DAY_OF_MONTH)).show();
           }
       });

        timeTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new TimePickerDialog(AppointmentActivity.this, time, myCalendarDate.get(Calendar.HOUR_OF_DAY), myCalendarDate.get(Calendar.MINUTE),true).show();
            }
        });



    }

    final  Calendar myCalendarDate = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,int DayOfMonth) {
            myCalendarDate.set(Calendar.YEAR,year);
            myCalendarDate.set(Calendar.MONTH,monthOfYear);
            myCalendarDate.set(Calendar.DAY_OF_MONTH,DayOfMonth);
            updateLabel(0);
        }
    };

    TimePickerDialog.OnTimeSetListener time =new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myCalendarDate.set(Calendar.HOUR_OF_DAY,hourOfDay);
            myCalendarDate.set(Calendar.MINUTE,minute);
            updateLabel(1);
        }
    };

    private void updateLabel(int typeOfLabel){
        switch (typeOfLabel){
            case 0:
                dateTextView.setText(sdfDate.format(myCalendarDate.getTime()));
                break;
            case 1:
                timeTextView.setText(sdfTime.format(myCalendarDate.getTime()));
                break;
        }
    }


    private void setupSpinner() {
        ArrayAdapter doctorSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_doctor_options, android.R.layout.simple_spinner_item);

        doctorSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mDoctorSpinner.setAdapter(doctorSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mDoctorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.doctor))) {
                        mDoctor = "Hospital";
                    } else if (selection.equals(getString(R.string.clinic))) {
                        mDoctor = "Clinic";
                    } else {
                        mDoctor = "Select Location";
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mDoctor = "Select Location"; // Unknown
            }
        });
    }

    public void openReminderPopup(){
                final String[] items = {"Before 1 Day", "Before 1 hour", "Before 30min", "Before 10Min"};
                AlertDialog.Builder builder = new AlertDialog.Builder(AppointmentActivity.this);
                builder.setTitle("Choose names: ");
                builder.setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        ReminderTextView.setText(items[item]);
                    }
                });
                builder
                        .setCancelable(false)
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();

                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
            }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked) {
            openReminderPopup();
            Log.v("You are :", "Checked");
        }
        else {
            ReminderTextView.setText("");
            Log.v("You are :", " Not Checked");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_appointment_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            //boolean remind = remindSwitch.isChecked();
            createAndInsertAppointment();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void createAndInsertAppointment(){
        String appTittle = mApptTittleEditText.getText().toString();
        Appointment appointment = new Appointment(appTittle,mDoctor,myCalendarDate.getTime());
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
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToFirst()) {
            String apptLocation = cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.APPOINTMENT_LOCATION));
            String apptDescription = cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.APPOINTMENT_DESCRIPTION));
            mApptTittleEditText.setText(apptDescription);
            switch (apptLocation) {
                case "Doctor":
                    mDoctorSpinner.setSelection(1);
                    break;
                case "Clinic":
                    mDoctorSpinner.setSelection(2);
                    break;
                default:
                    mDoctorSpinner.setSelection(0);
                    break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
