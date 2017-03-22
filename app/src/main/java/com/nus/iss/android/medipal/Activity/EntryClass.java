package com.nus.iss.android.medipal.Activity;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nus.iss.android.medipal.Data.MedipalContract;
import com.nus.iss.android.medipal.Data.MedipalDBHelper;
import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.Data.MedipalContract.PersonalEntry;
/**
 * Created by Ritesh on 3/9/2017.
 */

public class EntryClass extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    public static final String LOG_TAG = EntryClass.class.getSimpleName();

    private EditText mUserName;
    private EditText mUserAddress;
    private Uri mCurrentUserUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_detail_form);
        mUserName = (EditText) findViewById(R.id.ev_name);
        mUserAddress = (EditText) findViewById(R.id.ev_address);

        Intent intent = getIntent();
        mCurrentUserUri = intent.getData();
        if (mCurrentUserUri == null) {
            setTitle("Add a User");
        } else {
            setTitle("Edit User");
            getLoaderManager().initLoader(MainActivity.USER_LOADER, null, this);
        }

        Button clickMe = (Button) findViewById(R.id.clickButton);
        clickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    public void saveData(){
        String UserName = mUserName.getText().toString().trim();
        String UserDesc = mUserAddress.getText().toString().trim();

        MedipalDBHelper medipalDBHelper = new MedipalDBHelper(this);
        SQLiteDatabase sqLiteDatabase = medipalDBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PersonalEntry.USER_NAME, UserName);
        values.put(MedipalContract.PersonalEntry.USER_ADDRESS, UserDesc);
        Log.v(LOG_TAG,"content changed");

        if (mCurrentUserUri == null) {
            Uri newUri = getContentResolver().insert(MedipalContract.PersonalEntry.CONTENT_URI_PERSONAL, values);
            Log.v(LOG_TAG, " new URI: " + newUri);
            if (newUri == null) {
                Toast.makeText(this, "Not able to insert new user", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Insert new member success", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            int rowsAffected = getContentResolver().update(mCurrentUserUri, values, null, null);
            if (rowsAffected == 0) {
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Update Success", Toast.LENGTH_SHORT).show();
                finish();
            }

        }
        //END

        /*
            Uri newUri = getContentResolver().insert(PersonalEntry.CONTENT_URI_PERSONAL, values);
            Log.v(LOG_TAG, " new URI: " + newUri);
            if (newUri == null) {
                Toast.makeText(this, "Insert new member fail again", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Insert new member success", Toast.LENGTH_SHORT).show();
                finish();
            }
            //END
*/
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                PersonalEntry._ID,
                MedipalContract.PersonalEntry.USER_NAME,
                MedipalContract.PersonalEntry.USER_ADDRESS
        };
        return new CursorLoader(this, mCurrentUserUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToFirst()) {
            String userName = cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.USER_NAME));
            String userAddress = cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.USER_ADDRESS));
            mUserName.setText(userName);
            mUserAddress.setText(userAddress);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
