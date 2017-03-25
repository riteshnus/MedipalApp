package com.nus.iss.android.medipal.activity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.constants.Constants;
import com.nus.iss.android.medipal.dao.MedicineDAO;
import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.dto.Reminder;
import com.nus.iss.android.medipal.helper.Utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.R.attr.category;
import static android.R.attr.format;
import static android.os.Build.VERSION_CODES.M;

public class CurrentMedicineActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private Uri medicineUri;
    private Uri reminderUri;
    private Uri categoryUri;
    private static final int MEDICINE_LOADER=0;
    private static final int REMINDER_LOADER=1;
    private static final int CATEGORY_LOADER=2;
    private String medicine_name;
    private TextView descriptionTextView;
    private TextView categoryTextView;
    private TextView expiryFactorTextView;
    private String description_text;
    private String category_text;
    private String expiry_date;
    private int expiry_factor=0;
    private String issue_date;
    private TextView reminderTimeTextView;
    private String reminderStartTime;

    private Reminder reminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_medicine);

        medicineUri=getIntent().getData();

        descriptionTextView = (TextView) findViewById(R.id.description_text_view);
        categoryTextView= (TextView) findViewById(R.id.category_text_view);
        expiryFactorTextView= (TextView) findViewById(R.id.expiryFactor_text_view);
        reminderTimeTextView= (TextView) findViewById(R.id.reminder_start_time);

        /*// toolbar


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        getLoaderManager().initLoader(MEDICINE_LOADER,null,this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case MEDICINE_LOADER:
                String[] projectionForMedicine={MedipalContract.PersonalEntry.MEDICINE_MEDICINE_NAME,
                        MedipalContract.PersonalEntry.MEDICINE_REMINDERID,
                        MedipalContract.PersonalEntry.MEDICINE_CATID,
                        MedipalContract.PersonalEntry.MEDICINE_DESCRIPTION,
                        MedipalContract.PersonalEntry.MEDICINE_DATE_ISSUED,
                        MedipalContract.PersonalEntry.MEDICINE_REMIND,
                        MedipalContract.PersonalEntry.MEDICINE_EXPIRE_FACTOR};
                return new CursorLoader(this,medicineUri,projectionForMedicine,null,null,null);
            case REMINDER_LOADER:
                String[] projectionForReminder={MedipalContract.PersonalEntry.REMINDER_START_TIME,
                        MedipalContract.PersonalEntry.REMINDER_FREQUENCY};
                return new CursorLoader(this,reminderUri,projectionForReminder,null,null,null);
            case CATEGORY_LOADER:
                String[] projectionForCategory={MedipalContract.PersonalEntry.CATEGORIES_CODE};
                return new CursorLoader(this,categoryUri,projectionForCategory,null,null,null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
      switch (loader.getId()){
          case MEDICINE_LOADER:
              boolean reminderForMedicine = false;
              if(data.moveToFirst()){
                  int columnIndexForReminderId=data.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_REMINDERID);
                  int columnIndexForCategoryId=data.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_CATID);
                  int columnIndexForMedicineName=data.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_MEDICINE_NAME);
                  int columnIndexForMedicineExpiryFactor=data.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_EXPIRE_FACTOR);
                  int columnIndexForMedicineIssueDate=data.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_DATE_ISSUED);
                  int columnIndexForMedicineDscription=data.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_DESCRIPTION);
                  int columnIndexForMedicineRemindr=data.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_REMIND);
                  int reminderID=data.getInt(columnIndexForReminderId);
                  int categoryId=data.getInt(columnIndexForCategoryId);
                  reminderUri= ContentUris.withAppendedId(MedipalContract.PersonalEntry.CONTENT_URI_REMINDER,reminderID);
                  categoryUri=ContentUris.withAppendedId(MedipalContract.PersonalEntry.CONTENT_URI_CATEGORY,categoryId);
                  medicine_name=data.getString(columnIndexForMedicineName);
                  description_text=data.getString(columnIndexForMedicineDscription);
                   expiry_factor=data.getInt(columnIndexForMedicineExpiryFactor);
                   issue_date=data.getString(columnIndexForMedicineIssueDate);

                  try {
                      Date issueDate=Utils.converStringToDate(issue_date);

                      Date expiryDate= Utils.increaseDateByGivenNumberOfMonths(issueDate,expiry_factor);
                      expiry_date=Utils.convertDateToString(expiryDate);
                  } catch (ParseException e) {
                      e.printStackTrace();
                  }
                  descriptionTextView.setText(description_text);
                  expiryFactorTextView.setText(expiry_date);
                  int remindValue = data.getInt(columnIndexForMedicineRemindr);

                  if (remindValue == 1) {
                      reminderForMedicine = true;
                  }

              }
              setTitle(medicine_name);
              if(reminderForMedicine) {
                  getLoaderManager().restartLoader(REMINDER_LOADER, null, this);
              }else {
                  getLoaderManager().restartLoader(CATEGORY_LOADER,null,this);
              }
              break;
          case REMINDER_LOADER:
              if(data.moveToFirst()){
                  int columnIndxForReminderStartTime=data.getColumnIndex(MedipalContract.PersonalEntry.REMINDER_START_TIME);
                  String reminderStartTime=data.getString(columnIndxForReminderStartTime);
                  try {

                      String reminderStartText=Utils.getTimeFromDateString(reminderStartTime);
                      reminderTimeTextView.setText(reminderStartText);
                  } catch (ParseException e) {
                      e.printStackTrace();
                  }
              }
              getLoaderManager().restartLoader(CATEGORY_LOADER,null,this);
              break;
          case CATEGORY_LOADER:
              if(data.moveToFirst()){
                  int columnIndexForCategoryCode=data.getColumnIndex(MedipalContract.PersonalEntry.CATEGORIES_CODE);
                  String code=data.getString(columnIndexForCategoryCode);
                  categoryTextView.setText(code);
              }
      }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.medicine_update_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_update) {
          Intent myIntent=new Intent(this,AddMedicineActivity.class);
            myIntent.setData(medicineUri);
            startActivity(myIntent);
        }
        if (item.getItemId() == R.id.action_delete) {
            if (medicineUri != null) {
                deleteMedicine();
                finish();
            }
        }
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void deleteMedicine() {
        MedicineDAO medicineDAO=new MedicineDAO(this);
        medicineDAO.delete(medicineUri);
    }
}
