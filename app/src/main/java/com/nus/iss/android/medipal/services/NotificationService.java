package com.nus.iss.android.medipal.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.activity.AddMedicineActivity;
import com.nus.iss.android.medipal.activity.CurrentMedicineActivity;
import com.nus.iss.android.medipal.activity.MainActivity;
import com.nus.iss.android.medipal.activity.ReminderActivity;
import com.nus.iss.android.medipal.data.MedipalContract;

import static com.nus.iss.android.medipal.R.mipmap.ic_launcher;

public class NotificationService extends Service {
    public NotificationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        NotificationManager mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(), ReminderActivity.class);
       // intent1.putExtra("uri" , MedipalContract.PersonalEntry.CONTENT_URI_MEDICINE);
        intent1.setData(MedipalContract.PersonalEntry.CONTENT_URI_MEDICINE);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_CANCEL_CURRENT);
        // pIntent.send
        Notification mNotify = new Notification.Builder(this)
                .setContentTitle("Time to Take Medicines")
                .setContentText("Take Paracetamol")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setSound(sound)
                .build();

        mNM.notify(1, mNotify);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
