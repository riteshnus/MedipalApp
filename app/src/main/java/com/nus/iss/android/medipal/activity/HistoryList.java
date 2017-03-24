package com.nus.iss.android.medipal.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.nus.iss.android.medipal.adapter.HistoryConsumptionAdapter;
import com.nus.iss.android.medipal.adapter.HistoryMeasurementAdapter;
import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HistoryList extends AppCompatActivity {
    public String categoryValue, medicineValue, measurementTypeValue, formatValue, name;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.history_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.filter:
                Bundle bundle = getIntent().getExtras();
                String text = bundle.getString("NAME");

                AlertDialog.Builder builder = new AlertDialog.Builder(HistoryList.this).setCancelable(true)
                        .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final Bundle b = new Bundle();
                                b.putString("CategoryFilter", categoryValue);
                                b.putString("MedicineFilter", medicineValue);
                                b.putString("MeasurementTypeFilter", measurementTypeValue);
                                b.putString("FormatFilter", formatValue);
                                b.putString("NAME", name);
                                HistoryList.this.finish();
                                Intent intent = new Intent(HistoryList.this, HistoryList.class);
                                intent.putExtras(b);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                View mView = getLayoutInflater().inflate(R.layout.popup_filter_layout, null);

                //To manage filter options

                View categoryLayout = mView.findViewById(R.id.catLayout);
                View medicineLayout = mView.findViewById(R.id.medLayout);
                View measurementLayout = mView.findViewById(R.id.measurementLayout);

                if (Objects.equals(text, "CONSUMPTION")) {
                    categoryLayout.setVisibility(View.VISIBLE);
                    medicineLayout.setVisibility(View.VISIBLE);
                    name = "CONSUMPTION";
                } else if (Objects.equals(text, "MEASUREMENT")) {
                    measurementLayout.setVisibility(View.VISIBLE);
                    name = "MEASUREMENT";
                }

                //category spinner
                final Spinner category = (Spinner) mView.findViewById(R.id.spinnerCategory);
                ArrayAdapter categorySpinnerAdapter = ArrayAdapter.createFromResource(HistoryList.this,
                        R.array.array_categoryfilter_option, android.R.layout.simple_spinner_item);
                categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                category.setAdapter(categorySpinnerAdapter);

                //medicine spinner
                Spinner medicine = (Spinner) mView.findViewById(R.id.spinnerMedicine);
                List<String> arrayMedicine = getAllMedicine();
                ArrayAdapter<String> medicineSpinnerAdapter = new ArrayAdapter<>(HistoryList.this,
                        android.R.layout.simple_spinner_item, arrayMedicine);
                medicineSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                medicine.setAdapter(medicineSpinnerAdapter);

                //measurement spinner
                Spinner measurement = (Spinner) mView.findViewById(R.id.spinnerMeasurementType);
                ArrayAdapter measurementTypeSpinnerAdapter = ArrayAdapter.createFromResource(HistoryList.this,
                        R.array.array_meaurementtype_option, android.R.layout.simple_spinner_item);
                measurementTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                measurement.setAdapter(measurementTypeSpinnerAdapter);


                //report spinner
                Spinner other = (Spinner) mView.findViewById(R.id.spinner);
                ArrayAdapter reportSpinnerAdapter = ArrayAdapter.createFromResource(HistoryList.this,
                        R.array.array_report_option, android.R.layout.simple_spinner_item);
                reportSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                other.setAdapter(reportSpinnerAdapter);

                //category spinner value selection
                category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selection = (String) parent.getItemAtPosition(position);
                        if (!TextUtils.isEmpty(selection) && !Objects.equals(selection, "None")) {
                            switch (selection) {
                                case "Supplement":
                                    categoryValue = "1";
                                    break;
                                case "Chronic":
                                    categoryValue = "2";
                                    break;
                                case "Incidental":
                                    categoryValue = "3";
                                    break;
                                case "Complete Course":
                                    categoryValue = "4";
                                    break;
                                case "Self Apply":
                                    categoryValue = "5";
                                    break;
                                default:
                                    categoryValue = null;
                                    break;
                            }
                        }

                    }

                    // Because AdapterView is an abstract class, onNothingSelected must be defined
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        //  mGender = 0; // Unknown
                    }
                });

                //medicine spinner value selection
                medicine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selection = (String) parent.getItemAtPosition(position);
                        if (!TextUtils.isEmpty(selection)) {
                            if (Objects.equals(selection, "None")) {
                                medicineValue = null;
                                return;
                            }
                            medicineValue = selection;
                        }
                    }

                    // Because AdapterView is an abstract class, onNothingSelected must be defined
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        //  mGender = 0; // Unknown
                    }
                });

                //Measurement type spinner value selection
                measurement.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selection = (String) parent.getItemAtPosition(position);
                        if (!TextUtils.isEmpty(selection)) {
                            if (Objects.equals(selection, "None")) {
                                measurementTypeValue = null;
                                return;
                            }
                            measurementTypeValue = selection;
                        }
                    }

                    // Because AdapterView is an abstract class, onNothingSelected must be defined
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        //  mGender = 0; // Unknown
                    }
                });

                //Report spinner value selection
                other.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selection = (String) parent.getItemAtPosition(position);
                        if (!TextUtils.isEmpty(selection) && !Objects.equals(selection, "None")) {
                            formatValue = selection;
                        }
                    }

                    // Because AdapterView is an abstract class, onNothingSelected must be defined
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        //  mGender = 0; // Unknown
                    }
                });

                builder.setView(mView);
                AlertDialog alert = builder.create();
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(alert.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                alert.show();
                alert.getWindow().setAttributes(lp);
                //alert.getWindow().setBackgroundDrawable(R.style.MyTheme);
                //alert.getWindow().getDecorView().getBackground().setColorFilter(new LightingColorFilter(0x999999,0x9999CC));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Method to populate data in medicine filter spinner
    public List<String> getAllMedicine() {
        String[] projection = new String[]{MedipalContract.PersonalEntry.MEDICINE_MEDICINE_NAME};
        List<String> list = new ArrayList<>();
        list.add("None");
        list.add("   ");
        list.add("   ");
        Cursor cursor = this.getContentResolver().query(MedipalContract.PersonalEntry.CONTENT_URI_MEDICINE, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_MEDICINE_NAME)));//adding 2nd column data
                list.add("   ");
                list.add("   ");
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);

        Bundle bundle = getIntent().getExtras();
        String text = bundle.getString("NAME");
        String category = bundle.getString("CategoryFilter");
        String medicine = bundle.getString("MedicineFilter");
        //String format = bundle.getString("FormatFilter");
        String measurementType = bundle.getString("MeasurementTypeFilter");

        //To identify the button click either consumption or measurement
        if (Objects.equals(text, "CONSUMPTION")) {
            getConsumptionList(category, medicine);
        }

        if (Objects.equals(text, "MEASUREMENT")) {
            getMeasurementList(measurementType);
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //Method to get consumption list
    public void getConsumptionList(String category, String medicine) {
        String consumptionTable = MedipalContract.PersonalEntry.CONSUMPTION_TABLE_NAME;
        String medicineTable = MedipalContract.PersonalEntry.MEDICINE_TABLE_NAME;
        String medicineId = MedipalContract.PersonalEntry.MEDICINE_ID;
        String medicineNameCol = MedipalContract.PersonalEntry.MEDICINE_MEDICINE_NAME;
        String consumptionMedId = MedipalContract.PersonalEntry.CONSUMPTION_MEDICINE_ID;
        String quantityCol = MedipalContract.PersonalEntry.CONSUMPTION_QUANTITY;
        String consumedOnCol = MedipalContract.PersonalEntry.CONSUMPTION_CONSUMED_ON;

        String rawQuery = "SELECT DISTINCT " + consumptionTable + "." + quantityCol + ", * FROM " + consumptionTable + ", " + medicineTable + " WHERE " + consumptionTable + "." + consumptionMedId + " = " + medicineTable + "." + medicineId;
        if (category != null && !category.isEmpty()) {
            rawQuery = rawQuery + " AND " + medicineTable + "." + MedipalContract.PersonalEntry.MEDICINE_CATID + " = " + "\"" + category + "\"";
        }

        if (medicine != null && !medicine.isEmpty()) {
            rawQuery = rawQuery + " AND " + medicineTable + "." + medicineNameCol + " = " + "\"" + medicine + "\"";
        }

//        if(format != null && !format.isEmpty())
//        {
//            rawQuery = rawQuery + "ORDER BY " + consumptionTable + "." + consumedOnCol + format;
//        }
//        else
//        {
        rawQuery = rawQuery + " ORDER BY " + consumptionTable + "." + consumedOnCol + getString(R.string.order);
//        }

        Cursor resultConsumption = this.getContentResolver().query(MedipalContract.PersonalEntry.CONTENT_URI_CONSUMPTION, null, rawQuery, null, null);

        if ((resultConsumption != null ? resultConsumption.getCount() : 0) == 0) {
            TextView tvERROR = (TextView) findViewById(R.id.tvError);
            tvERROR.setText("No record found!!");
            tvERROR.setVisibility(View.VISIBLE);
            return;
        }

        HistoryConsumptionAdapter historyCursorAdapter;
        ListView historyListView = (ListView) findViewById(R.id.historylist);
        historyCursorAdapter = new HistoryConsumptionAdapter(this, resultConsumption);
        historyListView.setAdapter(historyCursorAdapter);
    }

    //Method to get list of measurement
    public void getMeasurementList(String measurementType) {
        //String[] projection = new String[0];
        String whereClause = "";
        if (measurementType != null) {
            switch (measurementType) {
                case "Blood Pressure":
                    //projection = new String[] {"Systolic", "Diastolic"};
                    whereClause = "Systolic" + "!=" + "'" + "' ";
                    break;
                case "Pulse":
                    //projection = new String[] {measurementType};
                    whereClause = "Pulse" + "!=" + "'" + "' ";
                    break;
                case "Temperature":
                    //projection = new String[] {measurementType};
                    whereClause = "Temperature" + "!=" + "'" + "' ";
                    break;
                case "Weight":
                    //projection = new String[] {measurementType};
                    whereClause = "Weight" + "!=" + "'" + "' ";
                    break;
                default:
                    //projection = null;
                    break;
            }
        }

        String sortBy = MedipalContract.PersonalEntry.MEASUREMENT_MEASURED_ON + getString(R.string.order);
        Cursor resultMeasurement = this.getContentResolver().query(MedipalContract.PersonalEntry.CONTENT_URI_MEASUREMENT, null, whereClause, null, sortBy);
        if ((resultMeasurement != null ? resultMeasurement.getCount() : 0) == 0) {
            TextView tvERROR = (TextView) findViewById(R.id.tvError);
            tvERROR.setText("No record found!!");
            tvERROR.setVisibility(View.VISIBLE);
            return;
        }

        HistoryMeasurementAdapter historyCursorAdapter;
        ListView historyListView = (ListView) findViewById(R.id.historylist);
        historyCursorAdapter = new HistoryMeasurementAdapter(this, resultMeasurement);
        historyListView.setAdapter(historyCursorAdapter);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("HistoryList Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
