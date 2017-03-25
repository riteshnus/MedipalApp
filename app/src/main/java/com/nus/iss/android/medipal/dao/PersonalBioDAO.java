package com.nus.iss.android.medipal.dao;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v4.app.Fragment;

import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.dto.PersonalBio;

/**
 * Created by Gautam on 23/03/17.
 */

public class PersonalBioDAO {
    public Fragment fragment;

    public PersonalBioDAO(Fragment fragment) {
        this.fragment = fragment;
    }

    public void save(PersonalBio personalBio){
        ContentValues values=new ContentValues();
        values.put(MedipalContract.PersonalEntry.USER_ADDRESS,personalBio.getmAddress());
        values.put(MedipalContract.PersonalEntry.USER_BLOOD_TYPE,personalBio.getmBloodType());
        values.put(MedipalContract.PersonalEntry.USER_DOB, String.valueOf(personalBio.getmDOB()));
        values.put(MedipalContract.PersonalEntry.USER_HEIGHT,personalBio.getmHeight());
        values.put(MedipalContract.PersonalEntry.USER_ID_NO,personalBio.getmIDNo());
        values.put(MedipalContract.PersonalEntry.USER_NAME,personalBio.getmName());
        values.put(MedipalContract.PersonalEntry.USER_POSTAL_CODE,personalBio.getmPostalCode());

        Uri uri = fragment.getActivity().getContentResolver().insert(MedipalContract.PersonalEntry.CONTENT_URI_PERSONAL,values);
        fragment.getActivity().getContentResolver().notifyChange(MedipalContract.PersonalEntry.CONTENT_URI_PERSONAL,null,false);
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

        int rowsUpdated = fragment.getActivity().getContentResolver().update(MedipalContract.PersonalEntry.CONTENT_URI_PERSONAL,values, where,selectionArgs);
        fragment.getActivity().getContentResolver().notifyChange(MedipalContract.PersonalEntry.CONTENT_URI_PERSONAL,null,false);

        return rowsUpdated;
    }
}
