package com.nus.iss.android.medipal.activity;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.nus.iss.android.medipal.adapter.UserAdapter;
import com.nus.iss.android.medipal.data.MedipalContract.PersonalEntry;
import com.nus.iss.android.medipal.R;

public class MainActivityOld extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,NavigationView.OnNavigationItemSelectedListener {

    public static final int USER_LOADER = 0;
    public UserAdapter userCursorAdapter;
    private FloatingActionButton fab1;
    private FloatingActionButton fabAppt;
    private FloatingActionButton fab2;
    private FloatingActionButton fab3;
    private boolean isFABOpen=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView setTextView = (ListView) findViewById(R.id.listview);
       /* View emptyView = findViewById(R.id.empty_view);
        setTextView.setEmptyView(emptyView);*/

        userCursorAdapter = new UserAdapter(this, null);
        setTextView.setAdapter(userCursorAdapter);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivityOld.this, EntryClass.class);
                Uri currentPetUri = ContentUris.withAppendedId(PersonalEntry.CONTENT_URI_PERSONAL, id);
                intent.setData(currentPetUri);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }

            }
        });
		
		fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityOld.this, AddMedicineActivity.class);
                startActivity(intent);
            }
        });

        fabAppt = (FloatingActionButton) findViewById(R.id.fabApp);
        fabAppt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityOld.this,AppointmentList.class);
                startActivity(intent);
            }
        });



        //getLoaderManager().initLoader(USER_LOADER, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                PersonalEntry._ID,
                PersonalEntry.USER_NAME,
                PersonalEntry.USER_ADDRESS,
        } ;
        return new CursorLoader(this, PersonalEntry.CONTENT_URI_PERSONAL,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        userCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        userCursorAdapter.swapCursor(null);
    }
    private void showFABMenu(){
        isFABOpen=true;
        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fabAppt.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
       // fab3.animate().translationY(-getResources().getDimension(R.dimen.standard_155));*/
    }
    private void closeFABMenu(){
        isFABOpen=false;
        fab1.animate().translationY(0);
        fabAppt.animate().translationY(0);
       // fab3.animate().translationY(0);*/
    }

    @Override
    public boolean onNavigationItemSelected( MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.nav_medicine_list){
            Intent newIntent= new Intent(MainActivityOld.this, MedicineActivity.class);
            startActivity(newIntent);
        }
        return false;
    }
}
