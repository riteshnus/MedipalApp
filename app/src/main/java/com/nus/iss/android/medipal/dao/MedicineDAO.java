package com.nus.iss.android.medipal.dao;

import android.app.Activity;
import android.content.ContentUris;
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
        values.put(PersonalEntry.MEDICINE_MEDICINE_NAME,medicine.getMedicine());
        values.put(PersonalEntry.MEDICINE_DESCRIPTION,medicine.getDescription());
        values.put(PersonalEntry.MEDICINE_CONSUME_QUANTITY,medicine.getConsumeQuantity());
        values.put(PersonalEntry.MEDICINE_DOSAGE,medicine.getDosage());
        values.put(PersonalEntry.MEDICINE_DATE_ISSUED, String.valueOf(medicine.getDateIssued()));
        values.put(PersonalEntry.MEDICINE_EXPIRE_FACTOR,medicine.getExpireFactor());
        values.put(PersonalEntry.MEDICINE_REMIND,medicine.isRemind());
        values.put(PersonalEntry.MEDICINE_QUANTITY,medicine.getQuantity());
        values.put(PersonalEntry.MEDICINE_THRESHOLD,medicine.getThreshold());
        values.put(PersonalEntry.MEDICINE_CATID,medicine.getCategory().getCategoryId());
        if(medicine.getReminder()!=null) {
            values.put(PersonalEntry.MEDICINE_REMINDERID, medicine.getReminder().getReminderId());
        }
        Uri newUri=activity.getContentResolver().insert(PersonalEntry.CONTENT_URI_MEDICINE,values);
        medicine.setMedicineId((int) ContentUris.parseId(newUri));
        activity.getContentResolver().notifyChange(PersonalEntry.CONTENT_URI_MEDICINE,null,false);
    }

    public void update(Medicine medicine, Uri medicineUri){
        ContentValues values=new ContentValues();
        values.put(PersonalEntry.MEDICINE_MEDICINE_NAME,medicine.getMedicine());
        values.put(PersonalEntry.MEDICINE_DESCRIPTION,medicine.getDescription());
        values.put(PersonalEntry.MEDICINE_CONSUME_QUANTITY,medicine.getConsumeQuantity());
        values.put(PersonalEntry.MEDICINE_DOSAGE,medicine.getDosage());
        values.put(PersonalEntry.MEDICINE_DATE_ISSUED, String.valueOf(medicine.getDateIssued()));
        values.put(PersonalEntry.MEDICINE_EXPIRE_FACTOR,medicine.getExpireFactor());
        values.put(PersonalEntry.MEDICINE_REMIND,medicine.isRemind());
        values.put(PersonalEntry.MEDICINE_QUANTITY,medicine.getQuantity());
        values.put(PersonalEntry.MEDICINE_THRESHOLD,medicine.getThreshold());
        values.put(PersonalEntry.MEDICINE_CATID,medicine.getCategory().getCategoryId());
        if(medicine.getReminder()!=null) {
            values.put(PersonalEntry.MEDICINE_REMINDERID, medicine.getReminder().getReminderId());
        }
        int rowUpdate=activity.getContentResolver().update(medicineUri,values,null,null);
        activity.getContentResolver().notifyChange(medicineUri,null,false);
    }
}
