package com.nus.iss.android.medipal.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.activity.AppointmentActivity;
import com.nus.iss.android.medipal.activity.ReminderActivity;
import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.dto.Medicine;

import static android.R.attr.id;

/**
 * Created by siddharth on 3/22/2017.
 */

public class NotificationIntentService extends IntentService {
      public static final String MYCLASS=NotificationIntentService.class.getSimpleName();

    public NotificationIntentService() {
        super(MYCLASS);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Medicine medicine= (Medicine) intent.getSerializableExtra("mediicne");

        Log.v(MYCLASS,"Id recieved by notification " +  medicine);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        int id=intent.getIntExtra("medicneId",-1);
        Uri medicneUri=intent.getData();
        Log.v(MYCLASS,"Id recieved by notification by Bind Method " +  id);
        return super.onBind(intent);
    }

    private void notificationForMedicine(){
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(), ReminderActivity.class);

        intent1.setData(MedipalContract.PersonalEntry.CONTENT_URI_MEDICINE);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification mNotify = new Notification.Builder(this)
                .setContentTitle("Time to Take Medicines")
                .setContentText("Take Paracetamol")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .setSound(sound)
                .build();

        mNM.notify(1, mNotify);
    }
    private void notificationForAppointment(){
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(), AppointmentActivity.class);

        intent1.setData(MedipalContract.PersonalEntry.CONTENT_URI_MEDICINE);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification mNotify = new Notification.Builder(this)
                .setContentTitle("Appointment Time")
                .setContentText("Go to Clinic")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .setSound(sound)
                .build();

        mNM.notify(2, mNotify);
    }
    private void notificationForMedicineThreshold(){
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(), ReminderActivity.class);

        intent1.setData(MedipalContract.PersonalEntry.CONTENT_URI_MEDICINE);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification mNotify = new Notification.Builder(this)
                .setContentTitle("Time to Refil Medicine")
                .setContentText("Refile Medicine")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .setSound(sound)
                .build();

        mNM.notify(3, mNotify);
    }


}
