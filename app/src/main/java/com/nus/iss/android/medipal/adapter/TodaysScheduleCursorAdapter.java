package com.nus.iss.android.medipal.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.constants.Constants;
import com.nus.iss.android.medipal.data.MedipalContract;

/**
 * Created by Gautam on 25/03/17.
 */

public class TodaysScheduleCursorAdapter extends CursorAdapter {
    int mSection;

    public TodaysScheduleCursorAdapter(Context context, Cursor c, int flags, int section) {
        super(context, c, flags);
        mSection = section;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_today_schedule, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView desc = (TextView) view.findViewById(R.id.schedule_item);
        TextView time = (TextView) view.findViewById(R.id.schedule_time);
        TextView subtitle = (TextView) view.findViewById(R.id.schedule_subtitle_1);

        View item = view.findViewById(R.id.item_layout_today_scheduled);
        //TextView empty = (TextView) view.findViewById(R.id.empty_schedule_section);


        String rowSection = cursor.getString(cursor.getColumnIndex("Section"));
        if(mSection==1 && rowSection.equals("1")) {
            item.setVisibility(View.VISIBLE);
            //empty.setVisibility(View.GONE);
            desc.setText(
                    cursor.getString(
                            cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_MEDICINE_NAME))
            );
            time.setText(
                    cursor.getString(
                            cursor.getColumnIndex(Constants.MEDICINE_TIME)
                    )
            );
            subtitle.setText(
                    cursor.getString(
                            cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_CONSUME_QUANTITY)
                    ) + " " + getDosage(cursor)
            );
        }else if(mSection==2 && rowSection.equals("2")) {
            item.setVisibility(View.VISIBLE);
            //empty.setVisibility(View.GONE);
            desc.setText(
                    cursor.getString(
                            cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_MEDICINE_NAME))
            );
            time.setText(
                    cursor.getString(
                            cursor.getColumnIndex(Constants.MEDICINE_TIME)
                    )
            );
            subtitle.setText(
                    cursor.getString(
                            cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_CONSUME_QUANTITY)
                    ) + " " + getDosage(cursor)
            );
        }else if(mSection==3 && rowSection.equals("3")) {
            item.setVisibility(View.VISIBLE);
            //empty.setVisibility(View.GONE);
            desc.setText(
                    cursor.getString(
                            cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_MEDICINE_NAME))
            );
            time.setText(
                    cursor.getString(
                            cursor.getColumnIndex(Constants.MEDICINE_TIME)
                    )
            );
            subtitle.setText(
                    cursor.getString(
                            cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_CONSUME_QUANTITY)
                    ) + " " + getDosage(cursor)
            );
        }else if(mSection==4 && rowSection.equals("4")) {
            item.setVisibility(View.VISIBLE);
            //empty.setVisibility(View.GONE);
            desc.setText(
                    cursor.getString(
                            cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_MEDICINE_NAME))
            );
            time.setText(
                    cursor.getString(
                            cursor.getColumnIndex(Constants.MEDICINE_TIME)
                    )
            );
            subtitle.setText(
                    cursor.getString(
                            cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_CONSUME_QUANTITY)
                    ) + " " + getDosage(cursor)
            );
        }else{
            view.setVisibility(View.GONE);
        }

    }

    private String getDosage(Cursor cursor){
        String dosage = cursor.getString(
                cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_DOSAGE));

        if(dosage.equals("0")) return Constants.DOSAGE.pills.toString();
        if(dosage.equals("1")) return Constants.DOSAGE.cc.toString();
        if(dosage.equals("2")) return Constants.DOSAGE.ml.toString();
        if(dosage.equals("3")) return Constants.DOSAGE.gr.toString();
        if(dosage.equals("4")) return Constants.DOSAGE.mg.toString();
        if(dosage.equals("5")) return Constants.DOSAGE.drops.toString();
        if(dosage.equals("6")) return Constants.DOSAGE.pieces.toString();
        if(dosage.equals("7")) return Constants.DOSAGE.puffs.toString();
        if(dosage.equals("8")) return Constants.DOSAGE.units.toString();
        if(dosage.equals("9")) return Constants.DOSAGE.teaspoon.toString();
        if(dosage.equals("10")) return Constants.DOSAGE.tablespoon.toString();
        if(dosage.equals("11")) return Constants.DOSAGE.patch.toString();
        if(dosage.equals("12")) return Constants.DOSAGE.mcg.toString();
        if(dosage.equals("13")) return Constants.DOSAGE.l.toString();
        if(dosage.equals("14")) return Constants.DOSAGE.meq.toString();
        if(dosage.equals("15")) return Constants.DOSAGE.spray.toString();

        return "";
    }
}
