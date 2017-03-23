package com.nus.iss.android.medipal.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nus.iss.android.medipal.R;

/**
 * Created by Shubhanshu Gautam on 19/03/17.
 */

public class HealthBioAdapter extends CursorAdapter {

    public HealthBioAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_health_bio, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
