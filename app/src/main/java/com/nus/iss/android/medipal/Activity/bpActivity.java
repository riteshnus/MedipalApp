package com.nus.iss.android.medipal.Activity;

import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.constants.Constants;
import com.nus.iss.android.medipal.dao.MeasurementDAO;
import com.nus.iss.android.medipal.dto.BloodPressure;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class bpActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private EditText systolicTextView;
    private EditText diastolicTextView;
    private TextView measureDate;
    private TextView notes;
    private ImageView emoji;
    private String normalBp = "you have normal systolic bp";
    private String lowBp = "systolic bp AT RISK";
    private String highBp = "HIGH systolic bp";
    private String normalBpD = "you have normal Diastolic bp";
    private String lowBpD = "you have low Diastolic bp";
    private String highBpD = "you have high Diastolic bp";
    private SimpleDateFormat sdf;
    private SimpleDateFormat sdf2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bp);
        systolicTextView = (EditText) findViewById(R.id.et_sys);
        diastolicTextView = (EditText) findViewById(R.id.et_dia);
        measureDate = (TextView) findViewById(R.id.tv_daybp);
        notes = (TextView) findViewById(R.id.ev_bpnotes);
        emoji = (ImageView)findViewById(R.id.iv_faceBp);
        getLoaderManager().initLoader(Constants.ADD_MEASUREMENT_LOADER, null, this);
        String myFormat = "EEE, d MMM yyyy";
        /*to set today's date in the text view*/
        sdf = new SimpleDateFormat(myFormat);
        sdf2 = new SimpleDateFormat(Constants.SIMPLE_DATE_FORMAT);
        String currentDateandTime = sdf.format(new Date());
        measureDate.setText(currentDateandTime);

/*datepicker*/


        measureDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(bpActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


         /*textchange listerner for systolic textview*/

        systolicTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if(systolicTextView.getText().toString().length()==0)
                {
                    systolicTextView.setError("Field cannot be blank");
                    return;
                }

                int probSys = Integer.parseInt(systolicTextView.getText().toString());

                if  (probSys < 120) {
                    notes.setText(normalBp);
                    notes.setTextColor(Color.rgb(0,128,0));
                    emoji.setImageResource(R.mipmap.emohappy);

                }
                else if (probSys > 120 & probSys <= 139) {notes.setText(lowBp);
                    notes.setTextColor(Color.rgb(255,165,0));
                    emoji.setImageResource(R.mipmap.emoneutral);

                }
                else

                {
                    notes.setText(highBp);
                    notes.setTextColor(Color.rgb(200,0,0));
                    emoji.setImageResource(R.mipmap.emosad);

                }
            }

        });



         /*textchange listerner for diastolic textview*/

        diastolicTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if(diastolicTextView.getText().toString().length()==0)
                {

                    diastolicTextView.setError("Field cannot be blank");
                    return;
                }

                int probSys = Integer.parseInt(diastolicTextView.getText().toString());

                if (probSys < 80) {
                    notes.setText(normalBpD);
                    notes.setTextColor(Color.rgb(0,128,0));
                    emoji.setImageResource(R.mipmap.emohappy);


                }
                else if (probSys > 80 & probSys <=89)
                {notes.setText(lowBpD);
                    notes.setTextColor(Color.rgb(255,165,0));
                    emoji.setImageResource(R.mipmap.emoneutral);
                }
                else

                {
                    notes.setText( highBpD);
                    notes.setTextColor(Color.rgb(200,0,0));
                    emoji.setImageResource(R.mipmap.emosad);
                }
            }


        });


/*bracket ends*/

    }


    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };


    private void updateLabel() {

        String myFormat = "EEE, d MMM yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        measureDate.setText(sdf.format(myCalendar.getTime()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.add_blood_pressure_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_saveBp) {

                     createAndInsertMeasurement();


        }
        return super.onOptionsItemSelected(item);
    }

    public void createAndInsertMeasurement() {
        int sys = 0; int dia = 0;
        int check1=systolicTextView.getText().toString().length();
        int check2=diastolicTextView.getText().toString().length();

        if((check1==0) && (check2==0))
        {
            systolicTextView.setError("Field cannot be blank");
            diastolicTextView.setError("Field cannot be blank");
            return;
        }
        else if(check1==0) {

           systolicTextView.setError("Field cannot be blank");
           return;
       }
       else if(check2==0){

           diastolicTextView.setError("Field cannot be blank");
           return;

       }
        if(check1!=0)
        {
            sys = Integer.parseInt(systolicTextView.getText().toString());
        }
        if(check2!=0){

            dia = Integer.parseInt(diastolicTextView.getText().toString());
        }

        DateFormat formatForDate = new SimpleDateFormat(Constants.SIMPLE_TIME_FORMAT);

        Date textField = null;
        try {
            textField = sdf2.parse(sdf2.format(sdf.parse(String.valueOf(measureDate.getText()))));

        } catch (ParseException e) {

            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"dae not saved",Toast.LENGTH_SHORT).show();
            return;
        }

        BloodPressure bp = new BloodPressure(sys, dia,  textField);
        MeasurementDAO MedDao = new MeasurementDAO(this);
        MedDao.saveBp(bp);
        finish();
    }
    @Override
    public void onBackPressed() {
        if(systolicTextView.getText().toString().length()!=0|diastolicTextView.getText().toString().length()!=0){

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
        }

        else
        {
            finish();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


}




