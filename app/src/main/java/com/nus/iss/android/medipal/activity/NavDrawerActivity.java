package com.nus.iss.android.medipal.activity;

/**
 * Created by Shubhanshu Gautam on 15/03/17.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nus.iss.android.medipal.R;

import java.text.SimpleDateFormat;

import static com.nus.iss.android.medipal.R.id.fab1;

public class NavDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FloatingActionButton fab1;
    private FloatingActionButton fabAppt;
    private boolean isFABOpen=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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
                closeFABMenu();
                Intent intent = new Intent(NavDrawerActivity.this, AddMedicineActivity.class);
                startActivity(intent);
            }
        });

        fabAppt = (FloatingActionButton) findViewById(R.id.fabApp);
        fabAppt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
                Intent intent = new Intent(NavDrawerActivity.this,AppointmentList.class);
                startActivity(intent);
            }
        });

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

        // TODO remove these listeners, implement View.OnClickListener in this class, and use switch case with view ids
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
        //------------ End ------------
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action for profile photo
        } else if (id == R.id.nav_gallery) {
            Intent iPersonalInfo = new Intent(NavDrawerActivity.this, PersonalInfoActivity.class);
            startActivity(iPersonalInfo);

        } else if (id == R.id.nav_medicine) {
            Intent intent = new Intent(NavDrawerActivity.this,MedicineActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_measurement) {
            Intent intent = new Intent(NavDrawerActivity.this,Measurement.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_history) {
            Intent intent = new Intent(NavDrawerActivity.this, History.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

}
