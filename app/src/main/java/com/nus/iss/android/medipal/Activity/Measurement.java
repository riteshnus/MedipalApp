package com.nus.iss.android.medipal.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.nus.iss.android.medipal.R;

public class Measurement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void sendTemperature(View view)
    {
        Intent intent = new Intent(Measurement.this, temperatureActivity.class);
        startActivity(intent);
    }
    public void sendPulse(View view)
    {
        Intent intent = new Intent(Measurement.this, pulseActivity.class);
        startActivity(intent);
    }
    public void sendWeight(View view)
    {
        Intent intent = new Intent(Measurement.this, weightActivity.class);
        startActivity(intent);
    }
    public void sendBp(View view)
    {
        Intent intent = new Intent(Measurement.this, bpActivity.class);
        startActivity(intent);
    }
}