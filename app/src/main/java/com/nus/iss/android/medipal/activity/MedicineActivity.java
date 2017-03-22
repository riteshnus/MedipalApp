package com.nus.iss.android.medipal.activity;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.nus.iss.android.medipal.adapter.MedicineAdapter;
import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.R;

import java.util.ArrayList;
import java.util.List;

public class MedicineActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int MEDICINE_LOADER=0;
    private static final int REMINDER_LOADER=1;
    private static final int CATEGORY_LOADER=2;
    private MedicineAdapter medicineAdpter;
    private List<Integer> reminderIDList=new ArrayList<Integer>();
    private TextView reminderTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        ListView medicineListView = (ListView) findViewById(R.id.list_medicine);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        medicineListView.setEmptyView(emptyView);
        ListView listView= (ListView) findViewById(R.id.list_medicine);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                
            }
        });
        reminderTextView= (TextView) findViewById(R.id.reminder_text);
        medicineAdpter=new MedicineAdapter(this,null,0);
        listView.setAdapter(medicineAdpter);
        setTitle("Active Medicine");
        getLoaderManager().initLoader(MEDICINE_LOADER,null,this);
        getLoaderManager().initLoader(REMINDER_LOADER,null,this);
        //getLoaderManager().initLoader(CATEGORY_LOADER,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
       switch (id){
           case MEDICINE_LOADER:
               String[] projectionForMedicine = {
                       MedipalContract.MedicineEntry.MEDICINE_ID,
                       MedipalContract.MedicineEntry.MEDICINE_MEDICINE_NAME,
                       MedipalContract.MedicineEntry.MEDICINE_REMINDERID,
                       MedipalContract.MedicineEntry.MEDICINE_CATID,
                       MedipalContract.MedicineEntry.MEDICINE_CONSUME_QUANTITY,
                       MedipalContract.MedicineEntry.MEDICINE_QUANTITY};
               return new CursorLoader(this, MedipalContract.MedicineEntry.CONTENT_URI_MEDICINE, projectionForMedicine, null, null, null);
           case REMINDER_LOADER:
               String[] projectionForReminder = {
                       MedipalContract.ReminderEntry.REMINDER_ID,
                       MedipalContract.ReminderEntry.REMINDER_START_TIME};
               String selection= MedipalContract.PersonalEntry._ID +"in?";
               List<String> selectionArgsList=new ArrayList<>();
               for(Integer reminder:reminderIDList){
                   selectionArgsList.add(String.valueOf(reminder));
               }
               String[] selectionArgs= (String[]) selectionArgsList.toArray();
               return new CursorLoader(this, MedipalContract.ReminderEntry.CONTENT_URI_REMINDER, projectionForReminder, selection, selectionArgs, null);
           case CATEGORY_LOADER:
               String[] projectionForCategory = {
                       MedipalContract.CategoriesEntry.CATEGORIES_ID,
                       MedipalContract.CategoriesEntry.CATEGORIES_CATEGORY_NAME,
                       MedipalContract.CategoriesEntry.CATEGORIES_CODE,
                       MedipalContract.CategoriesEntry.CATEGORIES_REMIND};
               return new CursorLoader(this, MedipalContract.CategoriesEntry.CONTENT_URI_CATEGORY, projectionForCategory, null, null, null);
           default:
               return null;
       }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
         switch (loader.getId()){
             case MEDICINE_LOADER:
                 while(data.moveToNext()){
                     int remindrIdColumnIndex=data.getColumnIndex(MedipalContract.MedicineEntry.MEDICINE_REMINDERID);
                     int reminderId=data.getInt(remindrIdColumnIndex);
                     reminderIDList.add(reminderId);
                 }
                getLoaderManager().restartLoader(REMINDER_LOADER,null,this);

                 medicineAdpter.swapCursor(data);
                 break;
             case REMINDER_LOADER:
                 if(data.moveToFirst()){
                     int startTimeColumnIndex=data.getColumnIndex(MedipalContract.ReminderEntry.REMINDER_START_TIME);
                     String startTimeString=data.getString(startTimeColumnIndex);
                     reminderTextView.setText(startTimeString);
                 }
         }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
