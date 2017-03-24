package com.nus.iss.android.medipal.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import com.nus.iss.android.medipal.activity.AddAppointmentActivity;
import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.data.MedipalContract;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by siddharth on 3/23/2017.
 */

public class AppointmentReceiver extends BroadcastReceiver {
     private int uniqueId=0;
    @Override
    public void onReceive(Context context, Intent intent) {

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager mNM = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(context, AddAppointmentActivity.class);

        intent1.setData(MedipalContract.PersonalEntry.CONTENT_URI_MEDICINE);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification mNotify = new Notification.Builder(context)
                .setContentTitle("Time to go to clinic")
                .setContentText("Check prerequisite")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .setSound(sound)
                .build();

        mNM.notify(1, mNotify);
    }
}
