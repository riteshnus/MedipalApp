package com.nus.iss.android.medipal.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;

import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.dto.PersonalBio;

/**
 * Created by Gautam on 23/03/17.
 */

public class PersonalBioDAO {
    public Activity activity;

    public PersonalBioDAO(Activity activity) {
        this.activity = activity;
    }

    public Uri save(PersonalBio personalBio){
        ContentValues values=new ContentValues();
        values.put(MedipalContract.PersonalEntry.USER_ADDRESS,personalBio.getmAddress());
        values.put(MedipalContract.PersonalEntry.USER_BLOOD_TYPE,personalBio.getmBloodType());
        values.put(MedipalContract.PersonalEntry.USER_DOB, String.valueOf(personalBio.getmDOB()));
        values.put(MedipalContract.PersonalEntry.USER_HEIGHT,personalBio.getmHeight());
        values.put(MedipalContract.PersonalEntry.USER_ID_NO,personalBio.getmIDNo());
        values.put(MedipalContract.PersonalEntry.USER_NAME,personalBio.getmName());
        values.put(MedipalContract.PersonalEntry.USER_POSTAL_CODE,personalBio.getmPostalCode());
        Uri uri = activity.getContentResolver().insert(MedipalContract.PersonalEntry.CONTENT_URI_PERSONAL,values);
        activity.getContentResolver().notifyChange(MedipalContract.PersonalEntry.CONTENT_URI_PERSONAL,null,false);
        return uri;
    }

    public int update(PersonalBio personalBio, String where, String[]selectionArgs){
        ContentValues values=new ContentValues();
        values.put(MedipalContract.PersonalEntry.USER_ADDRESS,personalBio.getmAddress());
        values.put(MedipalContract.PersonalEntry.USER_BLOOD_TYPE,personalBio.getmBloodType());
        values.put(MedipalContract.PersonalEntry.USER_DOB, String.valueOf(personalBio.getmDOB()));
        values.put(MedipalContract.PersonalEntry.USER_HEIGHT,personalBio.getmHeight());
        values.put(MedipalContract.PersonalEntry.USER_ID_NO,personalBio.getmIDNo());
        values.put(MedipalContract.PersonalEntry.USER_NAME,personalBio.getmName());
        values.put(MedipalContract.PersonalEntry.USER_POSTAL_CODE,personalBio.getmPostalCode());

        int rowsUpdated = activity.getContentResolver().update(MedipalContract.PersonalEntry.CONTENT_URI_PERSONAL,values, where,selectionArgs);
        activity.getContentResolver().notifyChange(MedipalContract.PersonalEntry.CONTENT_URI_PERSONAL,null,false);

        return rowsUpdated;
    }
}
