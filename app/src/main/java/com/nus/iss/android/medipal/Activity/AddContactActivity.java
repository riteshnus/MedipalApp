package com.nus.iss.android.medipal.Activity;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nus.iss.android.medipal.Data.MedipalContract;
import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.dao.ContactDAO;
import com.nus.iss.android.medipal.dto.ICEContact;

import static com.nus.iss.android.medipal.Activity.AppointmentList.APPOINTMENT_LOADER;

/**
 * Created by Ritesh on 3/18/2017.
 */

public class AddContactActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Uri mCurrentContactUri;
    private EditText nameTextView;
    private EditText contactTextView;
    private TextView priorityTextView;
    private TextView typeTextView;
    private EditText description;

    private static final int ICE_CONTACT_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail_form);
        nameTextView = (EditText) findViewById(R.id.et_IceName);
        contactTextView = (EditText) findViewById(R.id.et_IceNo);
       priorityTextView = (TextView) findViewById(R.id.tv_IcePriority);
        typeTextView = (TextView) findViewById(R.id.tv_IceType);
        description = (EditText) findViewById(R.id.et_IceDescription);

        priorityTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {"1", "2", "3"};

                AlertDialog.Builder builder = new AlertDialog.Builder(AddContactActivity.this);
                builder.setTitle("Select Your Priority Order");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        priorityTextView.setText(items[item]);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        //for pop selection of type
        typeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {"Doctor", "Family", "Others"};

                AlertDialog.Builder builder = new AlertDialog.Builder(AddContactActivity.this);
                builder.setTitle("Make your selection");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        typeTextView.setText(items[item]);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        mCurrentContactUri = intent.getData();
        if (mCurrentContactUri != null) {
            getLoaderManager().initLoader(APPOINTMENT_LOADER, null, this);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_contact_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                createAndInsertContact();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void createAndInsertContact()
    {
        int rowsAffected = 0;
        String name;
        String contact;
        String priority;
        String type;
        String des;

        if(null != nameTextView.getText() && !nameTextView.getText().toString().isEmpty())
        {
        name  = (nameTextView.getText().toString());
        }
        else
        {
            nameTextView.setError("Field cannot be blank");
            return;
        }

        if(null != contactTextView.getText() && !contactTextView.getText().toString().isEmpty())
        {
            contact  = (contactTextView.getText().toString());
        }
        else
        {
            contactTextView.setError("Field cannot be blank");
            return;
        }
        if(null != priorityTextView.getText() && !priorityTextView.getText().toString().isEmpty())
        {
           priority  = (priorityTextView.getText().toString());
        }
        else
        {
            priorityTextView.setError("Field cannot be blank");
            return;
        }


        if(null != typeTextView.getText() && !typeTextView.getText().toString().isEmpty())
        {
             type  = (typeTextView.getText().toString());
        }
        else
        {
            typeTextView.setError("Field cannot be blank");
            return;
        }

        if(null != description.getText() && !description.getText().toString().isEmpty())
        {
          des  = (description.getText().toString());
        }
        else
        {
            description.setError("Field cannot be blank");
            return;
        }

        ICEContact newEntry = new ICEContact(name,contact,type,des,priority );
        ContactDAO contactDAO = new ContactDAO(this);
        if (mCurrentContactUri == null) {
            contactDAO.saveIce(newEntry);
        } else {
            rowsAffected = contactDAO.update(newEntry,mCurrentContactUri);
            if (rowsAffected == 0) {
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Update Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,ICEContactList.class);
                startActivity(intent);
            }
        }

    }

    @Override
    public void onBackPressed() {
        if(nameTextView.getText().toString().length()!=0|contactTextView.getText().toString().length()!=0|priorityTextView.getText().toString().length()!=0|typeTextView.getText().toString().length()!=0|description.getText().toString().length()!=0){

            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }

        else
        {
            finish();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projectionForIce = {
                MedipalContract.PersonalEntry.ICE_ID,
                MedipalContract.PersonalEntry.ICE_NAME,
                MedipalContract.PersonalEntry.ICE_CONTACT_NUMBER,
                MedipalContract.PersonalEntry.ICE_CONTACT_TYPE,
                MedipalContract.PersonalEntry.ICE_SEQUENCE,
                MedipalContract.PersonalEntry.ICE_DESCRIPTION,
        };
        return new CursorLoader(this, mCurrentContactUri, projectionForIce, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
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
            description.setText(contactDescription);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
