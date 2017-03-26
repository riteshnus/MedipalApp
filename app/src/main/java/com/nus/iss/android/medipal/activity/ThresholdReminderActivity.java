package com.nus.iss.android.medipal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nus.iss.android.medipal.R;

/**
 * Created by siddharth on 3/25/2017.
 */
public class ThresholdReminderActivity extends AppCompatActivity {

     private TextView medicineNameTextView;
     private TextView quantityLeftTextView;
     private Button okButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.threshold_reminder_activity);
        medicineNameTextView= (TextView) findViewById(R.id.threshold_medicine_name);
        quantityLeftTextView= (TextView) findViewById(R.id.threshold_medicine_quantity);
        okButton= (Button) findViewById(R.id.ok_button);

        Intent intent=getIntent();
        if(intent!=null){
            String name=intent.getExtras().getString("medicine");
            int quantity=intent.getExtras().getInt("quantityLeft");
            medicineNameTextView.setText("Refil medicne " + name );
            quantityLeftTextView.setText("Quantity left " + String.valueOf(quantity));
        }
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ThresholdReminderActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
