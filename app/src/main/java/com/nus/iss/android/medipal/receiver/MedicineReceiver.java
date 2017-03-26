package com.nus.iss.android.medipal.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.activity.ReminderActivity;
import com.nus.iss.android.medipal.data.MedipalContract;

import static android.R.id.message;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by siddharth on 3/23/2017.
 */

public class MedicineReceiver extends BroadcastReceiver {
    private int uniqueId=0;
    @Override
    public void onReceive(Context context, Intent intent) {

        String medicine = intent.getExtras().getString("medicine");
        int id=intent.getExtras().getInt("id");
        Log.i("medicine"," "+medicine);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager mNM = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(context, ReminderActivity.class);
        intent1.putExtra("medicineName",medicine);
        // intent1.putExtra("id",id);
        intent1.setData(ContentUris.withAppendedId(MedipalContract.PersonalEntry.CONTENT_URI_MEDICINE,id));
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification mNotify = new Notification.Builder(context)
                .setContentTitle("Time to Take " + medicine)
                .setContentText("Take " +medicine)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .setSound(sound)
                .build();

        mNM.notify(0, mNotify);

    }

}
