package com.nus.iss.android.medipal.activity;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.adapter.MedicineAdapter;
import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.dto.Medicine;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static android.os.Build.VERSION_CODES.M;
import static com.nus.iss.android.medipal.R.string.threshold;

public class MedicineActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int MEDICINE_LOADER=0;
    private static final int REMINDER_LOADER=1;
    private static final int CATEGORY_LOADER=2;
    private MedicineAdapter medicineAdpter;
    private Integer medicineId=null;
    private List<Medicine> medicineList=new ArrayList<Medicine>();
    private FloatingActionButton fab;
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
                int medicineId=medicineList.get(position).getMedicineId();
                Uri medicineUri= ContentUris.withAppendedId(MedipalContract.PersonalEntry.CONTENT_URI_MEDICINE,medicineId);
                Intent newIntent=new Intent(MedicineActivity.this,CurrentMedicineActivity.class);
                newIntent.setData(medicineUri);
                startActivity(newIntent);
            }
        });

        medicineAdpter=new MedicineAdapter(this,null,0);
        listView.setAdapter(medicineAdpter);
        setTitle("Active Medicine");
        fab= (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent=new Intent(MedicineActivity.this,AddMedicineActivity.class);
                startActivity(newIntent);
            }
        });
        getLoaderManager().initLoader(MEDICINE_LOADER,null,this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case MEDICINE_LOADER:
                String[] projectionForMedicine = {
                        MedipalContract.PersonalEntry.MEDICINE_ID,
                        MedipalContract.PersonalEntry.MEDICINE_MEDICINE_NAME,
                        MedipalContract.PersonalEntry.MEDICINE_CONSUME_QUANTITY,
                        MedipalContract.PersonalEntry.MEDICINE_QUANTITY};
                return new CursorLoader(this, MedipalContract.PersonalEntry.CONTENT_URI_MEDICINE, projectionForMedicine, null, null, null);

            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        while (data.moveToNext()){
            Medicine medicine = new Medicine();
            int columnIndexForMedicineId=data.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_ID);
            int id=data.getInt(columnIndexForMedicineId);
            medicine.setMedicineId(id);
            medicineList.add(medicine);
        }

        medicineAdpter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
