package com.nus.iss.android.medipal.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nus.iss.android.medipal.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddHealthBioActivity extends AppCompatActivity {

    private EditText descriotionTextView;
    private RadioButton allergyRadio;
    private RadioButton conditionRadio;
    private TextView dateTextView;
    private SimpleDateFormat sdf;
    private SimpleDateFormat sdf2;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_health_bio);
        descriotionTextView = (EditText) findViewById(R.id.et_userDescription);
        allergyRadio = (RadioButton) findViewById(R.id.rb_allergy);
        conditionRadio = (RadioButton) findViewById(R.id.rb_condition);
        dateTextView = (TextView) findViewById(R.id.tv_date);
        String DateFormat = "dd-MM-yyyy";
        String myFormat = "EEE, d MMM yyyy";
        /*to set the current date in the textview*/
        sdf = new SimpleDateFormat(myFormat);
        //sdf2 = new SimpleDateFormat(Constants.SIMPLE_DATE_FORMAT);
        String currentDateandTime = sdf.format(new Date(System.currentTimeMillis()));
        dateTextView.setText(currentDateandTime);
        sdf2 = new SimpleDateFormat(DateFormat);
         /*datepicker*/


        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddHealthBioActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });



    }



    private void updateLabel(){

        dateTextView.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.activity_add_health_bio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {

            createAndInsertDetail();


        }
        return super.onOptionsItemSelected(item);
    }


    public void createAndInsertDetail() {

        if(null != descriotionTextView.getText() && !descriotionTextView.getText().toString().isEmpty()) {
            String description = (descriotionTextView.getText().toString());}
        else
        {
            descriotionTextView.setError("Field cannot be blank");
            return;
        }

        Date textField=null;

        try {
            textField = sdf2.parse(sdf2.format(sdf.parse(String.valueOf(dateTextView.getText()))));

        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"failed to save date",Toast.LENGTH_SHORT).show();
            return;
        }

        int condition =0;
        boolean allergy = allergyRadio.isChecked();

        if(allergy)
        {
            condition=0;
        }
        else{
            condition=1;
        }

/*        MeasurementDAO MedDao = new MeasurementDAO(this);
        MedDao.saveTemp(tempEntry);
        finish();*/
    }




}
