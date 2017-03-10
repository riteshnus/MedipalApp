package com.nus.iss.android.medipal.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nus.iss.android.medipal.Data.MedipalContract.personalEntry;

/**
 * Created by Ritesh on 3/8/2017.
 */

public class MedipalDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "Medipal.db";
    public static final int DB_VERSION = 1;

    public MedipalDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_DATA_TABLE =  "CREATE TABLE " + personalEntry.TABLE_NAME + " ("
                + personalEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + personalEntry.USER_NAME + " TEXT NOT NULL, "
                + personalEntry.USER_ADDRESS + " TEXT); ";
        db.execSQL(SQL_CREATE_DATA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
