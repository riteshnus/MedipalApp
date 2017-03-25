package com.nus.iss.android.medipal.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nus.iss.android.medipal.R;
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
                            cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_TIME)
                    )
            );
            subtitle.setText(
                    cursor.getString(
                            cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_CONSUME_QUANTITY)
                    ) + cursor.getString(
                            cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_DOSAGE)
                    )
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
                            cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_TIME)
                    )
            );
            subtitle.setText(
                    cursor.getString(
                            cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_CONSUME_QUANTITY)
                    ) + cursor.getString(
                            cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_DOSAGE)
                    )
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
                            cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_TIME)
                    )
            );
            subtitle.setText(
                    cursor.getString(
                            cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_CONSUME_QUANTITY)
                    ) + cursor.getString(
                            cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_DOSAGE)
                    )
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
                            cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_TIME)
                    )
            );
            subtitle.setText(
                    cursor.getString(
                            cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_CONSUME_QUANTITY)
                    ) + cursor.getString(
                            cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_DOSAGE)
                    )
            );
        }else{
            view.setVisibility(View.GONE);
        }

    }
}
