package com.nus.iss.android.medipal.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.activity.ReminderActivityAppointment;
import com.nus.iss.android.medipal.data.MedipalContract;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Ritesh on 3/25/2017.
 */

public class AppointmentReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager mNM = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Intent appReminderIntent = new Intent(context, ReminderActivityAppointment.class);
        String appointment = intent.getExtras().getString("appointment");
        String place = intent.getExtras().getString("place");
        String timeAppt = intent.getExtras().getString("timeAppt");
        Log.i("appointment"," "+appointment+" place: "+place+" time"+timeAppt);
        appReminderIntent.putExtra("appointment",appointment);
        appReminderIntent.putExtra("place",place);
        appReminderIntent.putExtra("timeAppt",timeAppt);

        appReminderIntent.setData(MedipalContract.PersonalEntry.CONTENT_URI_APPOINTMENT);
        PendingIntent pIntent = PendingIntent.getActivity(context, 1, appReminderIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification mNotify = new Notification.Builder(context)
                .setContentTitle("Time to go to "+place)
                .setContentText(appointment+" at "+ timeAppt)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .setSound(sound)
                .build();

        mNM.notify(1, mNotify);
    }
}
