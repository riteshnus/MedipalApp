package com.nus.iss.android.medipal.dao;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;

import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.dto.Reminder;

/**
 * Created by siddharth on 3/14/2017.
 */

public class ReminderDAO {
    private Activity activity;

    public ReminderDAO(Activity activity) {
        this.activity = activity;
    }

    public Reminder save(Reminder reminder){
        ContentValues values=new ContentValues();
        values.put(MedipalContract.ReminderEntry.REMINDER_FREQUENCY,reminder.getFrequency());
        values.put(MedipalContract.ReminderEntry.REMINDER_INTERVAL,reminder.getInterval());
        values.put(MedipalContract.ReminderEntry.REMINDER_START_TIME, String.valueOf(reminder.getStartTime()));
        Uri newUri=activity.getContentResolver().insert(MedipalContract.ReminderEntry.CONTENT_URI_REMINDER,values);
        int id= (int) ContentUris.parseId(newUri);
        reminder.setReminderId(id);
        return reminder;
    }
}
