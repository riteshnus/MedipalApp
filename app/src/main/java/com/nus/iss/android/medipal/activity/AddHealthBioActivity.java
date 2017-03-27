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
import com.nus.iss.android.medipal.dao.HealthBioDAO;
import com.nus.iss.android.medipal.dto.HealthBio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddHealthBioActivity extends AppCompatActivity {

    private EditText condDescrTextView;
    private RadioButton conditionRadio;
    private TextView dateTextView;

    private SimpleDateFormat sdfView = new SimpleDateFormat("EEE, d MMM yyyy");
    private SimpleDateFormat sdfDB= new SimpleDateFormat("yyyy-MM-dd");

    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_health_bio);
        condDescrTextView = (EditText) findViewById(R.id.et_userDescription);
        conditionRadio = (RadioButton) findViewById(R.id.rb_condition);
        dateTextView = (TextView) findViewById(R.id.tv_date);

        /* to set the current date in the textview */
        dateTextView.setText(sdfView.format(new Date(System.currentTimeMillis())));

        /*datepicker*/
         dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddHealthBioActivity.this, dateSetListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    } // End of on Create



    private void updateLabel(){
        dateTextView.setText(sdfView.format(myCalendar.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

        String description;
        // C=Condition , A=Allergy
        String condType;
        Date condStartDate;

        if(null != condDescrTextView.getText() && !condDescrTextView.getText().toString().isEmpty()) {
            description = (condDescrTextView.getText().toString());}
        else {
            condDescrTextView.setError("Field cannot be blank");
            return;
        }

        try {
            //condStartDate = sdfDB.parse(sdfDB.format(sdfView.parse(String.valueOf(dateTextView.getText()))));
            condStartDate = sdfDB.parse(sdfDB.format(sdfView.parse(String.valueOf(dateTextView.getText()))));

        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"failed to save date",Toast.LENGTH_SHORT).show();
            return;
        }

        //boolean allergy = allergyRadio.isChecked();

        if(conditionRadio.isChecked())
        {
            condType="C";
        } else{
            condType="A";
        }

        HealthBio healthBio = new HealthBio(description,condStartDate,condType);
        HealthBioDAO healthBioDAO = new HealthBioDAO(this);
        healthBioDAO.save(healthBio);
        finish(); // finish activity
    }




}
