package com.nus.iss.android.medipal.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nus.iss.android.medipal.data.MedipalContract.PersonalEntry;

/**
 * Created by Ritesh on 3/8/2017.
 */

public class MedipalDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "MedipalDatabase.db";
    public static final int DB_VERSION = 5;

    // Create Table for PErsonal Bio
    public static final  String SQL_CREATE_USER_DATA_TABLE =  "CREATE TABLE IF NOT EXISTS " + PersonalEntry.USER_TABLE_NAME + " ("
            + PersonalEntry.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PersonalEntry.USER_NAME + " TEXT NOT NULL, "
            + PersonalEntry.USER_DOB + " DATE, "
            + PersonalEntry.USER_ID_NO + " TEXT, "
            + PersonalEntry.USER_POSTAL_CODE + " TEXT, "
            + PersonalEntry.USER_ADDRESS + " TEXT, "
            + PersonalEntry.USER_HEIGHT + " INTEGER, "
            + PersonalEntry.USER_BLOOD_TYPE  + " TEXT );";

    //Create Table for Health Bio
    public static final  String SQL_CREATE_HEATH_BIO_DATA_TABLE =  "CREATE TABLE IF NOT EXISTS " + MedipalContract.HealthBioEntry.HEALTH_BIO_TABLE_NAME + " ("
            + MedipalContract.HealthBioEntry.HEALTH_BIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MedipalContract.HealthBioEntry.HEALTH_CONDITION + " TEXT, "
            + MedipalContract.HealthBioEntry.HEALTH_START_DATE + " DATE, "
            + MedipalContract.HealthBioEntry.HEALTH_CONDITION_TYPE  + " TEXT );";

    //Create Table for Categories
    public static final  String SQL_CREATE_CATEGORIES_DATA_TABLE =  "CREATE TABLE IF NOT EXISTS " + MedipalContract.CategoriesEntry.CATEGORIES_TABLE_NAME + " ("
            + MedipalContract.CategoriesEntry.CATEGORIES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MedipalContract.CategoriesEntry.CATEGORIES_CATEGORY_NAME + " TEXT, "
            + MedipalContract.CategoriesEntry.CATEGORIES_CODE + " TEXT, "
            + MedipalContract.CategoriesEntry.CATEGORIES_DESCRIPTION + " TEXT, "
            + MedipalContract.CategoriesEntry.CATEGORIES_REMIND  + " INTEGER DEFAULT 0 );";

    // Ceate Table for Medicine
    public static final  String SQL_CREATE_MEDICINE_DATA_TABLE =  "CREATE TABLE IF NOT EXISTS " + MedipalContract.MedicineEntry.MEDICINE_TABLE_NAME + " ("
            + MedipalContract.MedicineEntry.MEDICINE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MedipalContract.MedicineEntry.MEDICINE_MEDICINE_NAME + " TEXT, "
            + MedipalContract.MedicineEntry.MEDICINE_DESCRIPTION + " TEXT, "
            + MedipalContract.MedicineEntry.MEDICINE_CATID + " INTEGER, "
            + MedipalContract.MedicineEntry.MEDICINE_REMINDERID + " INTEGER, "
            + MedipalContract.MedicineEntry.MEDICINE_REMIND + " INTEGER DEFAULT 0, "
            + MedipalContract.MedicineEntry.MEDICINE_QUANTITY + " INTEGER, "
            + MedipalContract.MedicineEntry.MEDICINE_DOSAGE + " INTEGER, "
            + MedipalContract.MedicineEntry.MEDICINE_CONSUME_QUANTITY + " INTEGER, "
            + MedipalContract.MedicineEntry.MEDICINE_THRESHOLD + " INTEGER, "
            + MedipalContract.MedicineEntry.MEDICINE_DATE_ISSUED + " DATE, "
            + MedipalContract.MedicineEntry.MEDICINE_EXPIRE_FACTOR  + " INTEGER );";

    // Create Table for Measurement
    public static final  String SQL_CREATE_MEASUREMENT_DATA_TABLE =  "CREATE TABLE IF NOT EXISTS " + MedipalContract.MeasurementEntry.MEASUREMENT_TABLE_NAME + " ("
            + MedipalContract.MeasurementEntry.MEASUREMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MedipalContract.MeasurementEntry.MEASUREMENT_SYSTOLIC + " INTEGER, "
            + MedipalContract.MeasurementEntry.MEASUREMENT_DIASTOLIC + " INTEGER, "
            + MedipalContract.MeasurementEntry.MEASUREMENT_PULSE + " INTEGER, "
            + MedipalContract.MeasurementEntry.MEASUREMENT_TEMPERATURE + " REAL, "
            + MedipalContract.MeasurementEntry.MEASUREMENT_WEIGHT  + " INTEGER, "
            + MedipalContract.MeasurementEntry.MEASUREMENT_MEASURED_ON + " DATE );";

    // Create Table for Consumption
    public static final  String SQL_CREATE_CONSUMPTION_DATA_TABLE =  "CREATE TABLE IF NOT EXISTS " + MedipalContract.ConsumptionEntry.CONSUMPTION_TABLE_NAME + " ("
            + MedipalContract.ConsumptionEntry.CONSUMPTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MedipalContract.ConsumptionEntry.CONSUMPTION_MEDICINE_ID + " INTEGER, "
            + MedipalContract.ConsumptionEntry.CONSUMPTION_QUANTITY + " INTEGER, "
            + MedipalContract.ConsumptionEntry.CONSUMPTION_CONSUMED_ON + " DATE );";

    // Ceate table for Reminder
    public static final  String SQL_CREATE_REMINDER_DATA_TABLE =  "CREATE TABLE IF NOT EXISTS " + MedipalContract.ReminderEntry.REMINDER_TABLE_NAME + " ("
            + MedipalContract.ReminderEntry.REMINDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MedipalContract.ReminderEntry.REMINDER_FREQUENCY + " INTEGER, "
            + MedipalContract.ReminderEntry.REMINDER_START_TIME + " DATE,"
            + MedipalContract.ReminderEntry.REMINDER_INTERVAL + " INTEGER );";

    // Create Table for Appointment
    public static final  String SQL_CREATE_APPOINTMENT_DATA_TABLE =  "CREATE TABLE IF NOT EXISTS " + MedipalContract.AppointmentEntry.APPOINTMENT_TABLE_NAME + " ("
            + MedipalContract.AppointmentEntry.APPOINTMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MedipalContract.AppointmentEntry.APPOINTMENT_LOCATION + " INTEGER, "
            + MedipalContract.AppointmentEntry.APPOINTMENT_DATE_TIME + " DATE,"
            + MedipalContract.AppointmentEntry.APPOINTMENT_DESCRIPTION + " TEXT );";

    // Create table for ICE
    public static final  String SQL_CREATE_ICE_DATA_TABLE =  "CREATE TABLE IF NOT EXISTS " + MedipalContract.IceEntry.ICE_TABLE_NAME + " ("
            + MedipalContract.IceEntry.ICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MedipalContract.IceEntry.ICE_NAME + " TEXT, "
            + MedipalContract.IceEntry.ICE_CONTACT_NUMBER + " TEXT, "
            + MedipalContract.IceEntry.ICE_CONTACT_TYPE + " TEXT,"
            + MedipalContract.IceEntry.ICE_DESCRIPTION + " TEXT, "
            + MedipalContract.IceEntry.ICE_SEQUENCE + "INTEGER );";


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
        insertCategory(db,"Supplement","SUP","Meds for Supplement",0);
        insertCategory(db,"Chronic","CHR","Meds for long term disease",1);
        insertCategory(db,"Incidental","INC","For Common Cold and Flu",1);
        insertCategory(db,"Complete Course","COM","Antibiotics for Infection",1);
        insertCategory(db,"Self Apply","SEL","Self Prescribed Prescription",0);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + PersonalEntry.USER_TABLE_NAME );
        onCreate(db);
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    private void insertCategory(SQLiteDatabase db,String categoryName,String code,String description,int remind){
        ContentValues categoryValue=new ContentValues();
        categoryValue.put(MedipalContract.CategoriesEntry.CATEGORIES_CATEGORY_NAME,categoryName);
        categoryValue.put(MedipalContract.CategoriesEntry.CATEGORIES_CODE,code);
        categoryValue.put(MedipalContract.CategoriesEntry.CATEGORIES_DESCRIPTION,description);
        categoryValue.put(MedipalContract.CategoriesEntry.CATEGORIES_REMIND,remind);
        db.insert(MedipalContract.CategoriesEntry.CATEGORIES_TABLE_NAME,null,categoryValue);
    }
}
