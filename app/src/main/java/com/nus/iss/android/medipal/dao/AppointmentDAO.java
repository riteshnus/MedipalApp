package com.nus.iss.android.medipal.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;

import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.dto.Appointment;

/**
 * Created by Ritesh on 3/22/2017.
 */

public class AppointmentDAO {

    private Activity activity;
    public  AppointmentDAO(Activity activity){
        this.activity=activity;
    }

    public void save(Appointment appointment){
        ContentValues values=new ContentValues();
        values.put(MedipalContract.PersonalEntry.APPOINTMENT_LOCATION,appointment.getLocation());
        values.put(MedipalContract.PersonalEntry.APPOINTMENT_DATE_TIME,String.valueOf(appointment.getAppontmentTime()));
        values.put(MedipalContract.PersonalEntry.APPOINTMENT_DESCRIPTION,appointment.getDescription());
        Uri newUri=activity.getContentResolver().insert(MedipalContract.PersonalEntry.CONTENT_URI_APPOINTMENT,values);
        activity.getContentResolver().notifyChange(MedipalContract.PersonalEntry.CONTENT_URI_APPOINTMENT,null,false);
    }

    public int update(Appointment appointment, Uri mCurrentUri){
        ContentValues values=new ContentValues();
        values.put(MedipalContract.PersonalEntry.APPOINTMENT_LOCATION,appointment.getLocation());
        values.put(MedipalContract.PersonalEntry.APPOINTMENT_DATE_TIME,String.valueOf(appointment.getAppontmentTime()));
        values.put(MedipalContract.PersonalEntry.APPOINTMENT_DESCRIPTION,appointment.getDescription());
        int rowsAffected =activity.getContentResolver().update(mCurrentUri,values,null,null);
        activity.getContentResolver().notifyChange(MedipalContract.PersonalEntry.CONTENT_URI_APPOINTMENT,null,false);
        return rowsAffected;
    }

    public int delete(Uri mCurrentUri){
        int rowsAffected =activity.getContentResolver().delete(mCurrentUri,null,null);
        activity.getContentResolver().notifyChange(MedipalContract.PersonalEntry.CONTENT_URI_APPOINTMENT,null,false);
        return rowsAffected;
    }
}
