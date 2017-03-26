package com.nus.iss.android.medipal.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;

import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.activity.ThresholdReminderActivity;
import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.data.MedipalDBHelper;
import com.nus.iss.android.medipal.dto.Consumption;
import com.nus.iss.android.medipal.dto.Medicine;
import com.nus.iss.android.medipal.helper.Utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by siddharth on 3/22/2017.
 */

public class NotificationIntentService extends IntentService {
      public static final String MYCLASS=NotificationIntentService.class.getSimpleName();
      private MedipalDBHelper dbHelper;

    public NotificationIntentService() {
        super(MYCLASS);
    }

    /*@Override
    public void onCreate() {
        super.onCreate();
        dbHelper=
    }*/

    @Override
    protected void onHandleIntent(Intent intent) {
        dbHelper  =new MedipalDBHelper(this);
        int totalConsumed=0;
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        List<Medicine> medicineList=getAllMedicines(db);
        if(medicineList!=null){
            for(Medicine medicine:medicineList){
                if(medicine.getConsumptionList()!=null){
                    for(Consumption consumption:medicine.getConsumptionList()){
                        totalConsumed=totalConsumed+consumption.getQuantity();
                    }
                    int quantityLeft=medicine.getQuantity()-totalConsumed;
                    if(quantityLeft<medicine.getThreshold()){
                        notificationForMedicineThreshold(medicine.getMedicine(),quantityLeft);
                    }
                }
            }
        }
    }


    private void notificationForMedicineThreshold(String name,int quantityLeft){
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(), ThresholdReminderActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        intent1.putExtra("quantityLeft",quantityLeft);
        intent1.putExtra("medicne",name);
        Notification mNotify = new Notification.Builder(this)
                .setContentTitle("Time to Refil " +name)
                .setContentText("Refile Medicine")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .setSound(sound)
                .build();

        mNM.notify(3, mNotify);
    }

    public List<Medicine> getAllMedicines(SQLiteDatabase db) {
        List<Medicine> medicineList = new ArrayList<>();
        String query = "SELECT DISTINCT " + MedipalContract.PersonalEntry.MEDICINE_ID + " , "
                + MedipalContract.PersonalEntry.MEDICINE_QUANTITY
                + ", " + MedipalContract.PersonalEntry.MEDICINE_MEDICINE_NAME
                + ", " + MedipalContract.PersonalEntry.MEDICINE_CONSUME_QUANTITY
                + " FROM " + MedipalContract.PersonalEntry.MEDICINE_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null, null);
        int columnIndexForMedicineId = cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_ID);
        int columnIndexForMedicineName = cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_MEDICINE_NAME);
        int columnIndexForMedicineQuantity = cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_QUANTITY);
        int columnIndexForMedicineConsumeQuantity = cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_CONSUME_QUANTITY);
       if(cursor.moveToFirst()) {
           while (cursor.moveToNext()) {
               Medicine medicine = new Medicine();
               String name = cursor.getString(columnIndexForMedicineName);
               int id = cursor.getInt(columnIndexForMedicineId);
               int quantity = cursor.getInt(columnIndexForMedicineQuantity);
               int consumeQuantity = cursor.getInt(columnIndexForMedicineConsumeQuantity);
               medicine.setMedicineId(id);
               medicine.setMedicine(name);
               medicine.setQuantity(quantity);
               medicine.setConsumeQuantity(consumeQuantity);
               medicineList.add(medicine);
           }
       }
        cursor.close();
        for (Medicine medicne : medicineList) {
            // Uri consumptionUri= ContentUris.withAppendedId(MedipalContract.PersonalEntry.CONTENT_URI_CONSUMPTION,medicne.getMedicineId());
            String queryForConsumption = "SELECT " + MedipalContract.PersonalEntry.CONSUMPTION_CONSUMED_ON
                    + ", " + MedipalContract.PersonalEntry.CONSUMPTION_QUANTITY
                    + " FROM " + MedipalContract.PersonalEntry.CONSUMPTION_TABLE_NAME
                    + " WHERE " + MedipalContract.PersonalEntry.CONSUMPTION_MEDICINE_ID + " =?";
            String[] selectionArgs = new String[]{String.valueOf(medicne.getMedicineId())};
            Cursor cursorForConsumption = db.rawQuery(queryForConsumption, selectionArgs, null);
            int columnIndxForConsumedOn = cursorForConsumption.getColumnIndex(MedipalContract.PersonalEntry.CONSUMPTION_CONSUMED_ON);
            int columnIndxForQuantity = cursorForConsumption.getColumnIndex(MedipalContract.PersonalEntry.CONSUMPTION_QUANTITY);
            if(cursorForConsumption.moveToFirst()) {
                while (cursorForConsumption.moveToNext()) {
                    int quantity = cursorForConsumption.getInt(columnIndxForQuantity);
                    String consumedOn = cursorForConsumption.getString(columnIndxForConsumedOn);
                    Date consumeDate = null;
                    try {
                        consumeDate = Utils.converStringToDate(consumedOn);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Consumption consumption = new Consumption(quantity, consumeDate);
                    medicne.addConsumption(consumption);
                }
            }

        }
        db.close();
        return medicineList;
    }
}
