package com.nus.iss.android.medipal.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.helper.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by Medha Sharma on 15/3/2017.
 */

public class HistoryConsumptionAdapter extends CursorAdapter
{

    public HistoryConsumptionAdapter(Context context, Cursor c)
    {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate(R.layout.history_consumption_layout, parent ,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        TextView firstColumn = (TextView) view.findViewById(R.id.idMedName);
        TextView secondColumn = (TextView) view.findViewById(R.id.idDosage);
        TextView thirdColumn = (TextView) view.findViewById(R.id.idMedDate);
        TextView fourthColumn = (TextView) view.findViewById(R.id.idMedTime);
        ImageView imgTaken = (ImageView) view.findViewById(R.id.ivTakenMedicine);
        ImageView imgMissed = (ImageView) view.findViewById(R.id.ivMissedMedicine);

        Date dateOfConsumption;
        String timeOfConsumption;
        String time;


        String nameMedHistory = cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_MEDICINE_NAME));
        String dosageHistory = cursor.getString(cursor.getInt(0));
        String date = cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.CONSUMPTION_CONSUMED_ON));

        try
        {
            dateOfConsumption = Utils.converStringToDate(date);
            //date = Utils.convertDateToString(dateOfConsumption);
            DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            date = formatDate.format(dateOfConsumption);
            SimpleDateFormat date_format = new SimpleDateFormat("HH:mm");
            timeOfConsumption = date_format.format(dateOfConsumption.getTime());
            time = timeOfConsumption;

            //date = date + " " + time;
        }

        catch (ParseException e)
        {
            e.printStackTrace();
            return;
        }

        if(Objects.equals(dosageHistory, "0") || (dosageHistory == null))
        {
            imgMissed.setVisibility(View.VISIBLE);
            imgTaken.setVisibility(view.GONE);
        }

        if(!Objects.equals(dosageHistory, "0") && dosageHistory!=null)
        {
            imgTaken.setVisibility(View.VISIBLE);
            imgMissed.setVisibility(view.GONE);
        }

        firstColumn.setText(nameMedHistory);
        secondColumn.setText(dosageHistory);
        thirdColumn.setText(date);
        fourthColumn.setText(time);
    }
}
