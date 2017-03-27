package com.nus.iss.android.medipal.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.adapter.HistoryConsumptionAdapter;
import com.nus.iss.android.medipal.adapter.HistoryMeasurementAdapter;
import com.nus.iss.android.medipal.data.MedipalContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HistoryListActivity extends AppCompatActivity
{
    private String categoryValue, medicineValue, measurementTypeValue, formatValue, name;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.history_list_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);

        Bundle bundle = getIntent().getExtras();
        String text = bundle.getString(getString(R.string.pageName));
        String category = bundle.getString(getString(R.string.filterCategory));
        String medicine = bundle.getString(getString(R.string.filterMed));
        String measurementType = bundle.getString(getString(R.string.filterMeasure));
        String missedOrTaken = bundle.getString(getString(R.string.filterTakenOrMissed));

        //To identify the button click either consumption or measurement
        if (Objects.equals(text, getString(R.string.Consumption))) {
            getConsumptionList(category, medicine, missedOrTaken);
            setTitle(R.string.consumption_history);
        }

        if (Objects.equals(text, getString(R.string.Measurement))) {
            getMeasurementList(measurementType);
            setTitle(R.string.measurement_history);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            //Back button click
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            //Filter button click
            case R.id.filter:
                Bundle bundle = getIntent().getExtras();
                String text = bundle.getString(getString(R.string.pageName));

                //Creating alert dialog on click of filter icon
                AlertDialog.Builder builder = new AlertDialog.Builder(HistoryListActivity.this).setCancelable(true)
                        .setPositiveButton(R.string.btnApply, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                final Bundle b = new Bundle();
                                b.putString(getString(R.string.filterCategory), categoryValue);
                                b.putString(getString(R.string.filterMed), medicineValue);
                                b.putString(getString(R.string.filterMeasure), measurementTypeValue);
                                b.putString(getString(R.string.filterTakenOrMissed), formatValue);
                                b.putString(getString(R.string.pageName), name);
                                HistoryListActivity.this.finish();
                                Intent intent = new Intent(HistoryListActivity.this, HistoryListActivity.class);
                                intent.putExtras(b);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.btnCancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                View mView = getLayoutInflater().inflate(R.layout.popup_filter_layout, null);

                //To manage the visibility of filter options
                View categoryLayout = mView.findViewById(R.id.catLayout);
                View medicineLayout = mView.findViewById(R.id.medLayout);
                View measurementLayout = mView.findViewById(R.id.measurementLayout);
                View missedOrTakenLayout = mView.findViewById(R.id.missedOrTakenLayout);

                if (Objects.equals(text, getString(R.string.Consumption)))
                {
                    categoryLayout.setVisibility(View.VISIBLE);
                    medicineLayout.setVisibility(View.VISIBLE);
                    missedOrTakenLayout.setVisibility(View.VISIBLE);
                    name = getString(R.string.Consumption);
                }
                else if (Objects.equals(text, getString(R.string.Measurement)))
                {
                    measurementLayout.setVisibility(View.VISIBLE);
                    missedOrTakenLayout.setVisibility(View.GONE);
                    name = getString(R.string.Measurement);
                }

                getCategorySpinner(mView);
                getMedicineSpinner(mView);
                getMeasurementSpinner(mView);
                getMissedOrTakenSpinner(mView);

                builder.setView(mView);
                AlertDialog alert = builder.create();
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(alert.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                alert.show();
                alert.getWindow().setAttributes(lp);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Method to generate missed or taken spinner
    private void getMissedOrTakenSpinner(View mView)
    {
        Spinner missedOrTaken = (Spinner) mView.findViewById(R.id.spinner);
        ArrayAdapter missedtakenAdapter = ArrayAdapter.createFromResource(HistoryListActivity.this,
                R.array.array_missedtaken_option, android.R.layout.simple_spinner_item);
        missedtakenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        missedOrTaken.setAdapter(missedtakenAdapter);


        //Missed or Taken spinner value selection
        missedOrTaken.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection))
                {
                    if (Objects.equals(selection, getString(R.string.None)))
                    {
                        formatValue = null;
                        return;
                    }
                    formatValue = selection;
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //Method to generate measurement spinner
    private void getMeasurementSpinner(View mView)
    {
        Spinner measurement = (Spinner) mView.findViewById(R.id.spinnerMeasurementType);
        ArrayAdapter measurementTypeSpinnerAdapter = ArrayAdapter.createFromResource(HistoryListActivity.this,
                R.array.array_meaurementtype_option, android.R.layout.simple_spinner_item);
        measurementTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        measurement.setAdapter(measurementTypeSpinnerAdapter);

        //Measurement type spinner value selection
        measurement.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (Objects.equals(selection, getString(R.string.None))) {
                        measurementTypeValue = null;
                        return;
                    }
                    measurementTypeValue = selection;
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //Method to generate medicine spinner
    private void getMedicineSpinner(View mView) {
        Spinner medicine = (Spinner) mView.findViewById(R.id.spinnerMedicine);
        List<String> arrayMedicine = getAllMedicine();
        ArrayAdapter<String> medicineSpinnerAdapter = new ArrayAdapter<>(HistoryListActivity.this,
                android.R.layout.simple_spinner_item, arrayMedicine);
        medicineSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        medicine.setAdapter(medicineSpinnerAdapter);

        //medicine spinner value selection
        medicine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (Objects.equals(selection, getString(R.string.None))) {
                        medicineValue = null;
                        return;
                    }
                    medicineValue = selection;
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //Method to generate category spinner
    private void getCategorySpinner(View mView) {
        final Spinner category = (Spinner) mView.findViewById(R.id.spinnerCategory);
        ArrayAdapter categorySpinnerAdapter = ArrayAdapter.createFromResource(HistoryListActivity.this,
                R.array.array_categoryfilter_option, android.R.layout.simple_spinner_item);
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(categorySpinnerAdapter);

        //category spinner value selection
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection) && !Objects.equals(selection, getString(R.string.None))) {
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
            }
        });
    }

    //Method to populate data in medicine filter spinner
    private List<String> getAllMedicine() {
        String[] projection = new String[]{MedipalContract.PersonalEntry.MEDICINE_MEDICINE_NAME};
        List<String> list = new ArrayList<>();
        list.add(getString(R.string.None));
        Cursor cursor = this.getContentResolver().query(MedipalContract.PersonalEntry.CONTENT_URI_MEDICINE, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_MEDICINE_NAME)));//adding 2nd column data
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

    //Method to get consumption list
    private void getConsumptionList(String category, String medicine, String missedOrTaken)
    {
        String rawQuery = generateQuery(category, medicine, missedOrTaken);

        Cursor resultConsumption = this.getContentResolver().query(MedipalContract.PersonalEntry.CONTENT_URI_CONSUMPTION, null, rawQuery, null, null);

        if ((resultConsumption != null ? resultConsumption.getCount() : 0) == 0)
        {
            TextView tvERROR = (TextView) findViewById(R.id.tvError);
            tvERROR.setText(R.string.errorNoRecord);
            tvERROR.setVisibility(View.VISIBLE);
            return;
        }

        HistoryConsumptionAdapter historyCursorAdapter;
        ListView historyListView = (ListView) findViewById(R.id.historylist);
        historyCursorAdapter = new HistoryConsumptionAdapter(this, resultConsumption);
        historyListView.setAdapter(historyCursorAdapter);
    }

    @NonNull
    private String generateQuery(String category, String medicine, String missedOrTaken)
    {
        String consumptionTable = MedipalContract.PersonalEntry.CONSUMPTION_TABLE_NAME;
        String medicineTable = MedipalContract.PersonalEntry.MEDICINE_TABLE_NAME;
        String medicineId = MedipalContract.PersonalEntry.MEDICINE_ID;
        String medicineNameCol = MedipalContract.PersonalEntry.MEDICINE_MEDICINE_NAME;
        String consumptionMedId = MedipalContract.PersonalEntry.CONSUMPTION_MEDICINE_ID;
        String quantityCol = MedipalContract.PersonalEntry.CONSUMPTION_QUANTITY;
        String consumedOnCol = MedipalContract.PersonalEntry.CONSUMPTION_CONSUMED_ON;

        String rawQuery = "SELECT DISTINCT " + consumptionTable + "." + quantityCol + " as c_qty"+ ", * FROM " + consumptionTable + ", " + medicineTable + " WHERE " + consumptionTable + "." + consumptionMedId + " = " + medicineTable + "." + medicineId;
        if (category != null && !category.isEmpty()) {
            rawQuery = rawQuery + " AND " + medicineTable + "." + MedipalContract.PersonalEntry.MEDICINE_CATID + " = " + "\"" + category + "\"";
        }

        if (medicine != null && !medicine.isEmpty()) {
            rawQuery = rawQuery + " AND " + medicineTable + "." + medicineNameCol + " = " + "\"" + medicine + "\"";
        }

        if (missedOrTaken != null && !missedOrTaken.isEmpty())
        {
            if(missedOrTaken.equals("Taken"))
            {
                rawQuery = rawQuery + " AND " + consumptionTable + "." + quantityCol + " != 0";
            }
            else if(missedOrTaken.equals("Missed"))
            {
                rawQuery = rawQuery + " AND " + consumptionTable + "." + quantityCol + " = 0";
            }
        }

        rawQuery = rawQuery + " ORDER BY " + consumptionTable + "." + consumedOnCol + getString(R.string.order);
        return rawQuery;
    }

    //Method to get list of measurement
    private void getMeasurementList(String measurementType)
    {
        String whereClause = "";
        if (measurementType != null) {
            switch (measurementType) {
                case "Blood Pressure":
                    whereClause = getString(R.string.Systolic) + "!=" + "'" + "' ";
                    break;
                case "Pulse":
                    whereClause = getString(R.string.Pulse) + "!=" + "'" + "' ";
                    break;
                case "Temperature":
                    whereClause = getString(R.string.Temperature) + "!=" + "'" + "' ";
                    break;
                case "Weight":
                    whereClause = getString(R.string.Weight) + "!=" + "'" + "' ";
                    break;
                default:
                    break;
            }
        }

        String sortBy = MedipalContract.PersonalEntry.MEASUREMENT_MEASURED_ON + getString(R.string.order);
        Cursor resultMeasurement = this.getContentResolver().query(MedipalContract.PersonalEntry.CONTENT_URI_MEASUREMENT, null, whereClause, null, sortBy);
        if ((resultMeasurement != null ? resultMeasurement.getCount() : 0) == 0) {
            TextView tvERROR = (TextView) findViewById(R.id.tvError);
            tvERROR.setText(getString(R.string.errorNoRecord));
            tvERROR.setVisibility(View.VISIBLE);
            return;
        }

        HistoryMeasurementAdapter historyCursorAdapter;
        ListView historyListView = (ListView) findViewById(R.id.historylist);
        historyCursorAdapter = new HistoryMeasurementAdapter(this, resultMeasurement);
        historyListView.setAdapter(historyCursorAdapter);
    }
}
