package com.nus.iss.android.medipal.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.helper.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ritesh on 3/23/2017.
 */

public class AppointmentAdapter extends CursorAdapter {
    public AppointmentAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.appointment_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String dateFormat = "EEE, d MMM, HH:mm a";
        SimpleDateFormat sdfDate = new SimpleDateFormat(dateFormat);
        TextView tvTittle = (TextView) view.findViewById(R.id.tv_appt_tittle);
        TextView tvTime = (TextView) view.findViewById(R.id.tv_appt_time);
        String tittle = cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.APPOINTMENT_DESCRIPTION));
        Date time;
        try {
            time = Utils.converStringToDate(cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.APPOINTMENT_DATE_TIME)));
            tvTime.setText(sdfDate.format(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvTittle.setText(tittle);
    }
}
