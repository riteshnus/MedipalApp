package com.nus.iss.android.medipal.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.Manifest;
import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.activity.ICEContactList;
import com.nus.iss.android.medipal.data.MedipalContract;

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
    public void bindView(final View view, final Context context, Cursor cursor) {
        TextView nameTextView = (TextView) view.findViewById(R.id.Ice_Name);
        int columnIndexForName = cursor.getColumnIndex(MedipalContract.PersonalEntry.ICE_NAME);
        String nameText = cursor.getString(columnIndexForName);
        nameTextView.setText(nameText);

        final TextView numberTextView = (TextView) view.findViewById(R.id.Number);
        int columnIndexForNumber = cursor.getColumnIndex(MedipalContract.PersonalEntry.ICE_CONTACT_NUMBER);
        final String numberText = cursor.getString(columnIndexForNumber);
        numberTextView.setText(numberText);

    /* author="Medha Sharma" date="25-03-2017" action="Addition"
     description: Call and message button functionality added*/
        ImageView ivCall = (ImageView) view.findViewById(R.id.ivCall);
        final ImageView ivMsg = (ImageView) view.findViewById(R.id.ivMsg);

        final Intent callIntent = new Intent(Intent.ACTION_CALL);
        ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callIntent.setData(Uri.parse("tel:" + numberTextView.getText().toString()));
                if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((ICEContactList) view.getContext(),
                            new String[]{Manifest.permission.CALL_PHONE},   //request specific permission from user
                            10);
                    return;
                } else {
                    view.getContext().startActivity(callIntent);
                }
            }
        });

        ivMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent msgIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + numberText));
                msgIntent.putExtra("sms_body", context.getString(R.string.Help));
                view.getContext().startActivity(msgIntent);
            }
        });
    }
}
