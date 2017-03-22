package com.nus.iss.android.medipal.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.nus.iss.android.medipal.Data.MedipalContract.PersonalEntry;
/**
 * Created by Ritesh on 3/8/2017.
 */

public class MedicalProvider extends ContentProvider {

    public static final String LOG_TAG = MedicalProvider.class.getSimpleName();
    private static final int MEMBER = 100;
    private static final int MEMBER_ID = 101;
    private static final int MEDICINE=102;
    private static final int MEDICINE_ID=103;
    private static final int REMINDER=104;
    private static final int REMINDER_ID=105;
    private static final int CATEGORY=106;
    private static final int CATEGORY_ID=107;
    private static final int APPOINTMENT=108;
    private static final int APPOINTMENT_ID=109;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(MedipalContract.CONTENT_AUTHORITY,PersonalEntry.USER_TABLE_NAME,MEMBER);
        sUriMatcher.addURI(MedipalContract.CONTENT_AUTHORITY,PersonalEntry.USER_TABLE_NAME+"/#",MEMBER_ID);
        sUriMatcher.addURI(MedipalContract.CONTENT_AUTHORITY,PersonalEntry.MEDICINE_TABLE_NAME,MEDICINE);
        sUriMatcher.addURI(MedipalContract.CONTENT_AUTHORITY,PersonalEntry.MEDICINE_TABLE_NAME+"/#",MEDICINE_ID);
        sUriMatcher.addURI(MedipalContract.CONTENT_AUTHORITY,PersonalEntry.REMINDER_TABLE_NAME,REMINDER);
        sUriMatcher.addURI(MedipalContract.CONTENT_AUTHORITY,PersonalEntry.REMINDER_TABLE_NAME+"/#",REMINDER_ID);
        sUriMatcher.addURI(MedipalContract.CONTENT_AUTHORITY,PersonalEntry.CATEGORIES_TABLE_NAME,CATEGORY);
        sUriMatcher.addURI(MedipalContract.CONTENT_AUTHORITY,PersonalEntry.CATEGORIES_TABLE_NAME+"/#",CATEGORY_ID);
        sUriMatcher.addURI(MedipalContract.CONTENT_AUTHORITY,PersonalEntry.CATEGORIES_TABLE_NAME,APPOINTMENT);
        sUriMatcher.addURI(MedipalContract.CONTENT_AUTHORITY,PersonalEntry.CATEGORIES_TABLE_NAME+"/#",APPOINTMENT_ID);
    }

    private MedipalDBHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new MedipalDBHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.v(LOG_TAG,"Query called for uri" + uri);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        Log.v(LOG_TAG,"Query to be called for " + match);
        switch (match){
            case MEMBER:
                cursor = sqLiteDatabase.query(PersonalEntry.USER_TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case MEMBER_ID:
                selection = PersonalEntry._ID +"=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = sqLiteDatabase.query(PersonalEntry.USER_TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case CATEGORY:
                cursor=sqLiteDatabase.query(PersonalEntry.CATEGORIES_TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case CATEGORY_ID:
                selection = PersonalEntry._ID +"=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = sqLiteDatabase.query(PersonalEntry.CATEGORIES_TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case MEDICINE:
                cursor=sqLiteDatabase.query(PersonalEntry.MEDICINE_TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case MEDICINE_ID:
                selection = PersonalEntry._ID +"=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = sqLiteDatabase.query(PersonalEntry.MEDICINE_TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case REMINDER:
                cursor=sqLiteDatabase.query(PersonalEntry.REMINDER_TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case REMINDER_ID:
                selection = PersonalEntry._ID +"=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = sqLiteDatabase.query(PersonalEntry.REMINDER_TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case APPOINTMENT:
                cursor=sqLiteDatabase.query(PersonalEntry.APPOINTMENT_TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case APPOINTMENT_ID:
                selection = PersonalEntry._ID +"=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = sqLiteDatabase.query(PersonalEntry.APPOINTMENT_TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Can not find query for uri "+uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int match = sUriMatcher.match(uri);
        switch (match){
            case MEMBER:
                return insertTable(uri,values,PersonalEntry.USER_TABLE_NAME);
            case MEDICINE:
                return insertTable(uri,values,PersonalEntry.MEDICINE_TABLE_NAME);
            case REMINDER:
                return insertTable(uri,values,PersonalEntry.REMINDER_TABLE_NAME);
            case APPOINTMENT:
                return insertTable(uri,values,PersonalEntry.APPOINTMENT_TABLE_NAME);
            default:
                throw new IllegalArgumentException("Uri didn't match with anything");
        }
    }

    public Uri insertTable(Uri uri, ContentValues contentValues,String tableName){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        long newId = sqLiteDatabase.insert(tableName,null,contentValues);
        if(newId == -1){
            Log.e(LOG_TAG, "Failed to insert into table "+uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri,null,false);
        return ContentUris.withAppendedId(uri,newId);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        int rowDeleted ;
        final int match = sUriMatcher.match(uri);
        switch (match){
            case MEMBER:
                rowDeleted = sqLiteDatabase.delete(PersonalEntry.USER_TABLE_NAME,selection,selectionArgs);
                if(rowDeleted !=0) {
                    getContext().getContentResolver().notifyChange(uri,null);
                }
                break;
            case MEMBER_ID:
                selection = MedipalContract.PersonalEntry._ID +"=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowDeleted = sqLiteDatabase.delete(MedipalContract.PersonalEntry.USER_TABLE_NAME,selection,selectionArgs);
                if(rowDeleted !=0) {
                    getContext().getContentResolver().notifyChange(uri,null);
                }
                break;
            case APPOINTMENT:
                rowDeleted = sqLiteDatabase.delete(PersonalEntry.APPOINTMENT_TABLE_NAME,selection,selectionArgs);
                if(rowDeleted !=0) {
                    getContext().getContentResolver().notifyChange(uri,null);
                }
                break;
            case APPOINTMENT_ID:
                selection = MedipalContract.PersonalEntry._ID +"=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowDeleted = sqLiteDatabase.delete(PersonalEntry.APPOINTMENT_TABLE_NAME,selection,selectionArgs);
                if(rowDeleted !=0) {
                    getContext().getContentResolver().notifyChange(uri,null);
                }
                break;
            default:
                throw new IllegalArgumentException("Can not delete the row "+ uri);
        }
        return rowDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case MEMBER:
                return updateUser(uri,contentValues,selection,selectionArgs,PersonalEntry.USER_TABLE_NAME);
            case MEMBER_ID:
                selection = PersonalEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateUser(uri,contentValues,selection,selectionArgs,PersonalEntry.USER_TABLE_NAME);
            case APPOINTMENT:
                return updateUser(uri,contentValues,selection,selectionArgs,PersonalEntry.APPOINTMENT_TABLE_NAME);
            case APPOINTMENT_ID:
                selection = PersonalEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateUser(uri,contentValues,selection,selectionArgs,PersonalEntry.APPOINTMENT_TABLE_NAME);
            default:
                throw new IllegalArgumentException("Insertion is not supported for "+uri);
        }
    }

    private int updateUser(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs,String tableName){
        if(contentValues.containsKey(MedipalContract.PersonalEntry.USER_NAME)){
            String userName = contentValues.getAsString(MedipalContract.PersonalEntry.USER_NAME);
            Log.v(LOG_TAG,"User: "+userName);
            if(userName == null){
                throw new IllegalArgumentException("Name can not be blank");
            }
        }

        if(contentValues.size()==0){
            return 0;
        }

        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        int rowUpdated = sqLiteDatabase.update(tableName,contentValues,selection,selectionArgs);
        if(rowUpdated !=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowUpdated;

    }
}
