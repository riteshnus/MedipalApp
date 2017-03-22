package com.nus.iss.android.medipal.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;

import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.dto.BloodPressure;
import com.nus.iss.android.medipal.dto.Pulse;
import com.nus.iss.android.medipal.dto.Temperature;
import com.nus.iss.android.medipal.dto.Weight;

import static com.nus.iss.android.medipal.data.MedipalContract.PersonalEntry;

/**
 * Created by thushara on 3/16/2017.
 */

public class MeasurementDAO {


    private Activity activity;

    public MeasurementDAO(Activity activity) {
        this.activity = activity;
    }

    public void saveBp(BloodPressure measurement) {
        ContentValues val = new ContentValues();
        val.put(MedipalContract.MeasurementEntry.MEASUREMENT_SYSTOLIC, measurement.getSystolic());
        val.put(MedipalContract.MeasurementEntry.MEASUREMENT_DIASTOLIC, measurement.getDiastolic());
        val.put(MedipalContract.MeasurementEntry.MEASUREMENT_MEASURED_ON, String.valueOf(measurement.getMeasuredOn()));

        Uri myUri = activity.getContentResolver().insert(
                MedipalContract.MeasurementEntry.CONTENT_URI_MEASUREMENT, val);

        activity.getContentResolver().notifyChange(MedipalContract.MeasurementEntry.CONTENT_URI_MEASUREMENT, null, false);


    }


    public void savePulse(Pulse measurement) {
        ContentValues val = new ContentValues();
        val.put(MedipalContract.MeasurementEntry.MEASUREMENT_PULSE, measurement.getPulse());
        val.put(MedipalContract.MeasurementEntry.MEASUREMENT_MEASURED_ON, String.valueOf(measurement.getMeasuredOn()));
        Uri myUri = activity.getContentResolver().insert(MedipalContract.MeasurementEntry.CONTENT_URI_MEASUREMENT, val);
        activity.getContentResolver().notifyChange(MedipalContract.MeasurementEntry.CONTENT_URI_MEASUREMENT, null, false);

    }

    public void saveWeight(Weight measurement) {
        ContentValues val = new ContentValues();
        val.put(MedipalContract.MeasurementEntry.MEASUREMENT_WEIGHT, measurement.getWeight());
        val.put(MedipalContract.MeasurementEntry.MEASUREMENT_MEASURED_ON, String.valueOf(measurement.getMeasuredOn()));
        Uri myUri = activity.getContentResolver().insert(MedipalContract.MeasurementEntry.CONTENT_URI_MEASUREMENT, val);
        activity.getContentResolver().notifyChange(MedipalContract.MeasurementEntry.CONTENT_URI_MEASUREMENT, null, false);

    }

    public void saveTemp(Temperature measurement) {
        ContentValues val = new ContentValues();
        val.put(MedipalContract.MeasurementEntry.MEASUREMENT_TEMPERATURE, measurement.getTemperature());
        val.put(MedipalContract.MeasurementEntry.MEASUREMENT_MEASURED_ON, String.valueOf(measurement.getMeasuredOn()));
        Uri myUri = activity.getContentResolver().insert(MedipalContract.MeasurementEntry.CONTENT_URI_MEASUREMENT, val);
        activity.getContentResolver().notifyChange(MedipalContract.MeasurementEntry.CONTENT_URI_MEASUREMENT, null, false);

    }

}
