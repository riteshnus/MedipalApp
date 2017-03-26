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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.adapter.TodaysScheduleAdapter;
import com.nus.iss.android.medipal.adapter.TodaysScheduleCursorAdapter;
import com.nus.iss.android.medipal.constants.Constants;
import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.dto.ScheduledEventJoin;
import com.nus.iss.android.medipal.dto.ScheduledItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static com.nus.iss.android.medipal.activity.MainActivity.PlaceholderFragment.mCursorAdapter;
import static com.nus.iss.android.medipal.helper.Utils.inceaseTimeByGivenNumberOfHours;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>   {
    //Todo: Rename as TodayScheduleActivity

    private static final String SECTION = "Section";
    private static final String TYPE_OF_EVENT = "Type";
    private static final String MEDICINE = "Medicine";
    private static final String APPOINTMENT = "Appointment";


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private List<ScheduledEventJoin> scheduledEventJoinList =new ArrayList<ScheduledEventJoin>();
    private static final int MEDICINE_WITH_REMINDER_LOADER=0;

    private SimpleDateFormat sdfView = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat sdfDB = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy");


    private static List<ScheduledItem> morningEvents=  new ArrayList<ScheduledItem>();
    private static List<ScheduledItem> noonEvents=  new ArrayList<ScheduledItem>();
    private static List<ScheduledItem> eveningEvents=  new ArrayList<ScheduledItem>();
    private static List<ScheduledItem> nightEvents=  new ArrayList<ScheduledItem>();

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
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        createTimeSlotsOfDay();
        getSupportLoaderManager().initLoader(MEDICINE_WITH_REMINDER_LOADER,null,this);

        // Create the adapter that will return a fragment for each of the four
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        // from Home activity
        int i = getIntent().getExtras().getInt("tab_index"); // TODO refactor
        //tabLayout.getTabAt(i).select();
        mViewPager.setCurrentItem(i);

        tabLayout.setupWithViewPager(mViewPager);



    }

    public void createTimeSlotsOfDay(){
        cal.set(1970,0,1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        midnightStart = cal.getTime();
        /*Log.e("Compare", midnightStart.toString());*/

        cal.add(Calendar.HOUR_OF_DAY,4);
        morningStart = cal.getTime();
        /*Log.e("Compare", morningStart.toString());*/

        cal.add(Calendar.HOUR_OF_DAY,8);
        noonStart = cal.getTime();
        /*Log.e("Compare", noonStart.toString());*/

        cal.add(Calendar.HOUR_OF_DAY,6);
        eveningStart = cal.getTime();
        /*Log.e("Compare", eveningStart.toString());*/
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

/*

    public void getDataForTodaySchedule(){
        String query = "SELECT "+ MedipalContract.ReminderEntry.REMINDER_ID +","
                + MedipalContract.ReminderEntry.REMINDER_START_TIME +","
                + MedipalContract.ReminderEntry.REMINDER_FREQUENCY +","
                + MedipalContract.ReminderEntry.REMINDER_INTERVAL +","
                + MedipalContract.MedicineEntry.MEDICINE_MEDICINE_NAME +","
                + MedipalContract.MedicineEntry.MEDICINE_CONSUME_QUANTITY +","
                + MedipalContract.MedicineEntry.MEDICINE_DOSAGE
                + " FROM " + MedipalContract.MedicineEntry.MEDICINE_TABLE_NAME+","
                + MedipalContract.ReminderEntry.REMINDER_TABLE_NAME
                + " WHERE " + MedipalContract.ReminderEntry.REMINDER_ID + "=" + MedipalContract.MedicineEntry.MEDICINE_REMINDERID;

        //Cursor cursor =

    }
*/


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
                boolean isAppointment = false;
                mEventsMatrixCursor = new MatrixCursor(new String[]{
                        MedipalContract.PersonalEntry.REMINDER_ID ,
                        MedipalContract.PersonalEntry.MEDICINE_MEDICINE_NAME ,
                        MedipalContract.PersonalEntry.MEDICINE_CONSUME_QUANTITY ,
                        MedipalContract.PersonalEntry.MEDICINE_DOSAGE,
                        Constants.MEDICINE_TIME ,
                        SECTION,
                        TYPE_OF_EVENT
                });
/*

                morningEvents.clear();
                noonEvents.clear();
                eveningEvents.clear();
                nightEvents.clear();
*/

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

                    //Reminder reminder = new Reminder(reminderFrequency,reminderStartTime,reminderInterval);
                    //ScheduledEventJoin scheduledEventJoin = new ScheduledItem(medicineName, medicineConsumeQty, medicineDosage, date, false);
                    Date date = reminderStartTime;
                    while(reminderFrequency>0){
/*                      Log.e("Schedule DateTime", "Begin : " + String.valueOf(date));
                        Log.e("Comapare", "1st : " + date.compareTo(midnightStart));
                        Log.e("Comapare", "2nd : " + date.compareTo(morningStart));*/
                        if(date.compareTo(midnightStart)>=0 && date.compareTo(morningStart)<0){
                            //NIGHT
                            mEventsMatrixCursor.addRow(new String[]{reminderId+"",medicineName,medicineConsumeQty+"",medicineDosage+"",sdfView.format(date),"4","Medicine"});
                            //ScheduledItem scheduledItem = new ScheduledItem( reminderId, medicineName , medicineConsumeQty, medicineDosage, date, isAppointment);
                            //nightEvents.add(scheduledItem);
                        }else if(date.compareTo(morningStart)>=0 && date.compareTo(noonStart)<0){
                            //MORNING
                            mEventsMatrixCursor.addRow(new String[]{reminderId+"",medicineName,medicineConsumeQty+"",medicineDosage+"",sdfView.format(date),"1","Medicine"});
                            //ScheduledItem scheduledItem = new ScheduledItem( reminderId, medicineName , medicineConsumeQty, medicineDosage, date, isAppointment);
                            //morningEvents.add(scheduledItem);
                        }else if(date.compareTo(noonStart)>=0 && date.compareTo(eveningStart)<0){
                            //AFTERNOON
                            mEventsMatrixCursor.addRow(new String[]{reminderId+"",medicineName,medicineConsumeQty+"",medicineDosage+"",sdfView.format(date),"2","Medicine"});
                            //ScheduledItem scheduledItem = new ScheduledItem( reminderId, medicineName , medicineConsumeQty, medicineDosage, date, isAppointment);
                            //noonEvents.add(scheduledItem);
                        }else{
                            //EVENING
                            mEventsMatrixCursor.addRow(new String[]{reminderId+"",medicineName,medicineConsumeQty+"",medicineDosage+"",sdfView.format(date),"3","Medicine"});
                            //ScheduledItem scheduledItem = new ScheduledItem( reminderId, medicineName , medicineConsumeQty, medicineDosage, date, isAppointment);
                            //eveningEvents.add(scheduledItem);
                        }

                        date = inceaseTimeByGivenNumberOfHours(date,reminderInterval);
                        reminderFrequency--;
                        /*Log.e("Schedule DateTime", "End: " + String.valueOf(date));*/
                    }// End of cursorLoop

                    // --- Populate Cursor with sorted data ---
   /*                 sortEvents();

                    populateCursor();
                    */
                    // ---

                    mCursorAdapter.swapCursor(mEventsMatrixCursor);
                    mCursorAdapter.notifyDataSetChanged();


                    //ScheduledEventJoin scheduledEventJoin = new ScheduledEventJoin(medicineName, medicineConsumeQty, medicineDosage, reminder, false);
                    //scheduledEventJoinList.add(scheduledEventJoin);
/*                        int remindrIdColumnIndex = data.getColumnIndex(MedipalContract.MedicineEntry.MEDICINE_REMINDERID);
                        int reminderId = data.getInt(remindrIdColumnIndex);
                        reminderIDList.add(reminderId);*/
                }

                //getLoaderManager().restartLoader(REMINDER_LOADER, null, this);

                //medicineAdpter.swapCursor(data);
                break;
/*                case APPOINTMENT_LOADER:
                    if (data.moveToFirst()) {
                        int startTimeColumnIndex = data.getColumnIndex(MedipalContract.ReminderEntry.REMINDER_START_TIME);
                        String startTimeString = data.getString(startTimeColumnIndex);
                        //reminderTextView.setText(startTimeString);
                    }*/

        }
    }

    private void sortEvents(){
        Collections.sort(morningEvents, new Comparator<ScheduledItem>() {
            public int compare(ScheduledItem o2, ScheduledItem o1) {
                return o1.getMedicineTime().compareTo(o2.getMedicineTime());
            }
        });
        Collections.sort(noonEvents, new Comparator<ScheduledItem>() {
            public int compare(ScheduledItem o2, ScheduledItem o1) {
                return o1.getMedicineTime().compareTo(o2.getMedicineTime());
            }
        });
        Collections.sort(eveningEvents, new Comparator<ScheduledItem>() {
            public int compare(ScheduledItem o2, ScheduledItem o1) {
                return o1.getMedicineTime().compareTo(o2.getMedicineTime());
            }
        });
        Collections.sort(nightEvents, new Comparator<ScheduledItem>() {
            public int compare(ScheduledItem o2, ScheduledItem o1) {
                return o1.getMedicineTime().compareTo(o2.getMedicineTime());
            }
        });
    }
    private void  populateCursor(){
        int _id=0;
        for(ScheduledItem event:morningEvents){
            _id++;
            mEventsMatrixCursor.addRow(new String[]{_id+"",event.getMedicine(),event.getConsumeQuantity()+"",event.getDosage()+"",sdfView.format(event.getMedicineTime()),"1",MEDICINE });
        }
        for(ScheduledItem event:noonEvents){
            _id++;
            mEventsMatrixCursor.addRow(new String[]{_id+"",event.getMedicine(),event.getConsumeQuantity()+"",event.getDosage()+"",sdfView.format(event.getMedicineTime()),"2",MEDICINE });
        }
        for(ScheduledItem event:eveningEvents){
            _id++;
            mEventsMatrixCursor.addRow(new String[]{_id+"",event.getMedicine(),event.getConsumeQuantity()+"",event.getDosage()+"",sdfView.format(event.getMedicineTime()),"3",MEDICINE });
        }
        for(ScheduledItem event:nightEvents){
            _id++;
            mEventsMatrixCursor.addRow(new String[]{_id+"",event.getMedicine(),event.getConsumeQuantity()+"",event.getDosage()+"",sdfView.format(event.getMedicineTime()),"4",MEDICINE });
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
        TodaysScheduleAdapter mListAdapter;
        static TodaysScheduleCursorAdapter mCursorAdapter = new TodaysScheduleCursorAdapter(null,null,0,0);
        List<ScheduledItem> mEventList;

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber,List<ScheduledItem> events) {
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
            //mCursorAdapter.
/*
            if(getArguments().getInt(ARG_SECTION_NUMBER)==1) {
                mListAdapter = new TodaysScheduleAdapter(getContext(),morningEvents);

            }else if(getArguments().getInt(ARG_SECTION_NUMBER)==2){
                mListAdapter = new TodaysScheduleAdapter(getContext(),noonEvents);
            }else if(getArguments().getInt(ARG_SECTION_NUMBER)==3){
                mListAdapter = new TodaysScheduleAdapter(getContext(),eveningEvents);
            }else if(getArguments().getInt(ARG_SECTION_NUMBER)==4){
                mListAdapter = new TodaysScheduleAdapter(getContext(),nightEvents);
            }
            */
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            // Initializing loaders
            //getLoaderManager().initLoader(MEDICINE_WITH_REMINDER_LOADER,null,this);

            //

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
                return PlaceholderFragment.newInstance(position + 1, morningEvents);
            }else if(position==1){
                return PlaceholderFragment.newInstance(position + 1, noonEvents);
            }else if(position==2){
                return PlaceholderFragment.newInstance(position + 1, eveningEvents);
            }else if(position==3){
                return PlaceholderFragment.newInstance(position + 1, nightEvents);
            }

            return PlaceholderFragment.newInstance(position + 1, null);
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
