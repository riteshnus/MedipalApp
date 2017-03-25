package com.nus.iss.android.medipal.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.R;

/**
 * Created by thushara on 3/23/2017.
 */

public class IceContactAdapter extends CursorAdapter {


    public IceContactAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.contact_list_item, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTextView= (TextView) view.findViewById(R.id.Ice_Name);
        int columnIndexForName=cursor.getColumnIndex(MedipalContract.PersonalEntry.ICE_NAME);
        String nameText=cursor.getString(columnIndexForName);
        nameTextView.setText(nameText);

        TextView numberTextView = (TextView) view.findViewById(R.id.Number);
        int columnIndexForNumber=cursor.getColumnIndex(MedipalContract.PersonalEntry.ICE_CONTACT_NUMBER);
        String numberText=cursor.getString(columnIndexForNumber);
        numberTextView.setText(numberText);

        
    }
}
