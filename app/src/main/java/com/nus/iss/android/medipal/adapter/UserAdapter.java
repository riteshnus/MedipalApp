package com.nus.iss.android.medipal.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.data.MedipalContract.PersonalEntry;
/**
 * Created by Ritesh on 3/9/2017.
 */

public class UserAdapter extends CursorAdapter{

    public UserAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.listview_item_layout, parent ,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        TextView tvAddress = (TextView) view.findViewById(R.id.tvAddress);

        String name = cursor.getString(cursor.getColumnIndex(PersonalEntry.USER_NAME));
        String address = cursor.getString(cursor.getColumnIndex(PersonalEntry.USER_ADDRESS));
        tvName.setText(name);
        tvAddress.setText(address);
    }
}
