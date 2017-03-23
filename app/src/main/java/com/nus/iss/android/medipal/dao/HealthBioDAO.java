package com.nus.iss.android.medipal.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.support.v4.app.Fragment;

import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.dto.HealthBio;

/**
 * Created by Gautam on 23/03/17.
 */

public class HealthBioDAO {
    public Activity activity;

    public HealthBioDAO(Activity activity) {
        this.activity = activity;
    }

    public void save(HealthBio healthBio){
        ContentValues values=new ContentValues();
        values.put(MedipalContract.HealthBioEntry.HEALTH_CONDITION,healthBio.getCondition());
        values.put(MedipalContract.HealthBioEntry.HEALTH_CONDITION_TYPE,healthBio.getConditionType());
        values.put(MedipalContract.HealthBioEntry.HEALTH_START_DATE,String.valueOf(healthBio.getStartDate()));

        Uri uri = activity.getContentResolver().insert(MedipalContract.HealthBioEntry.CONTENT_URI_HEALTH_BIO,values);
        activity.getContentResolver().notifyChange(MedipalContract.HealthBioEntry.CONTENT_URI_HEALTH_BIO,null,false);
    }
}
