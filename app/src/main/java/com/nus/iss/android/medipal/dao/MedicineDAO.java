package com.nus.iss.android.medipal.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;

import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.dto.Medicine;

import static com.nus.iss.android.medipal.data.MedipalContract.PersonalEntry;

/**
 * Created by siddharth on 3/14/2017.
 */

public class MedicineDAO {



    private Activity activity;
    public  MedicineDAO(Activity activity){
       this.activity=activity;
    }

    public void save(Medicine medicine){
        ContentValues values=new ContentValues();
        values.put(MedipalContract.MedicineEntry.MEDICINE_MEDICINE_NAME,medicine.getMedicine());
        values.put(MedipalContract.MedicineEntry.MEDICINE_DESCRIPTION,medicine.getDescription());
        values.put(MedipalContract.MedicineEntry.MEDICINE_CONSUME_QUANTITY,medicine.getConsumeQuantity());
        values.put(MedipalContract.MedicineEntry.MEDICINE_DOSAGE,medicine.getDosage());
        values.put(MedipalContract.MedicineEntry.MEDICINE_DATE_ISSUED, String.valueOf(medicine.getDateIssued()));
        values.put(MedipalContract.MedicineEntry.MEDICINE_EXPIRE_FACTOR,medicine.getExpireFactor());
        values.put(MedipalContract.MedicineEntry.MEDICINE_REMIND,medicine.isRemind());
        values.put(MedipalContract.MedicineEntry.MEDICINE_QUANTITY,medicine.getQuantity());
        values.put(MedipalContract.MedicineEntry.MEDICINE_THRESHOLD,medicine.getThreshold());
        values.put(MedipalContract.MedicineEntry.MEDICINE_CATID,medicine.getCategory().getCategoryId());
        values.put(MedipalContract.MedicineEntry.MEDICINE_REMINDERID,medicine.getReminder().getReminderId());
        Uri newUri=activity.getContentResolver().insert(MedipalContract.MedicineEntry.CONTENT_URI_MEDICINE,values);
        activity.getContentResolver().notifyChange(MedipalContract.MedicineEntry.CONTENT_URI_MEDICINE,null,false);
    }
}
