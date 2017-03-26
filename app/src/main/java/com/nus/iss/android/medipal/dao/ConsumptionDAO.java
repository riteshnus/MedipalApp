package com.nus.iss.android.medipal.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;

import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.dto.Appointment;
import com.nus.iss.android.medipal.dto.Consumption;

/**
 * Created by siddharth on 3/25/2017.
 */

public class ConsumptionDAO {

    private Activity activity;
    public  ConsumptionDAO(Activity activity){
        this.activity=activity;
    }

    public void save(Consumption consumption){
        ContentValues values=new ContentValues();
        values.put(MedipalContract.PersonalEntry.CONSUMPTION_MEDICINE_ID,consumption.getMedicine().getMedicineId());
        values.put(MedipalContract.PersonalEntry.CONSUMPTION_QUANTITY,String.valueOf(consumption.getQuantity()));
        values.put(MedipalContract.PersonalEntry.CONSUMPTION_CONSUMED_ON, String.valueOf(consumption.getConsumedOn()));
        Uri newUri=activity.getContentResolver().insert(MedipalContract.PersonalEntry.CONTENT_URI_CONSUMPTION,values);
        activity.getContentResolver().notifyChange(MedipalContract.PersonalEntry.CONTENT_URI_CONSUMPTION,null,false);
    }
}
