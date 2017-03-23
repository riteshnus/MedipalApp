package com.nus.iss.android.medipal.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;

import com.nus.iss.android.medipal.Data.MedipalContract;
import com.nus.iss.android.medipal.dto.ICEContact;

/**
 * Created by thushara on 3/23/2017.
 */

public class ContactDAO {

    private Activity activity;
    public ContactDAO(Activity activity){
        this.activity=activity;
    }

    public void saveIce(ICEContact iceContact){
        ContentValues val= new ContentValues();
        val.put(MedipalContract.PersonalEntry.ICE_NAME, iceContact.getName());
        val.put(MedipalContract.PersonalEntry.ICE_CONTACT_NUMBER, iceContact.getContactNo());
        val.put(MedipalContract.PersonalEntry.ICE_SEQUENCE, iceContact.getSequence());
        val.put(MedipalContract.PersonalEntry.ICE_CONTACT_TYPE, iceContact.getContactType());
        val.put(MedipalContract.PersonalEntry.ICE_DESCRIPTION, iceContact.getDescription());
        Uri myUri = activity.getContentResolver().insert(MedipalContract.PersonalEntry.CONTENT_URI_CONTACT,val);
        activity.getContentResolver().notifyChange(MedipalContract.PersonalEntry.CONTENT_URI_CONTACT,null,false);
    }

    public int update(ICEContact iceContact, Uri mCurrentUri){
        ContentValues values=new ContentValues();
        values.put(MedipalContract.PersonalEntry.ICE_NAME, iceContact.getName());
        values.put(MedipalContract.PersonalEntry.ICE_CONTACT_NUMBER, iceContact.getContactNo());
        values.put(MedipalContract.PersonalEntry.ICE_SEQUENCE, iceContact.getSequence());
        values.put(MedipalContract.PersonalEntry.ICE_CONTACT_TYPE, iceContact.getContactType());
        values.put(MedipalContract.PersonalEntry.ICE_DESCRIPTION, iceContact.getDescription());
        int rowsAffected =activity.getContentResolver().update(mCurrentUri,values,null,null);
        activity.getContentResolver().notifyChange(MedipalContract.PersonalEntry.CONTENT_URI_CONTACT,null,false);
        return rowsAffected;
    }

    public int delete(Uri mCurrentUri){
        int rowsAffected =activity.getContentResolver().delete(mCurrentUri,null,null);
        activity.getContentResolver().notifyChange(MedipalContract.PersonalEntry.CONTENT_URI_CONTACT,null,false);
        return rowsAffected;
    }
}
