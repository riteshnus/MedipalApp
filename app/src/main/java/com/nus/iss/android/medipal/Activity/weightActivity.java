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
import com.nus.iss.android.medipal.dto.Weight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class weightActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Object> {
    private EditText weightTextView;
    private TextView notes;
    private TextView mesureDate;
    private String normalWeight="You are in optimal shape! Good Work!";
    private String lowWeight="You are below Weight! Eat more1";
    private String hightWeight=" You are over weight! Eat Less!";
    private double height = 1.56;
    private ImageView emoji;
    private SimpleDateFormat sdf;
    private SimpleDateFormat sdf2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        weightTextView = (EditText) findViewById(R.id.et_weight);
        mesureDate = (TextView) findViewById(R.id.tv_dayweight);
        notes = (TextView) findViewById(R.id.ev_weightNote);
        emoji=(ImageView)findViewById(R.id.iv_weightFace);
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

                new DatePickerDialog(weightActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
/*textchange listerner*/

        weightTextView.addTextChangedListener(new TextWatcher() {
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
                if (weightTextView.getText().toString().length() == 0) {
                    weightTextView.setError("Field cannot be blank");
                    return;
                }

                int weight = Integer.parseInt(weightTextView.getText().toString());
                double result = calBmi(weight,height);

                if(result<18.5){

                    notes.setText(lowWeight);
                    notes.setTextColor(Color.rgb(255,165,0));
                    emoji.setImageResource(R.mipmap.emoneutral);

                }
                else if(result>= 18.5 & result <= 25.5){

                    notes.setText(normalWeight);
                    notes.setTextColor(Color.rgb(0,128,0));
                    emoji.setImageResource(R.mipmap.emohappy);

                }
                else
                {
                    notes.setText(hightWeight);
                    notes.setTextColor(Color.rgb(200,0,0));
                    emoji.setImageResource(R.mipmap.emosad);
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

        String myFormat = "EEE, d MMM yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        mesureDate.setText(sdf.format(myCalendar.getTime()));
    }

    private double calBmi(int wt,double ht){
       double bmi;
        bmi = (wt)/ht*ht;
        return bmi;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.add_weight_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_saveWeight) {

            createAndInsertMeasurement();


        }
        return super.onOptionsItemSelected(item);
    }

    public void createAndInsertMeasurement() {

        int weight = 0;
        if(null != weightTextView.getText() && !weightTextView.getText().toString().isEmpty()) {
            weight = Integer.parseInt(weightTextView.getText().toString());
        }else{
            weightTextView.setError("Field cannot be blank");
            return;
        }

        Date textField = null;
        try {
            textField = sdf2.parse(sdf2.format(sdf.parse(String.valueOf(mesureDate.getText()))));
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"failed to save date",Toast.LENGTH_SHORT).show();
            return;
        }

      Weight weightEntry = new Weight( weight, textField);
        MeasurementDAO MedDao = new MeasurementDAO(this);
        MedDao.saveWeight(weightEntry);
        finish();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
    @Override
    public void onBackPressed() {
        if(weightTextView.getText().toString().length()!=0){

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

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }
}
