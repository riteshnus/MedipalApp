package com.nus.iss.android.medipal.Activity;

import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.content.DialogInterface;
import android.content.Loader;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
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
import com.nus.iss.android.medipal.dto.Temperature;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class temperatureActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Object> {
    private EditText tempTextView;
    private TextView notes;
    private TextView mesureDate;
    private ImageView emogi;

    private SimpleDateFormat sdf;
    private SimpleDateFormat sdf2;

    private String normalTemp="Normal Body Temperature";
    private String lowtemp="LOW Body Temperature";
    private String highTemp="HIGH Body Temperature";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);
        tempTextView = (EditText) findViewById(R.id.et_temp);
        mesureDate = (TextView) findViewById(R.id.tv_daytemp);
        notes = (TextView) findViewById(R.id.tv_tempNotes);
        emogi =(ImageView)findViewById(R.id.iv_tempFace);
        getLoaderManager().initLoader(Constants.ADD_MEASUREMENT_LOADER, null,this);
        String myFormat = "EEE, d MMM yyyy";
        /*to set the current date in the textview*/
        sdf = new SimpleDateFormat(myFormat);
        sdf2 = new SimpleDateFormat(Constants.SIMPLE_DATE_FORMAT);
        String currentDateandTime = sdf.format(new Date());
        mesureDate.setText(currentDateandTime);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close);

        /*datepicker*/

        mesureDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                new DatePickerDialog(temperatureActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        /*textchange listerner*/

        tempTextView.addTextChangedListener(new TextWatcher() {
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
                if(tempTextView.getText().toString().length()==0)
                {

                    tempTextView.setError("Field cannot be blank");
                    return;
                }
                //String value = tempTextView.getText().toString();
                double probSys = Double.parseDouble(tempTextView.getText().toString());
                //int probSys = Integer.parseInt(value);

                if  (probSys >33.2 & probSys<=38.2) {
                    notes.setText(normalTemp);
                    notes.setTextColor(Color.rgb(0,128,0));
                    emogi.setImageResource(R.mipmap.emohappy);

                }
                else if (probSys <=33.2) {notes.setText(lowtemp);
                    notes.setTextColor(Color.rgb(255,165,0));
                    emogi.setImageResource(R.mipmap.emoneutral);
                }
                else

                {
                    notes.setText(highTemp);
                    notes.setTextColor(Color.rgb(200,0,0));
                    emogi.setImageResource(R.mipmap.emosad);
                }
            }
        });


/*bracket ends*/


    }
    final Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date= new DatePickerDialog.OnDateSetListener() {

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


    private void updateLabel(){


        mesureDate.setText(sdf.format(myCalendar.getTime()));
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.add_temperature_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_saveTemp) {

            createAndInsertMeasurement();


        }
        return super.onOptionsItemSelected(item);
    }



    public void createAndInsertMeasurement() {
        Float temp= null;
        if(null != tempTextView.getText() && !tempTextView.getText().toString().isEmpty()) {
         temp = Float.parseFloat(tempTextView.getText().toString());}
        else
        {
            tempTextView.setError("Field cannot be blank");
            return;
        }
        Date textField=null;

        try {
            textField = sdf2.parse(sdf2.format(sdf.parse(String.valueOf(mesureDate.getText()))));

        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"failed to save date",Toast.LENGTH_SHORT).show();
            return;
        }
       Temperature tempEntry = new Temperature(temp,textField);
        MeasurementDAO MedDao = new MeasurementDAO(this);
        MedDao.saveTemp(tempEntry);
        finish();
    }

    @Override
    public void onBackPressed() {
        if(tempTextView.getText().toString().length()!=0){

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
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        return null;
    }



    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {

    }


    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }


}
