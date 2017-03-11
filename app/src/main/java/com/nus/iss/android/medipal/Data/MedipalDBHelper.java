package com.nus.iss.android.medipal.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.nus.iss.android.medipal.data.MedipalContract.PersonalEntry;

/**
 * Created by Ritesh on 3/8/2017.
 */

public class MedipalDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "Medipal.db";
    public static final int DB_VERSION = 1;

     // Create Table for PErsonal Bio
    public static final  String SQL_CREATE_USER_DATA_TABLE =  "CREATE TABLE " + PersonalEntry.USER_TABLE_NAME + " ("
            + PersonalEntry.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PersonalEntry.USER_NAME + " TEXT NOT NULL, "
            + PersonalEntry.USER_DOB + " DATE, "
            +PersonalEntry.USER_ID_NO + " TEXT, "
            +PersonalEntry.USER_POSTAL_CODE + " TEXT, "
            +PersonalEntry.USER_HEIGHT + " INTEGER, "
            +PersonalEntry.USER_BLOOD_TYPE  + " TEXT );";

    //Ceate Table for Health Bio
    public static final  String SQL_CREATE_HEATH_BIO_DATA_TABLE =  "CREATE TABLE " + PersonalEntry.HEALTH_BIO_TABLE_NAME + " ("
            + PersonalEntry.HEALTH_BIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PersonalEntry.HEALTH_CONDITION + " TEXT, "
            + PersonalEntry.HEALTH_START_DATE + " DATE, "
            +PersonalEntry.HEALTH_CONDITION_TYPE  + " TEXT );";

    //Create Table for Categories
    public static final  String SQL_CREATE_CATEGORIES_DATA_TABLE =  "CREATE TABLE " + PersonalEntry.CATEGORIES_TABLE_NAME + " ("
            + PersonalEntry.CATEGORIES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PersonalEntry.CATEGORIES_CATEGORY_NAME + " TEXT, "
            +PersonalEntry.CATEGORIES_CODE + " TEXT, "
            +PersonalEntry.CATEGORIES_DESCRIPTION + " TEXT, "
            +PersonalEntry.CATEGORIES_REMIND  + " INTEGER DEFAULT 0 );";

    // Ceate Table for Medicine
    public static final  String SQL_CREATE_MEDICINE_DATA_TABLE =  "CREATE TABLE " + PersonalEntry.MEDICINE_TABLE_NAME + " ("
            + PersonalEntry.MEDICINE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PersonalEntry.MEDICINE_MEDICINE_NAME + " TEXT, "
            +PersonalEntry.MEDICINE_DESCRIPTION + " TEXT, "
            +PersonalEntry.MEDICINE_CATID + " INTEGER, "
            +PersonalEntry.MEDICINE_REMINDERID + " INTEGER, "
            +PersonalEntry.MEDICINE_REMIND + " INTEGER DEFAULT 0, "
            +PersonalEntry.MEDICINE_QUANTITY + " INTEGER, "
            +PersonalEntry.MEDICINE_DOSAGE + " INTEGER, "
            +PersonalEntry.MEDICINE_CONSUME_QUANTITY + " INTEGER, "
            +PersonalEntry.MEDICINE_THRESHOLD + " INTEGER, "
            + PersonalEntry.MEDICINE_DATE_ISSUED + " DATE, "
            +PersonalEntry.MEDICINE_EXPIRE_FACTOR  + " INTEGER );";

    // Create Table for Measurement
    public static final  String SQL_CREATE_MEASUREMENT_DATA_TABLE =  "CREATE TABLE " + PersonalEntry.MEASUREMENT_TABLE_NAME + " ("
            + PersonalEntry.MEASUREMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PersonalEntry.MEASUREMENT_SYSTOLIC + " INTEGER, "
            +PersonalEntry.MEASUREMENT_DIASTOLIC + " INTEGER, "
            +PersonalEntry.MEASUREMENT_PULSE + " INTEGER, "
            +PersonalEntry.MEASUREMENT_TEMPERATURE + " REAL, "
            +PersonalEntry.MEASUREMENT_WEIGHT  + " INTEGER, "
            +PersonalEntry.MEASUREMENT_MEASURED_ON + " DATE );";

    // Create Table for Consumption
    public static final  String SQL_CREATE_CONSUMPTION_DATA_TABLE =  "CREATE TABLE " + PersonalEntry.CONSUMPTION_TABLE_NAME + " ("
            + PersonalEntry.CONSUMPTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PersonalEntry.CONSUMPTION_MEDICINE_ID + " INTEGER, "
            +PersonalEntry.CONSUMPTION_QUANTITY + " INTEGER, "
            +PersonalEntry.CONSUMPTION_CONSUMED_ON + " DATE );";

    // Ceate table for Reminder
    public static final  String SQL_CREATE_REMINDER_DATA_TABLE =  "CREATE TABLE " + PersonalEntry.REMINDER_TABLE_NAME + " ("
            + PersonalEntry.REMINDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PersonalEntry.REMINDER_FREQUENCY + " INTEGER, "
            +PersonalEntry.REMINDER_START_TIME + " DATE,"
            +PersonalEntry.REMINDER_INTERVAL + " INTEGER );";

    // Create Table for Appointment
    public static final  String SQL_CREATE_APPOINTMENT_DATA_TABLE =  "CREATE TABLE " + PersonalEntry.APPOINTMENT_TABLE_NAME + " ("
            + PersonalEntry.APPOINTMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PersonalEntry.APPOINTMENT_LOCATION + " INTEGER, "
            +PersonalEntry.APPOINTMENT_APPOINTMENT_DATE_TIME + " DATE,"
            +PersonalEntry.APPOINTMENT_DESCRIPTION + " TEXT );";

    // Create table for ICE
    public static final  String SQL_CREATE_ICE_DATA_TABLE =  "CREATE TABLE " + PersonalEntry.ICE_TABLE_NAME + " ("
            + PersonalEntry.ICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PersonalEntry.ICE_NAME + " TEXT, "
            + PersonalEntry.ICE_CONTACT_NUMBER + " TEXT, "
            +PersonalEntry.ICE_CONTACT_TYPE + " TEXT,"
            +PersonalEntry.ICE_DESCRIPTION + " TEXT, "
            + PersonalEntry.ICE_SEQUENCE + "INTEGER );";



    public MedipalDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER_DATA_TABLE);
        db.execSQL(SQL_CREATE_HEATH_BIO_DATA_TABLE);
        db.execSQL(SQL_CREATE_CATEGORIES_DATA_TABLE);
        db.execSQL(SQL_CREATE_MEDICINE_DATA_TABLE);
        db.execSQL(SQL_CREATE_MEASUREMENT_DATA_TABLE);
        db.execSQL(SQL_CREATE_CONSUMPTION_DATA_TABLE);
        db.execSQL(SQL_CREATE_REMINDER_DATA_TABLE);
        db.execSQL(SQL_CREATE_APPOINTMENT_DATA_TABLE);
        db.execSQL(SQL_CREATE_ICE_DATA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
