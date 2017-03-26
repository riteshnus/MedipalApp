package com.nus.iss.android.medipal.activity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.app.PendingIntent;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.constants.Constants;
import com.nus.iss.android.medipal.dao.ConsumptionDAO;
import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.dto.Consumption;
import com.nus.iss.android.medipal.dto.Medicine;
import com.nus.iss.android.medipal.dto.Reminder;
import com.nus.iss.android.medipal.helper.Utils;
import com.nus.iss.android.medipal.receiver.MedicineReceiver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.R.id.input;


public class ReminderActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    public static final String LOG_TAG = ReminderActivity.class.getSimpleName();
    private static final int MEDICINE_LOADER = 0;
    private Uri medicineUri;
    private Button ignoreButton;
    private Button snoozeButton;
    private Button takeButton;
    private Medicine medicine;
    private PendingIntent pendingIntent;
    TextView reminderMedicine;
    TextView reminderTimeText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        reminderMedicine= (TextView) findViewById(R.id.reminder_medicine_id);
        reminderTimeText= (TextView) findViewById(R.id.reminder_time_text);
        Intent myIntent=getIntent();
        if(myIntent!=null){
            medicineUri = myIntent.getData();
            reminderMedicine.setText(myIntent.getStringExtra("medicineName"));
            Log.i(LOG_TAG,"Uri passed from Notification " + medicineUri);
        }
        String curentTime= Utils.convertTimeToString(new Date());
        reminderTimeText.setText(curentTime);
        ignoreButton= (Button) findViewById(R.id.ignore_button);
        //snoozeButton= (Button) findViewById(R.id.snooze_button);
        takeButton= (Button) findViewById(R.id.take_button);

        ignoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveConsumption(0);
                finish();
            }
        });
        takeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveConsumption(medicine.getConsumeQuantity());
                finish();
            }
        });
        /*snoozeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ReminderActivity.this);
                alertDialog.setTitle("Snooze Time");
                alertDialog.setMessage("Enter Password");
                final EditText snoozeTimeEditText= new EditText(ReminderActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                snoozeTimeEditText.setLayoutParams(lp);
                alertDialog.setView(input);
                *//*snoozeAlarm();
                finish();*//*
            }
        });*/
       getLoaderManager().initLoader(MEDICINE_LOADER,null,this);

    }

    /*private void snoozeAlarm() {

            Intent myIntent = new Intent(this , MedicineReceiver.class);
            AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
            pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Calendar calendar1=Calendar.getInstance();
            int hour=calendar1.get(Calendar.HOUR_OF_DAY);
            int minute=calendar1.get(Calendar.MINUTE);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MINUTE, minute+10);
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            myIntent.setAction("Paracetamol");
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            Toast.makeText(this,"Alarm Snoozed for 10 Minutes",Toast.LENGTH_SHORT).show();

    }*/

    private void saveConsumption(int consumeQuantity) {
        ConsumptionDAO consumptionDAO=new ConsumptionDAO(this);
        Calendar calendar=Calendar.getInstance();
        Date date =calendar.getTime();
        Consumption consumption=new Consumption(consumeQuantity,date);
        medicine.addConsumption(consumption);
        consumptionDAO.save(consumption);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        String[] projection={MedipalContract.PersonalEntry.MEDICINE_ID,
                MedipalContract.PersonalEntry.MEDICINE_CONSUME_QUANTITY};
        return new CursorLoader(this,medicineUri,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor cursor) {
     int columnIndxForMedicineId=cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_ID);
        int columnIndxForQuantity=cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_CONSUME_QUANTITY);
      if(cursor.moveToFirst()){
          int id=cursor.getInt(columnIndxForMedicineId);
          int quantity=cursor.getInt(columnIndxForQuantity);
          medicine=new Medicine();
          medicine.setMedicineId(id);
          medicine.setConsumeQuantity(quantity);
      }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveConsumption(0);
    }
}
