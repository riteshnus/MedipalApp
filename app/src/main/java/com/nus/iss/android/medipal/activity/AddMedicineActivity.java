package com.nus.iss.android.medipal.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.app.TimePickerDialog;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.dao.MedicineDAO;
import com.nus.iss.android.medipal.dao.ReminderDAO;
import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.data.MedipalContract.PersonalEntry;
import com.nus.iss.android.medipal.dto.Categories;
import com.nus.iss.android.medipal.dto.Medicine;
import com.nus.iss.android.medipal.dto.Reminder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.nus.iss.android.medipal.constants.Constants;

import static com.nus.iss.android.medipal.R.string.interval;
import static com.nus.iss.android.medipal.constants.Constants.SIMPLE_DATE_FORMAT;
import static com.nus.iss.android.medipal.constants.Constants.SIMPLE_TIME_FORMAT;


public class AddMedicineActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Map<String,Categories> categoryMap = new HashMap<String,Categories>();
    private EditText nameTextView;
    private EditText quantityTextView;
    private Spinner dosageSpinner;
    private TextView dateOfIssueTextView;
    private EditText expiryFactorTextView;
    private EditText dosageText;
    private Spinner categorySpinner;
    private String category_text;
    private Switch remindSwitch;
    private LinearLayout reminderlayout;
    private TextView startTimeTextView;
    private Switch thresholdReminderSwitch;
    private EditText thresholdText;
    private EditText intervalText;
    private EditText descriptionEditText;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        setTitle(R.string.add_medicine);
        nameTextView = (EditText) findViewById(R.id.name_text);
        quantityTextView = (EditText) findViewById(R.id.quantity_text);
        dosageSpinner = (Spinner) findViewById(R.id.spinner_dosage);
        dateOfIssueTextView = (TextView) findViewById(R.id.date_of_issue_text);
        expiryFactorTextView = (EditText) findViewById(R.id.expiry_text);
        dosageText = (EditText) findViewById(R.id.dosage_text);
        categorySpinner = (Spinner) findViewById(R.id.spinner_category);
        reminderlayout = (LinearLayout) findViewById(R.id.reminder_layout);
        startTimeTextView = (TextView) findViewById(R.id.reminder_time);
        thresholdText = (EditText) findViewById(R.id.threshold_text);
        intervalText = (EditText) findViewById(R.id.interval_text);
        thresholdReminderSwitch = (Switch) findViewById(R.id.threshold_remind_switch);
        descriptionEditText = (EditText) findViewById(R.id.description_text);
        remindSwitch = (Switch) findViewById(R.id.remind_switch);
        thresholdReminderSwitch.setChecked(Boolean.FALSE);
        thresholdText.setVisibility(View.GONE);
        addListeners();
        setupSpinner();
        setCurentDateAndTimeToTextView();
        getLoaderManager().initLoader(Constants.ADD_MEDICINE_LOADER, null, this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void addListeners() {
        startTimeTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddMedicineActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        startTimeTextView.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, false);
                // timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });


        remindSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    reminderlayout.setVisibility(View.GONE);
                } else {
                    reminderlayout.setVisibility(View.VISIBLE);
                }
            }
        });

        dateOfIssueTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                final int currentMonth = month + 1;
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddMedicineActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateOfIssueTextView.setText(dayOfMonth + "-" + currentMonth + "-" + year);
                    }
                }, year, month, day);
                // datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

            }
        });

        thresholdReminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    thresholdText.setVisibility(View.GONE);
                } else {
                    thresholdText.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * Setup the dropdown spinner that allows the user to select the dosage unit of the meicine and category.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter dosageSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_dosage_option, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        dosageSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        dosageSpinner.setAdapter(dosageSpinnerAdapter);

        // Set the integer mSelected to the constant values
        dosageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.dosage_pills))) {
                        // mGender = 1; // Male
                    } else if (selection.equals(getString(R.string.dosage_cc))) {
                        //mGender = 2; // Female
                    } else {
                        //mGender = 0; // Unknown
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //  mGender = 0; // Unknown
            }
        });

        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter categorySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_category_option, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        categorySpinner.setAdapter(categorySpinnerAdapter);

        // Set the integer mSelected to the constant values
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.supplement))) {
                        category_text = "SUP";
                        remindSwitch.setChecked(Boolean.FALSE);
                        reminderlayout.setVisibility(View.GONE);
                    } else if (selection.equals(getString(R.string.chronic))) {
                        category_text = "CHR";
                        remindSwitch.setChecked(Boolean.TRUE);
                        reminderlayout.setVisibility(View.VISIBLE);
                    } else if (selection.equals(getString(R.string.incidental))) {
                        category_text = "INC";
                        remindSwitch.setChecked(Boolean.TRUE);
                        reminderlayout.setVisibility(View.VISIBLE);
                    } else if (selection.equals(getString(R.string.complete_course))) {
                        category_text = "COM";
                        remindSwitch.setChecked(Boolean.TRUE);
                        reminderlayout.setVisibility(View.VISIBLE);
                    } else if (selection.equals(getString(R.string.self_apply))) {
                        category_text = "SEL";
                        remindSwitch.setChecked(Boolean.FALSE);
                        reminderlayout.setVisibility(View.GONE);
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //  mGender = 0; // Unknown
            }
        });
    }

    public void createAndInsertMedicine() {

        String name = nameTextView.getText().toString();
        String description = descriptionEditText.getText().toString();
        int quantity = Integer.parseInt(quantityTextView.getText().toString());
        int dosage = Integer.parseInt(dosageText.getText().toString());
        int expiryFactor = Integer.parseInt(expiryFactorTextView.getText().toString());
        int threshold = Integer.parseInt(thresholdText.getText().toString());
        int interval = Integer.parseInt(intervalText.getText().toString());
        DateFormat formatForDate = new SimpleDateFormat(Constants.SIMPLE_DATE_FORMAT);
        DateFormat formatForTime = new SimpleDateFormat(Constants.SIMPLE_TIME_FORMAT);
        boolean remindForMedicine = remindSwitch.isChecked();
        Date issueDate = null;
        Date startTime=null;
        try {
            issueDate = formatForDate.parse(String.valueOf(dateOfIssueTextView.getText()));
            startTime = formatForTime.parse(String.valueOf(startTimeTextView.getText()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Categories categories=categoryMap.get(category_text);
        Reminder reminder=new Reminder(3,startTime,interval); //TODO frequency needs to be added on screen.
        ReminderDAO reminderDAO=new ReminderDAO(this);
        reminder=reminderDAO.save(reminder);
        Medicine medicine = new Medicine(name, description, remindForMedicine, quantity, dosage, quantity, threshold, issueDate, expiryFactor);
        medicine.setCategory(categories);
        medicine.setReminder(reminder);
        MedicineDAO medicineDAO = new MedicineDAO(this);
        medicineDAO.save(medicine);
    }

    private void setCurentDateAndTimeToTextView() {
        Calendar calendar = Calendar.getInstance();
        Date start_date = calendar.getTime();
        DateFormat formatterForDate = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
        String dateOfIssue = formatterForDate.format(start_date);
        DateFormat formatterForTime = new SimpleDateFormat(SIMPLE_TIME_FORMAT);
        formatterForTime.setTimeZone(TimeZone.getTimeZone("SST"));
        String start_date_text = formatterForTime.format(start_date);
        startTimeTextView.setText(start_date_text);
        dateOfIssueTextView.setText(dateOfIssue);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.add_medicine_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            boolean remind = remindSwitch.isChecked();
            createAndInsertMedicine();

            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                PersonalEntry.CATEGORIES_ID,
                PersonalEntry.CATEGORIES_CATEGORY_NAME,
                PersonalEntry.CATEGORIES_CODE,
                PersonalEntry.CATEGORIES_REMIND};
        return new CursorLoader(this, PersonalEntry.CONTENT_URI_CATEGORY, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        int categoryNameIndex=cursor.getColumnIndex(PersonalEntry.CATEGORIES_CATEGORY_NAME);
        int categoryCodeIndex=cursor.getColumnIndex(PersonalEntry.CATEGORIES_CODE);
        int categoryRemindIndex=cursor.getColumnIndex(PersonalEntry.CATEGORIES_REMIND);
        int categoryIdIndex=cursor.getColumnIndex(PersonalEntry.CATEGORIES_ID);
        while (cursor.moveToNext()) {
           String categoryName=cursor.getString(categoryNameIndex);
            String categoryCode=cursor.getString(categoryCodeIndex);
            int remind=cursor.getInt(categoryRemindIndex);
            boolean reminderValue=false;
            if (remind == 1) {
                reminderValue=true;
            }
            int categoryId=cursor.getInt(categoryIdIndex);
            Categories categories=new Categories(categoryId,categoryName,categoryCode,null,reminderValue);
            categoryMap.put(categoryCode,categories);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


}
