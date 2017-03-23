package com.nus.iss.android.medipal.activity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.dao.MedicineDAO;
import com.nus.iss.android.medipal.dao.ReminderDAO;
import com.nus.iss.android.medipal.data.MedipalContract.PersonalEntry;
import com.nus.iss.android.medipal.dto.Categories;
import com.nus.iss.android.medipal.dto.Medicine;
import com.nus.iss.android.medipal.dto.Reminder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import com.nus.iss.android.medipal.constants.Constants;
import com.nus.iss.android.medipal.helper.Utils;

import com.nus.iss.android.medipal.receiver.MedicineReceiver;

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
    private PendingIntent pendingIntent;
    private Uri medicineUri;
    private static final int MEDICINE_LOADER=0;
    private static final int CATEGORY_LOADER=2;
    private static final int REMINDER_LOADER=3;
    private int dosage=0;
    private Medicine medicine;
    private Uri categoryUri;
    private Uri reminderUri;
    private String errorMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        medicineUri=getIntent().getData();
        if(medicineUri!=null){
            setTitle("Edit Medicine");
        }else {
            setTitle(R.string.add_medicine);
        }
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
        if(medicineUri==null)
            setCurentDateAndTimeToTextView();
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        if(medicineUri!=null){
            getLoaderManager().initLoader(MEDICINE_LOADER, null, this);
        }
        getLoaderManager().initLoader(Constants.ADD_MEDICINE_LOADER, null, this);

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
     * Setup the dropdown spinner that allows the user to select the dosage unit of the medicine and category.
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
                    if (selection.equals(Constants.DOSAGE.pills)) {
                        dosage=0;
                    } else if (selection.equals(Constants.DOSAGE.cc)) {
                        dosage=1;
                    } else if (selection.equals(Constants.DOSAGE.ml)) {
                        dosage=2;
                    }else if (selection.equals(Constants.DOSAGE.gr)) {
                        dosage=3;
                    }else if (selection.equals(Constants.DOSAGE.mg)) {
                        dosage=4;
                    }else if (selection.equals(Constants.DOSAGE.drops)) {
                        dosage=5;
                    }else if (selection.equals(Constants.DOSAGE.pieces)) {
                        dosage=6;
                    }else if (selection.equals(Constants.DOSAGE.puffs)) {
                        dosage=7;
                    }else if (selection.equals(Constants.DOSAGE.units)) {
                        dosage=8;
                    }else if (selection.equals(Constants.DOSAGE.teaspoon)) {
                        dosage=9;
                    }else if (selection.equals(Constants.DOSAGE.tablespoon)) {
                        dosage=10;
                    }else if (selection.equals(Constants.DOSAGE.patch)) {
                        dosage=11;
                    }else if (selection.equals(Constants.DOSAGE.mcg)) {
                        dosage=12;
                    }else if (selection.equals(Constants.DOSAGE.l)) {
                        dosage=13;
                    }else if (selection.equals(Constants.DOSAGE.meq)) {
                        dosage=14;
                    }else if (selection.equals(Constants.DOSAGE.spray)) {
                        dosage=15;
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
                        if(medicineUri==null) {
                            remindSwitch.setChecked(Boolean.FALSE);
                            reminderlayout.setVisibility(View.GONE);
                        }
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
                        if(medicineUri==null) {
                            remindSwitch.setChecked(Boolean.FALSE);
                            reminderlayout.setVisibility(View.GONE);
                        }
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
        int consumeQuantity = Integer.parseInt(dosageText.getText().toString());
        int expiryFactor = Integer.parseInt(expiryFactorTextView.getText().toString());
        int threshold=0;
        if(thresholdReminderSwitch.isChecked()){
            threshold  = Integer.parseInt(thresholdText.getText().toString());
        }


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
        Reminder reminder=null;
        if(remindForMedicine) {
            int interval = Integer.parseInt(intervalText.getText().toString());
            reminder = new Reminder(3, startTime, interval); //TODO frequency needs to be added on screen.
            ReminderDAO reminderDAO = new ReminderDAO(this);
            reminder = reminderDAO.save(reminder);

        }
        medicine = new Medicine(name, description, remindForMedicine, quantity,dosage, consumeQuantity, threshold, issueDate, expiryFactor);
        medicine.setCategory(categories);
        medicine.setReminder(reminder);
        MedicineDAO medicineDAO = new MedicineDAO(this);
        medicineDAO.save(medicine);
        medicineUri= ContentUris.withAppendedId(PersonalEntry.CONTENT_URI_MEDICINE,medicine.getMedicineId());
       if(remindForMedicine){
           addReminder(reminder);
       }
    }

    private void addReminder(Reminder reminder) {
        Intent myIntent = new Intent(this , MedicineReceiver.class);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        myIntent.putExtra("medicine",medicine.getMedicine());
        pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Date date=reminder.getStartTime();
        DateFormat format=new SimpleDateFormat(Constants.SIMPLE_TIME_FORMAT);
        String time=format.format(date);
        Calendar calendar1=Calendar.getInstance();
        calendar1.setTime(date);
        int hour=calendar1.get(Calendar.HOUR_OF_DAY);
        int minute=calendar1.get(Calendar.MINUTE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        myIntent.setAction("Paracetamol");
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),1000*60*60*reminder.getInterval(), pendingIntent);

    }

    private void setCurentDateAndTimeToTextView() {
        Calendar calendar = Calendar.getInstance();
        Date start_date = calendar.getTime();
        DateFormat formatterForDate = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
        String dateOfIssue = formatterForDate.format(start_date);
        DateFormat formatterForTime = new SimpleDateFormat(SIMPLE_TIME_FORMAT);
        formatterForTime.setTimeZone(TimeZone.getDefault());
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
            if (isValid()) {
                if (medicineUri != null) {
                    updateMedicine();
                } else {
                    boolean remind = remindSwitch.isChecked();
                    createAndInsertMedicine();
                }
                finish();
            }
            else {
                new AlertDialog.Builder(this)
                        .setMessage(errorMsg)
                        .setNegativeButton("Ok", null)
                        .setCancelable(false)
                        .show();
            }
        }

        return super.onOptionsItemSelected(item);
    }



    private void updateMedicine() {
        MedicineDAO medicineDAO=new MedicineDAO(this);
        String name = nameTextView.getText().toString();
        String description = descriptionEditText.getText().toString();
        int quantity = Integer.parseInt(quantityTextView.getText().toString());
        int consumeQuantity = Integer.parseInt(dosageText.getText().toString());
        int expiryFactor = Integer.parseInt(expiryFactorTextView.getText().toString());
        int threshold=0;
        if(!TextUtils.isEmpty(thresholdText.getText())){
            threshold = Integer.parseInt(thresholdText.getText().toString());
        }

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
        medicine.setMedicine(name);
        medicine.setDescription(description);
        medicine.setQuantity(quantity);
        medicine.setRemind(remindForMedicine);
        medicine.setThreshold(threshold);
        medicine.setConsumeQuantity(consumeQuantity);
        medicine.setExpireFactor(expiryFactor);
        medicine.setDateIssued(issueDate);
        medicine.setDosage(dosage);
        ReminderDAO reminderDAO=new ReminderDAO(this);
        Reminder reminder;
       if(remindForMedicine){
           if(medicine.getReminder()==null){
                reminder=new Reminder(3,startTime,interval);
               reminder=reminderDAO.save(reminder);
               medicine.setReminder(reminder);
           }else {
               reminder =medicine.getReminder();
               reminder.setStartTime(startTime);
               reminder.setInterval(interval);
               reminder=reminderDAO.update(reminder,reminderUri);
               medicine.setReminder(reminder);
           }
       }
       if(medicine.getReminder()!=null) {
           addReminder(medicine.getReminder());
       }
        medicineDAO.update(medicine,medicineUri);
        if(!remindForMedicine){
            if(medicine.getReminder()!=null) {
                medicine.setReminder(null);
                reminderDAO.delete(reminderUri);
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case MEDICINE_LOADER:
                String[] projectionForMedicine={
                        PersonalEntry.MEDICINE_ID,
                        PersonalEntry.MEDICINE_MEDICINE_NAME,
                        PersonalEntry.MEDICINE_DESCRIPTION,
                        PersonalEntry.MEDICINE_CATID,
                        PersonalEntry.MEDICINE_REMINDERID,
                        PersonalEntry.MEDICINE_CONSUME_QUANTITY,
                        PersonalEntry.MEDICINE_REMIND,
                        PersonalEntry.MEDICINE_DATE_ISSUED,
                        PersonalEntry.MEDICINE_DOSAGE,
                        PersonalEntry.MEDICINE_EXPIRE_FACTOR,
                        PersonalEntry.MEDICINE_THRESHOLD,
                        PersonalEntry.MEDICINE_QUANTITY};
                return new CursorLoader(this, medicineUri,projectionForMedicine, null, null, null);
            case Constants.ADD_MEDICINE_LOADER:
                String[] projection = {
                        PersonalEntry.CATEGORIES_ID,
                        PersonalEntry.CATEGORIES_CATEGORY_NAME,
                        PersonalEntry.CATEGORIES_CODE,
                        PersonalEntry.CATEGORIES_REMIND};
                return new CursorLoader(this, PersonalEntry.CONTENT_URI_CATEGORY, projection, null, null, null);
            case CATEGORY_LOADER:
                String[] projectionForCategory = {
                        PersonalEntry.CATEGORIES_ID,
                        PersonalEntry.CATEGORIES_CATEGORY_NAME,
                        PersonalEntry.CATEGORIES_CODE,
                        PersonalEntry.CATEGORIES_REMIND};
                return new CursorLoader(this, categoryUri,projectionForCategory, null, null, null);
            case REMINDER_LOADER:
                String[] projectionForReminder={
                        PersonalEntry.REMINDER_ID,
                        PersonalEntry.REMINDER_FREQUENCY,
                        PersonalEntry.REMINDER_START_TIME,
                        PersonalEntry.REMINDER_INTERVAL
                };
                return new CursorLoader(this, reminderUri,projectionForReminder, null, null, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()){
            case MEDICINE_LOADER:
                int idColumnIndex=cursor.getColumnIndex(PersonalEntry.MEDICINE_ID);
                int nameColumnIndex=cursor.getColumnIndex(PersonalEntry.MEDICINE_MEDICINE_NAME);
                int descColumnIndex=cursor.getColumnIndex(PersonalEntry.MEDICINE_DESCRIPTION);
                int quantityColumnIndex=cursor.getColumnIndex(PersonalEntry.MEDICINE_QUANTITY);
                int consumeQuantityIndex=cursor.getColumnIndex(PersonalEntry.MEDICINE_CONSUME_QUANTITY);
                int dosageIndex=cursor.getColumnIndex(PersonalEntry.MEDICINE_DOSAGE);
                int issueDateIndex=cursor.getColumnIndex(PersonalEntry.MEDICINE_DATE_ISSUED);
                int expiryFactorIndex=cursor.getColumnIndex(PersonalEntry.MEDICINE_EXPIRE_FACTOR);
                int thresholdIndex=cursor.getColumnIndex(PersonalEntry.MEDICINE_THRESHOLD);
                int catIdIndex=cursor.getColumnIndex(PersonalEntry.MEDICINE_CATID);
                int reminderIdIndex=cursor.getColumnIndex(PersonalEntry.MEDICINE_REMINDERID);
                int remindIndex=cursor.getColumnIndex(PersonalEntry.MEDICINE_REMIND);

                if(cursor.moveToFirst()) {
                    int medicineId = cursor.getInt(idColumnIndex);
                    String medicineName = cursor.getString(nameColumnIndex);
                    String description = cursor.getString(descColumnIndex);
                    int quantity = cursor.getInt(quantityColumnIndex);
                    int consumeQuantity = cursor.getInt(consumeQuantityIndex);
                    int dosage = cursor.getInt(dosageIndex);
                    String issueDate = cursor.getString(issueDateIndex);
                    Date dateIssued = null;
                    try {
                        dateIssued = Utils.converStringToDate(issueDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    int expiryFactor = cursor.getInt(expiryFactorIndex);
                    int threshold = cursor.getInt(thresholdIndex);
                    int catId = cursor.getInt(catIdIndex);
                    int reminderId = cursor.getInt(reminderIdIndex);
                    int remindValue = cursor.getInt(remindIndex);
                    boolean reminderForMedicine = false;
                    if (remindValue == 1) {
                        reminderForMedicine = true;
                    }
                    medicine = new Medicine(medicineName, description, reminderForMedicine, quantity, dosage, consumeQuantity, threshold, dateIssued, expiryFactor);
                    medicine.setMedicineId(medicineId);
                    categoryUri = ContentUris.withAppendedId(PersonalEntry.CONTENT_URI_CATEGORY, catId);
                    reminderUri = ContentUris.withAppendedId(PersonalEntry.CONTENT_URI_REMINDER, reminderId);
                    getLoaderManager().restartLoader(CATEGORY_LOADER, null, this);
                }
                break;
            case Constants.ADD_MEDICINE_LOADER:
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
                break;
            case CATEGORY_LOADER:
                int categoryNameIdx=cursor.getColumnIndex(PersonalEntry.CATEGORIES_CATEGORY_NAME);
                int categoryCodeIdx=cursor.getColumnIndex(PersonalEntry.CATEGORIES_CODE);
                int categoryRemindIdx=cursor.getColumnIndex(PersonalEntry.CATEGORIES_REMIND);
                int categoryIdIdx=cursor.getColumnIndex(PersonalEntry.CATEGORIES_ID);
                if (cursor.moveToFirst()) {
                    String categoryName=cursor.getString(categoryNameIdx);
                    String categoryCode=cursor.getString(categoryCodeIdx);
                    int remind=cursor.getInt(categoryRemindIdx);
                    boolean reminderValue=false;
                    if (remind == 1) {
                        reminderValue=true;
                    }
                    int categoryId=cursor.getInt(categoryIdIdx);
                    Categories categories=new Categories(categoryId,categoryName,categoryCode,null,reminderValue);
                    medicine.setCategory(categories);
                }
                if(medicine.isRemind()) {
                    getLoaderManager().restartLoader(REMINDER_LOADER, null, this);
                }else {
                    setData();
                }
                break;
            case REMINDER_LOADER:
                int remIdIndex=cursor.getColumnIndex(PersonalEntry.REMINDER_ID);
                int reminderFequencyIndex=cursor.getColumnIndex(PersonalEntry.REMINDER_FREQUENCY);
                int reminderIntervalIndex=cursor.getColumnIndex(PersonalEntry.REMINDER_INTERVAL);
                int reminderStartTime=cursor.getColumnIndex(PersonalEntry.REMINDER_START_TIME);

                if(cursor.moveToFirst()) {
                    int remId = cursor.getInt(remIdIndex);
                    int frequency = cursor.getInt(reminderFequencyIndex);
                    int interval = cursor.getInt(reminderIntervalIndex);
                    String startTimeString = cursor.getString(reminderStartTime);
                    Date startTime = null;
                    try {
                        startTime = Utils.converStringToDate(startTimeString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Reminder reminder = new Reminder(frequency, startTime, interval);
                    reminder.setReminderId(remId);
                    medicine.setReminder(reminder);
                }
                setData();


                break;

        }

    }

    private void setData() {
        nameTextView.setText(medicine.getMedicine());
        descriptionEditText.setText(medicine.getDescription());
        quantityTextView.setText(String.valueOf(medicine.getQuantity()));
        dateOfIssueTextView.setText(Utils.convertDateToString(medicine.getDateIssued()));
        expiryFactorTextView.setText(String.valueOf(medicine.getExpireFactor()));
        dosageText.setText(String.valueOf(medicine.getConsumeQuantity()));
        categorySpinner.setSelection(getPositionForCategory(category_text));
        dosageSpinner.setSelection(medicine.getDosage());
        if(medicine.isRemind()) {
            reminderlayout.setVisibility(View.VISIBLE);
            remindSwitch.setChecked(medicine.isRemind());
            startTimeTextView.setText(Utils.convertTimeToString(medicine.getReminder().getStartTime()));
            intervalText.setText(String.valueOf(medicine.getReminder().getInterval()));
        }else {
            reminderlayout.setVisibility(View.GONE);
        }
        if(medicine.getThreshold()!=0){
            thresholdReminderSwitch.setChecked(Boolean.TRUE);
            thresholdText.setVisibility(View.VISIBLE);
            thresholdText.setText(String.valueOf(medicine.getThreshold()));
        }else {
            thresholdReminderSwitch.setChecked(Boolean.FALSE);
            thresholdText.setVisibility(View.GONE);
        }
    }

    private int getPositionForCategory(String code) {
        int position=0;
        if(code.equals("SUP")){
            category_text = "SUP";
            position=0;
        }else if(code.equals("CHR")){
            category_text = "CHR";
            position=1;
        }else if(code.equals("INC")){
            category_text = "INC";
            position=2;
        }else if(code.equals("COM")){
            category_text = "COM";
            position=3;
        }else if(code.equals("SEL")){
            category_text = "SEL";
            position=4;
        }
        return position;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private boolean isValid(){

        if(TextUtils.isEmpty(nameTextView.getText())){
            errorMsg="Name of Medicine Can not be empty";
            return false;
        }
        if(TextUtils.isEmpty(quantityTextView.getText())){
            errorMsg="Medicine Quantity Can not be empty";
            return false;
        }
        if(TextUtils.isEmpty(dosageText.getText())){
            errorMsg="Daily to be consumed quantity Can not be empty";
            return false;
        }
        if(TextUtils.isEmpty(expiryFactorTextView.getText())){
            errorMsg="Expiry Factor of Medicine Can not be empty";
            return false;
        }
        if(remindSwitch.isChecked()) {
            if (TextUtils.isEmpty(intervalText.getText())) {
                errorMsg = "Interval for Reminder Can not be empty";
                return false;
            }
        }
        if(thresholdReminderSwitch.isChecked()) {
            if (TextUtils.isEmpty(thresholdText.getText())) {
                errorMsg = "Medicine threshold Can not be empty";
                return false;
            }
        }
        return true;
    }
}
