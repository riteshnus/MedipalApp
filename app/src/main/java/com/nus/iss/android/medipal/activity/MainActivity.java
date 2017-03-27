package com.nus.iss.android.medipal.activity;

/**
 * Created by Shubhanshu Gautam on 14/03/17.
 */

import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.adapter.TodaysScheduleCursorAdapter;
import com.nus.iss.android.medipal.constants.Constants;
import com.nus.iss.android.medipal.data.MedipalContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.nus.iss.android.medipal.activity.MainActivity.PlaceholderFragment.mCursorAdapter;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>   {

    private static final String SECTION = "Section";
    private static final String TYPE_OF_EVENT = "Type";
    private static final String MEDICINE = "Medicine";

    private static final int MEDICINE_WITH_REMINDER_LOADER=0;

    private SimpleDateFormat sdfView = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat sdfDB = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy");


    private static MatrixCursor mEventsMatrixCursor = new MatrixCursor(new String[]{
            MedipalContract.PersonalEntry.REMINDER_ID ,
            MedipalContract.PersonalEntry.MEDICINE_MEDICINE_NAME ,
            MedipalContract.PersonalEntry.MEDICINE_CONSUME_QUANTITY ,
            MedipalContract.PersonalEntry.MEDICINE_DOSAGE,
            Constants.MEDICINE_TIME ,
            SECTION,
            TYPE_OF_EVENT
    });

    Calendar cal = Calendar.getInstance();
    private static  Date morningStart = new Date();
    private static  Date noonStart = new Date();
    private static  Date eveningStart = new Date();
    private static  Date midnightStart = new Date();

    /**
     * The {@link ViewPager} that will host the section contents.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager;
        SectionsPagerAdapter sectionsPagerAdapter;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        createTimeSlotsOfDay();
        getSupportLoaderManager().initLoader(MEDICINE_WITH_REMINDER_LOADER,null,this);

        // Create the adapter that will return a fragment for each of the four
        // primary sections of the activity.
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        // from Home activity
        int i = getIntent().getExtras().getInt("tab_index");
        viewPager.setCurrentItem(i);

        tabLayout.setupWithViewPager(viewPager);

    }

    public void createTimeSlotsOfDay(){
        cal.set(1970,0,1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        midnightStart = cal.getTime();

        cal.add(Calendar.HOUR_OF_DAY,4);
        morningStart = cal.getTime();

        cal.add(Calendar.HOUR_OF_DAY,8);
        noonStart = cal.getTime();

        cal.add(Calendar.HOUR_OF_DAY,6);
        eveningStart = cal.getTime();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                // this.finish();
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case MEDICINE_WITH_REMINDER_LOADER:
                String query = "SELECT "+ MedipalContract.PersonalEntry.REMINDER_TABLE_NAME+"." + MedipalContract.PersonalEntry.REMINDER_ID +","
                        + MedipalContract.PersonalEntry.REMINDER_START_TIME +","
                        + MedipalContract.PersonalEntry.REMINDER_FREQUENCY +","
                        + MedipalContract.PersonalEntry.REMINDER_INTERVAL +","
                        + MedipalContract.PersonalEntry.MEDICINE_MEDICINE_NAME +","
                        + MedipalContract.PersonalEntry.MEDICINE_CONSUME_QUANTITY +","
                        + MedipalContract.PersonalEntry.MEDICINE_DOSAGE
                        + " FROM " + MedipalContract.PersonalEntry.MEDICINE_TABLE_NAME+","
                        + MedipalContract.PersonalEntry.REMINDER_TABLE_NAME
                        + " WHERE " + MedipalContract.PersonalEntry.REMINDER_TABLE_NAME+"." + MedipalContract.PersonalEntry.REMINDER_ID + "=" + MedipalContract.PersonalEntry.MEDICINE_REMINDERID;

                return new CursorLoader(this, MedipalContract.PersonalEntry.CONTENT_URI_MEDICINE_REMINDER_JOIN, null, query, null, null);
        }
        return null;
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case MEDICINE_WITH_REMINDER_LOADER:
                mEventsMatrixCursor = new MatrixCursor(new String[]{
                        MedipalContract.PersonalEntry.REMINDER_ID ,
                        MedipalContract.PersonalEntry.MEDICINE_MEDICINE_NAME ,
                        MedipalContract.PersonalEntry.MEDICINE_CONSUME_QUANTITY ,
                        MedipalContract.PersonalEntry.MEDICINE_DOSAGE,
                        Constants.MEDICINE_TIME ,
                        SECTION,
                        TYPE_OF_EVENT
                });


                while (data.moveToNext()) {
                    int reminderId = data.getInt(data.getColumnIndex(MedipalContract.PersonalEntry.REMINDER_ID));
                    int reminderFrequency = data.getInt(data.getColumnIndex(MedipalContract.PersonalEntry.REMINDER_FREQUENCY));
                    Date reminderStartTime = new Date();
                    try {
                        reminderStartTime = sdfDB.parse(data.getString(data.getColumnIndex(MedipalContract.PersonalEntry.REMINDER_START_TIME)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    int reminderInterval = data.getInt(data.getColumnIndex(MedipalContract.PersonalEntry.REMINDER_INTERVAL));

                    String medicineName = data.getString(data.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_MEDICINE_NAME));
                    int medicineConsumeQty = data.getInt(data.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_CONSUME_QUANTITY));
                    int medicineDosage = data.getInt(data.getColumnIndex(MedipalContract.PersonalEntry.MEDICINE_DOSAGE));

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(reminderStartTime);
                    calendar.set(1970,0,1);
                    Date date = calendar.getTime();
                    while(reminderFrequency>0){
                      Log.e("Schedule DateTime", "Begin : " + String.valueOf(date));
                        if(date.compareTo(midnightStart)>=0 && date.compareTo(morningStart)<0){
                            //NIGHT
                            mEventsMatrixCursor.addRow(new String[]{reminderId+"",medicineName,medicineConsumeQty+"",medicineDosage+"",sdfView.format(date),"4",MEDICINE});
                        }else if(date.compareTo(morningStart)>=0 && date.compareTo(noonStart)<0){
                            //MORNING
                            mEventsMatrixCursor.addRow(new String[]{reminderId+"",medicineName,medicineConsumeQty+"",medicineDosage+"",sdfView.format(date),"1",MEDICINE});
                        }else if(date.compareTo(noonStart)>=0 && date.compareTo(eveningStart)<0){
                            //AFTERNOON
                            mEventsMatrixCursor.addRow(new String[]{reminderId+"",medicineName,medicineConsumeQty+"",medicineDosage+"",sdfView.format(date),"2",MEDICINE});
                        }else{
                            //EVENING
                            mEventsMatrixCursor.addRow(new String[]{reminderId+"",medicineName,medicineConsumeQty+"",medicineDosage+"",sdfView.format(date),"3",MEDICINE});
                        }

                        // Always keep the date same
                        calendar.add(Calendar.HOUR_OF_DAY,reminderInterval);
                        Log.e("loop end : ",cal.getTime().toString());
                        calendar.set(1970,0,1); // keeping date as epoch; for same day
                        date=calendar.getTime();
                        Log.e("loop end : ",date.toString());
                        reminderFrequency--;
                    }

                }// End of cursorLoop
                mCursorAdapter.swapCursor(mEventsMatrixCursor);
                mCursorAdapter.notifyDataSetChanged();

                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    // ****************** FRAGMENT ******************

    /**
     * A placeholder FRAGMENT containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private View mRootView;
        ListView mListView;
        TextView mEmptyView;
        static TodaysScheduleCursorAdapter mCursorAdapter = new TodaysScheduleCursorAdapter(null,null,0,0);

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            mRootView = inflater.inflate(R.layout.fragment_todays_schedule, container, false);

            findViewsById();

            mListView.setEmptyView(mEmptyView);
            mCursorAdapter = new TodaysScheduleCursorAdapter(
                    getContext(),mEventsMatrixCursor,0,getArguments().getInt(ARG_SECTION_NUMBER));
            mListView.setAdapter(mCursorAdapter);
              return mRootView;
        }


        void findViewsById(){
            mListView = (ListView) mRootView.findViewById(R.id.scheduleList);
            mEmptyView = (TextView) mRootView.findViewById(R.id.tv_empty_schedule);
        }

    }

    // ****************** END FRAGMENT ******************

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position==0) {
                return PlaceholderFragment.newInstance(position + 1);
            }else if(position==1){
                return PlaceholderFragment.newInstance(position + 1);
            }else if(position==2){
                return PlaceholderFragment.newInstance(position + 1);
            }else if(position==3){
                return PlaceholderFragment.newInstance(position + 1);
            }

            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "MORNING";
                case 1:
                    return "NOON";
                case 2:
                    return "EVENING";
                case 3:
                    return "NIGHT";
            }
            return null;

        }
    }
}
