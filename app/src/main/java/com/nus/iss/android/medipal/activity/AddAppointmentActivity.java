package com.nus.iss.android.medipal.activity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.constants.Constants;
import com.nus.iss.android.medipal.dao.AppointmentDAO;
import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.dto.Appointment;
import com.nus.iss.android.medipal.helper.Utils;
import com.nus.iss.android.medipal.receiver.AppointmentReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Ritesh on 3/18/2017.
 */

public class AddAppointmentActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, LoaderManager.LoaderCallbacks<Cursor> {

    private Uri mCurrentApptUri;
    private Spinner mDoctorSpinner;
    private String mDoctor = null;
    private TextView dateTextView;
    private TextView timeTextView;
    private SimpleDateFormat sdfDate;
    private SimpleDateFormat sdfTime;
    private TextView ReminderTextView;
    private TextView mSelectCategory;
    private Switch toggleReminder;
    private EditText mApptTittleEditText;
    String dateFormat = "EEE, d MMM yyyy";
    String timeFormat = "h:mm a";

    private static final int APPOINTMENT_LOADER = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_detail_form);

        ReminderTextView = (TextView) findViewById(R.id.app_reminder);
        toggleReminder = (Switch) findViewById(R.id.ReminderSwitch);
        mSelectCategory = (TextView) findViewById(R.id.select_category);
        //mDoctorSpinner = (Spinner) findViewById(R.id.select_doctor);
        //setupSpinner();

        mApptTittleEditText = (EditText) findViewById(R.id.ev_appt_tittle);

        dateTextView = (TextView) findViewById(R.id.app_date);
        timeTextView = (TextView) findViewById(R.id.app_time);
        //to set the current date in the textfield
        sdfDate = new SimpleDateFormat(dateFormat);
        final String currentDate = sdfDate.format(new Date());
        dateTextView.setText(currentDate);
        sdfTime = new SimpleDateFormat(timeFormat);
        timeTextView.setText(sdfTime.format(new Date().getTime()));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close);


        toggleReminder.setOnCheckedChangeListener(this);

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddAppointmentActivity.this, date, myCalendarDate.get(Calendar.YEAR), myCalendarDate.get(Calendar.MONTH), myCalendarDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(AddAppointmentActivity.this, time, myCalendarDate.get(Calendar.HOUR_OF_DAY), myCalendarDate.get(Calendar.MINUTE), false).show();
            }
        });

        mSelectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"Doctor", "Clinic"};

                AlertDialog.Builder builder = new AlertDialog.Builder(AddAppointmentActivity.this);
                builder.setTitle("Select Category");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        mSelectCategory.setText(items[item]);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }

        });
        Intent intent = getIntent();
        mCurrentApptUri = intent.getData();
        if (mCurrentApptUri != null) {
            getLoaderManager().initLoader(APPOINTMENT_LOADER, null, this);
        }

    }

    final Calendar myCalendarDate = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int DayOfMonth) {
            myCalendarDate.set(Calendar.YEAR, year);
            myCalendarDate.set(Calendar.MONTH, monthOfYear);
            myCalendarDate.set(Calendar.DAY_OF_MONTH, DayOfMonth);
            updateLabel(0);
        }
    };

    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myCalendarDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
            myCalendarDate.set(Calendar.MINUTE, minute);
            updateLabel(1);
        }
    };

    private void updateLabel(int typeOfLabel) {
        switch (typeOfLabel) {
            case 0:
                dateTextView.setText(sdfDate.format(myCalendarDate.getTime()));
                break;
            case 1:
                timeTextView.setText(sdfTime.format(myCalendarDate.getTime()));
                break;
        }
    }

    public void openReminderPopup() {
        final String[] items = {"Before 1 Day", "Before 1 hour", "Before 30 Min", "Before 10 Min"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddAppointmentActivity.this);
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
        if (isChecked) {
            openReminderPopup();
            Log.v("You are :", "Checked");
        } else {
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                createAndInsertAppointment();
                //finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void createAndInsertAppointment() {
        int rowsAffected = 0;
        String appTittle = "";
        String appCategory = "";
        if(!mApptTittleEditText.getText().toString().isEmpty())
        {
             appTittle = mApptTittleEditText.getText().toString();
        }
        else
        {
            mApptTittleEditText.setError("Field cannot be blank");
            return;
        }
        if(!mSelectCategory.getText().toString().isEmpty())
        {
          appCategory = mSelectCategory.getText().toString();
        }
        else
        {
            mSelectCategory.setError("Please Select Category");
            return;
        }
        Appointment appointment = new Appointment(appTittle, appCategory, myCalendarDate.getTime());
        AppointmentDAO appointmentDAO = new AppointmentDAO(this);
        if (mCurrentApptUri == null) {
            appointmentDAO.save(appointment);
        } else {
            rowsAffected = appointmentDAO.update(appointment,mCurrentApptUri);
            if (rowsAffected == 0) {
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Update Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,AppointmentList.class);
                startActivity(intent);
            }
        }

        if(toggleReminder.isChecked()){
            addReminder();
        }
        finish();
    }

    private void addReminder() {
        String whenReminder= ReminderTextView.getText().toString();
        switch (whenReminder){
            case "Before 1 Day":
                myCalendarDate.add(Calendar.HOUR,-24);
            case "Before 1 hour":
                myCalendarDate.add(Calendar.HOUR,-1);
            case "Before 30 Min":
                myCalendarDate.add(Calendar.MINUTE,-30);
            case "Before 10 Min":
                myCalendarDate.add(Calendar.MINUTE,10);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(myCalendarDate.getTime());
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent apptIntent = new Intent(this, AppointmentReceiver.class);
            apptIntent.putExtra("appointment", mApptTittleEditText.getText().toString());
            apptIntent.putExtra("place", mSelectCategory.getText().toString());
            apptIntent.putExtra("timeAppt", timeTextView.getText().toString());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, Constants.pendingIntent__appointment_id, apptIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Log.i("Calender Time", " " + calendar.getTime());
            Calendar calendarTrigger = Calendar.getInstance();
            calendarTrigger.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
            calendarTrigger.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
            calendarTrigger.set(Calendar.SECOND, 0);
            Log.i("CalenderTrigger Time", " " + calendarTrigger.getTime());
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendarTrigger.getTimeInMillis(), pendingIntent);
            //alarmManager.set(AlarmManager.RTC_WAKEUP, calendarTrigger.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projectionForAppointment = {
                MedipalContract.PersonalEntry._ID,
                MedipalContract.PersonalEntry.APPOINTMENT_LOCATION,
                MedipalContract.PersonalEntry.APPOINTMENT_DATE_TIME,
                MedipalContract.PersonalEntry.APPOINTMENT_DESCRIPTION,
        };
        return new CursorLoader(this, mCurrentApptUri, projectionForAppointment, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToFirst()) {
            String apptLocation = cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.APPOINTMENT_LOCATION));
            String apptDescription = cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.APPOINTMENT_DESCRIPTION));
            Date time;
            try {
                time = Utils.converStringToDate(cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.APPOINTMENT_DATE_TIME)));
                sdfDate = new SimpleDateFormat(dateFormat);
                final String storedDate = sdfDate.format(time);
                dateTextView.setText(storedDate);
                sdfTime = new SimpleDateFormat(timeFormat);
                timeTextView.setText(sdfTime.format(time.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mApptTittleEditText.setText(apptDescription);
            mSelectCategory.setText(apptLocation);
            toggleReminder.setChecked(false);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onBackPressed() {
        if (mApptTittleEditText.getText().toString().length() != 0) {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        } else {
            finish();
        }
    }
}
