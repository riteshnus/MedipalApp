package com.nus.iss.android.medipal.activity;

/**
 * Created by Shubhanshu Gautam on 15/03/17.
 */

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.data.MedipalDBHelper;

import java.text.SimpleDateFormat;

public class NavDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean isFABOpen=false;
    FloatingActionButton fab;
    FloatingActionButton fabApptt;
    FloatingActionButton fabMeasure;
    FloatingActionButton fabMedi;
    TextView drawerUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView nv = (NavigationView) findViewById(R.id.nav_view);
        View header = nv.getHeaderView(0);
        //header.findViewById(R.id.drawer_user_name)
        drawerUserName = (TextView) header.findViewById(R.id.drawer_user_name);

        /* ---- FAB buttons ---- */
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.bringToFront();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {
                    rotateFabForward();
                    showFABMenu();
                    fabApptt.setVisibility(View.VISIBLE);
                    fabMeasure.setVisibility(View.VISIBLE);
                    fabMedi.setVisibility(View.VISIBLE);
                } else {
                    rotateFabBackward();
                    closeFABMenu();
                }
            }
        });

        fabApptt = (FloatingActionButton) findViewById(R.id.fab_appointment);
        fabApptt.setVisibility(View.GONE);
        fabApptt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent= new Intent(NavDrawerActivity.this, AddAppointmentActivity.class);
                startActivity(newIntent);
            }
        });

        fabMeasure = (FloatingActionButton) findViewById(R.id.fab_measurement);
        fabMeasure.setVisibility(View.GONE);
        fabMeasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavDrawerActivity.this,MeasurementActivity.class);
                startActivity(intent);
            }
        });

        fabMedi = (FloatingActionButton) findViewById(R.id.fab_medicine);
        fabMedi.setVisibility(View.GONE);
        fabMedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavDrawerActivity.this,AddMedicineActivity.class);
                startActivity(intent);
            }
        });

        /* ---------------------- */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Setting day/date
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, EEE");
        TextView dateTv = (TextView)findViewById(R.id.dateTextView);
        dateTv.setText(sdf.format(date));

        /* ------------ button listeners ------------ */
        ImageButton morning = (ImageButton)findViewById(R.id.ibMorning);
        ImageButton afternoon = (ImageButton)findViewById(R.id.ibAfternoon);
        ImageButton evening = (ImageButton)findViewById(R.id.ibEvening);
        ImageButton night = (ImageButton)findViewById(R.id.ibNight);

        morning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iMainActivity = new Intent(NavDrawerActivity.this, MainActivity.class);
                iMainActivity.putExtra("tab_index",0); // TODO
                startActivity(iMainActivity);
            }
        });

        afternoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iMainActivity = new Intent(NavDrawerActivity.this, MainActivity.class);
                iMainActivity.putExtra("tab_index",1); // TODO
                startActivity(iMainActivity);
            }
        });
        evening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iMainActivity = new Intent(NavDrawerActivity.this, MainActivity.class);
                iMainActivity.putExtra("tab_index",2); // TODO
                startActivity(iMainActivity);
            }
        });
        night.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iMainActivity = new Intent(NavDrawerActivity.this, MainActivity.class);
                iMainActivity.putExtra("tab_index",3); // TODO
                startActivity(iMainActivity);
            }

        });
        /* ---------- button listeners End ---------- */

        setUserName();
    } // End of onCreate



    private void showFABMenu(){
        isFABOpen=true;
        fabApptt.animate().translationY(-getResources().getDimension(R.dimen.standard_70));
        fabMeasure.animate().translationY(-getResources().getDimension(R.dimen.standard_140));
        fabMedi.animate().translationY(-getResources().getDimension(R.dimen.standard_210));
    }
    private void closeFABMenu(){
        isFABOpen=false;
        fabApptt.animate().translationY(0);
        fabMeasure.animate().translationY(0);
        fabMedi.animate().translationY(0);
    }

    private void rotateFabForward() {
        ViewCompat.animate(fab)
                .rotation(135.0F)
                .withLayer()
                .setDuration(300L)
                .setInterpolator(new OvershootInterpolator(10.0F))
                .start();
    }

    private void rotateFabBackward() {
        ViewCompat.animate(fab)
                .rotation(0.0F)
                .withLayer()
                .setDuration(300L)
                .setInterpolator(new OvershootInterpolator(10.0F))
                .start();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    private void setUserName(){
        MedipalDBHelper dbHelper = new MedipalDBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT "+ MedipalContract.PersonalEntry.USER_NAME+" FROM " +MedipalContract.PersonalEntry.USER_TABLE_NAME,null);
        if(c.moveToFirst())  drawerUserName.setText(c.getString(c.getColumnIndex(MedipalContract.PersonalEntry.USER_NAME)).trim());
        if(!c.isClosed()) c.close();
        if(db.isOpen())db.close();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_edit_profile) {
            Intent iPersonalInfo = new Intent(NavDrawerActivity.this, PersonalInfoActivity.class);
            startActivity(iPersonalInfo);
        } else if (id == R.id.nav_addMedicine) {
            Intent intent = new Intent(NavDrawerActivity.this,MedicineActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_measurement) {
            Intent intent = new Intent(NavDrawerActivity.this,MeasurementActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_appointment) {
            Intent intent = new Intent(NavDrawerActivity.this, AppointmentList.class);
            startActivity(intent);
        } else if (id == R.id.nav_ice) {
            Intent intent = new Intent(NavDrawerActivity.this, ICEContactList.class);
            startActivity(intent);
        }else if (id == R.id.nav_consumption_history) {
            final Bundle b = new Bundle();
            b.putString("NAME", "CONSUMPTION");
            Intent intent = new Intent(NavDrawerActivity.this, HistoryListActivity.class);
            intent.putExtras(b);
            startActivity(intent);
        } else if (id == R.id.nav_measurement_history) {
            final Bundle b = new Bundle();
            b.putString("NAME", "MEASUREMENT");
            Intent intent = new Intent(NavDrawerActivity.this, HistoryListActivity.class);
            intent.putExtras(b);
            startActivity(intent);
        }else if(id == R.id.nav_faq){
            startActivity(new Intent(NavDrawerActivity.this, FaqHelpActivity.class));
        } else if(id == R.id.nav_about_us){
    		Intent intent = new Intent(NavDrawerActivity.this, AboutUs.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
