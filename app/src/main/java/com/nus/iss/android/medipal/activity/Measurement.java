package com.nus.iss.android.medipal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.nus.iss.android.medipal.R;

public class Measurement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement);
    }

    public void sendTemperature(View view)
    {
        Intent intent = new Intent(Measurement.this, TemperatureActivity.class);
        startActivity(intent);
    }
    public void sendPulse(View view)
    {
        Intent intent = new Intent(Measurement.this, PulseActivity.class);
        startActivity(intent);
    }
    public void sendWeight(View view)
    {
        Intent intent = new Intent(Measurement.this, WeightActivity.class);
        startActivity(intent);
    }
    public void sendBp(View view)
    {
        Intent intent = new Intent(Measurement.this, BpActivity.class);
        startActivity(intent);
    }
}