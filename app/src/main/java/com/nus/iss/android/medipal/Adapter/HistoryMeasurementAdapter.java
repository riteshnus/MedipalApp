package com.nus.iss.android.medipal.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.Data.MedipalContract;
import com.nus.iss.android.medipal.helper.Utils;


import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Medha Sharma on 15/3/2017.
 */

public class HistoryMeasurementAdapter extends CursorAdapter
{

    public HistoryMeasurementAdapter(Context context, Cursor c)
    {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate(R.layout.history_measurement_layout, parent ,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        TextView typeMeasure = (TextView) view.findViewById(R.id.idMedName);
        TextView valueMeasure = (TextView) view.findViewById(R.id.idDosage);
        TextView dateMeasure = (TextView) view.findViewById(R.id.idMedDate);

        String timeMeasure, bpDiastolic = null, bpSystolic = null, pulse = null, temp = null, weight = null, type = null, value = null, date = null;
        Date dateMeasurement;
        Long timeMeasurement;

        if(cursor.getColumnIndex(MedipalContract.PersonalEntry.MEASUREMENT_DIASTOLIC) != -1)
        {
            bpDiastolic = cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.MEASUREMENT_DIASTOLIC));
        }

        if(cursor.getColumnIndex(MedipalContract.PersonalEntry.MEASUREMENT_SYSTOLIC) != -1)
        {
            bpSystolic = cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.MEASUREMENT_SYSTOLIC));
        }

        if(cursor.getColumnIndex(MedipalContract.PersonalEntry.MEASUREMENT_PULSE) != -1)
        {
            pulse = cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.MEASUREMENT_PULSE));
        }

        if(cursor.getColumnIndex(MedipalContract.PersonalEntry.MEASUREMENT_TEMPERATURE) != -1)
        {
            temp = cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.MEASUREMENT_TEMPERATURE));
        }

        if(cursor.getColumnIndex(MedipalContract.PersonalEntry.MEASUREMENT_WEIGHT) != -1)
        {
            weight = cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.MEASUREMENT_WEIGHT));
        }

        if(cursor.getColumnIndex(MedipalContract.PersonalEntry.MEASUREMENT_MEASURED_ON) != -1)
        {
            date = cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.MEASUREMENT_MEASURED_ON));
            try
            {
                dateMeasurement = Utils.converStringToDate(date);
                DateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                date = formatDate.format(dateMeasurement);
                //date = Utils.convertDateToString(dateMeasurement);

//                timeMeasurement = dateMeasurement.getTime();
//                timeMeasure = timeMeasurement.toString();

                //date = date + timeMeasure;

            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }

        if(bpSystolic != null && !bpSystolic.isEmpty())
        {
            type = "Blood Pressure : Systolic";
            value = bpSystolic;
        }

        if(bpDiastolic != null && !bpDiastolic.isEmpty())
        {
            type = type + " / Diastolic";
            value = bpSystolic + " / " + bpDiastolic;
        }
        else if(pulse != null && !pulse.isEmpty())
        {
            type = "Pulse";
            value = pulse;
        }
        else if(temp != null && !temp.isEmpty())
        {
            type = "Temperature";
            value = temp;
            Double d = Double.parseDouble(value);
            DecimalFormat df2 = new DecimalFormat(".#");
            value = df2.format(d);

        }
        else if(weight != null && !weight.isEmpty())
        {
            type = "Weight";
            value = weight;
        }

        typeMeasure.setText(type);
        valueMeasure.setText(value);
        dateMeasure.setText(date);
    }
}
