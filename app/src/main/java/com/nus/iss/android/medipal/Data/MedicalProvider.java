package com.nus.iss.android.medipal.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.nus.iss.android.medipal.data.MedipalContract.PersonalEntry;
/**
 * Created by Ritesh on 3/8/2017.
 */

public class MedicalProvider extends ContentProvider {

    public static final String LOG_TAG = MedicalProvider.class.getSimpleName();
    private static final int MEMBER = 100;
    private static final int MEMBER_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(MedipalContract.CONTENT_AUTHORITY,MedipalContract.PATH_PERSONAL,MEMBER);
        sUriMatcher.addURI(MedipalContract.CONTENT_AUTHORITY,MedipalContract.PATH_PERSONAL+"/#",MEMBER_ID);
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
        Log.v(LOG_TAG,"Query called");
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match){
            case MEMBER:
                cursor = sqLiteDatabase.query(PersonalEntry.USER_TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case MEMBER_ID:
                selection = PersonalEntry._ID +"=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = sqLiteDatabase.query(PersonalEntry.USER_TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
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
                return InsertUser(uri,values);
            default:
                throw new IllegalArgumentException("Uri didnt match with anything");
        }
    }

    public Uri InsertUser(Uri uri, ContentValues contentValues){
        String userName = contentValues.getAsString(MedipalContract.PersonalEntry.USER_NAME);
        if(userName == null){
            throw new IllegalArgumentException("Member require user name ");
        }

        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        long newId = sqLiteDatabase.insert(PersonalEntry.USER_TABLE_NAME,null,contentValues);
        if(newId == -1){
            Log.e(LOG_TAG, "Failed to insert new member "+uri);
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
                return updateUser(uri,contentValues,selection,selectionArgs);
            case MEMBER_ID:
                selection = PersonalEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateUser(uri,contentValues,selection,selectionArgs);
            default:
                throw new IllegalArgumentException("Insertion is not supported for "+uri);
        }
    }

    private int updateUser(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs){
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
        int rowUpdated = sqLiteDatabase.update(PersonalEntry.USER_TABLE_NAME,contentValues,selection,selectionArgs);
        if(rowUpdated !=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowUpdated;

    }
}
