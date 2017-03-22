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

/**
 * Created by siddharth on 3/16/2017.
 */

public class MedicineAdapter extends CursorAdapter {

    public MedicineAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.medicine_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTextView= (TextView) view.findViewById(R.id.medicine_name);
        int columnIndexForName=cursor.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_MEDICINE_NAME);
        String nameText=cursor.getString(columnIndexForName);
        nameTextView.setText(nameText);



    }
}
