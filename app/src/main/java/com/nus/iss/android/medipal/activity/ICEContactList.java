package com.nus.iss.android.medipal.activity;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.adapter.IceContactAdapter;
import com.nus.iss.android.medipal.data.MedipalContract;

/**
 * Created by Ritesh on 3/18/2017.
 */

public class ICEContactList extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ICE_LOADER =0;
    private IceContactAdapter iceContactAdapter;
    private  Cursor cursor ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list_view);

        ListView iceListView = (ListView) findViewById(R.id.iceList);
        View emptyView = findViewById(R.id.tv_ice_empty);
        iceListView.setEmptyView(emptyView);

        iceContactAdapter=new IceContactAdapter(this,null,0);
        iceListView.setAdapter(iceContactAdapter);

        FloatingActionButton fabAddAppt = (FloatingActionButton) findViewById(R.id.add_ice);
        fabAddAppt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ICEContactList.this,AddContactActivity.class);
                startActivity(intent);
            }
        });

        iceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(ICEContactList.this,ContactAction.class);
                Uri currentPetUri = ContentUris.withAppendedId(MedipalContract.PersonalEntry.CONTENT_URI_CONTACT,id);
                intent.setData(currentPetUri);
                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(ICE_LOADER,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projectionForIce = {
                MedipalContract.PersonalEntry.ICE_ID,
                MedipalContract.PersonalEntry.ICE_NAME,
                MedipalContract.PersonalEntry.ICE_CONTACT_NUMBER,
                MedipalContract.PersonalEntry.ICE_SEQUENCE,
               };
        return new CursorLoader(this, MedipalContract.PersonalEntry.CONTENT_URI_CONTACT, projectionForIce, null, null, MedipalContract.PersonalEntry.ICE_SEQUENCE+ " ASC");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        iceContactAdapter.swapCursor(data);
        cursor = data;
        FloatingActionButton fabAddAppt = (FloatingActionButton) findViewById(R.id.add_ice);
        if(cursor.getCount() >= 4)
        {
            fabAddAppt.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
            iceContactAdapter.swapCursor(null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
