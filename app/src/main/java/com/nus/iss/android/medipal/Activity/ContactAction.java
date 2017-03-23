package com.nus.iss.android.medipal.Activity;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.nus.iss.android.medipal.Data.MedipalContract;
import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.dao.ContactDAO;

import java.util.Date;

/**
 * Created by Ritesh on 3/23/2017.
 */

public class ContactAction extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private Uri mCurrentContactUri;
    private TextView nameTextView;
    private TextView contactTextView;
    private TextView priorityTextView;
    private TextView typeTextView;
    private TextView descriptionView;

    private static final int ICE_CONTACT_LOADER = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_view);
        Intent intent = getIntent();
        mCurrentContactUri = intent.getData();
        nameTextView = (TextView) findViewById(R.id.tv_contact_name);
        contactTextView = (TextView) findViewById(R.id.tv_contact_number);
        priorityTextView = (TextView) findViewById(R.id.tv_contact_priority);
        typeTextView = (TextView) findViewById(R.id.tv_contact_type);
        descriptionView = (TextView) findViewById(R.id.tv_contact_description);

        getLoaderManager().initLoader(ICE_CONTACT_LOADER, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contact_view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                deletePopup();
                return true;
            case R.id.action_edit:
                Intent myIntent = new Intent(this, AddContactActivity.class);
                myIntent.setData(mCurrentContactUri);
                startActivity(myIntent);
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case ICE_CONTACT_LOADER:
                String[] projectionForContact = {
                        MedipalContract.PersonalEntry.ICE_ID,
                        MedipalContract.PersonalEntry.ICE_NAME,
                        MedipalContract.PersonalEntry.ICE_CONTACT_NUMBER,
                        MedipalContract.PersonalEntry.ICE_CONTACT_TYPE,
                        MedipalContract.PersonalEntry.ICE_SEQUENCE,
                        MedipalContract.PersonalEntry.ICE_DESCRIPTION,
                };
                return new CursorLoader(this, mCurrentContactUri, projectionForContact, null, null, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Date time;
        switch (loader.getId()) {
            case ICE_CONTACT_LOADER:
                if (cursor.moveToFirst()) {
                    String contactName = cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.ICE_NAME));
                    String contactNumber = cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.ICE_CONTACT_NUMBER));
                    String contactType = cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.ICE_CONTACT_TYPE));
                    String priority = cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.ICE_SEQUENCE));
                    String contactDescription = cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.ICE_DESCRIPTION));
                    nameTextView.setText(contactName);
                    contactTextView.setText(contactNumber);
                    typeTextView.setText(contactType);
                    priorityTextView.setText(priority);
                    descriptionView.setText(contactDescription);
                    break;
                }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void deletePopup() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to delete?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteEntry();
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void deleteEntry(){
        int rowsAffected;
        ContactDAO contactDAO = new ContactDAO(this);
        rowsAffected = contactDAO.delete(mCurrentContactUri);
        if (rowsAffected == 0) {
            Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Record deleted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,ICEContactList.class);
            startActivity(intent);
        }
    }

}
