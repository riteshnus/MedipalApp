package com.nus.iss.android.medipal.activity;

/**
 * Created by Shubhanshu Gautam on 14/03/17.
 */

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.dao.PersonalBioDAO;
import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.dto.PersonalBio;
import com.nus.iss.android.medipal.fragments.HealthBioFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PersonalInfoActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Edit Personal Bio !", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_personal_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this,NavDrawerActivity.class));
                //this.finish();
                //NavUtils.navigateUpFromSameTask(this);
                return true;

        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
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
            View rootView = inflater.inflate(R.layout.fragment_personal_info, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }


    /**
     *   PersonalBioFragment for 1st tab in PersonalInfo Activity (Editable)
     */
    public static class PersonalBioFragment extends Fragment implements View.OnClickListener
            , LoaderManager.LoaderCallbacks<Cursor>{

        FloatingActionButton mFabProfileEdit;
        FloatingActionButton mFabProfileSave;
        Cursor mCursor;
        View mRootView;

        Calendar dobCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener mDate;
        boolean mIsCursorEmpty;
        private SimpleDateFormat sdfView = new SimpleDateFormat("d MMM yyyy");
        private SimpleDateFormat sdfDB= new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy");

        // Views
        EditText etxtDOB;
        EditText etxtName;
        EditText etxtAddr;
        EditText etxtBloodType;
        EditText etxtHeight;
        EditText etxtIDnum;
        EditText etxtPostalCode;

        public PersonalBioFragment() {
        }

        public PersonalBioFragment newInstance(int sectionNumber) {
            PersonalBioFragment fragment = new PersonalBioFragment();
/*            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);*/
            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            mRootView = inflater.inflate(R.layout.fragment_personal_bio, container, false);
            getLoaderManager().initLoader(0,null,this);
            findAllViewsById();
            //populatePersonalBio(); // pre-populate
            //toggleEditable(false);
            changeInputType();
            mFabProfileEdit = (FloatingActionButton) mRootView.findViewById(R.id.fabProfileEdit);
            mFabProfileEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFabProfileEdit.setVisibility(View.GONE);
                    mFabProfileSave.setVisibility(View.VISIBLE);
                    //toggleEditable(true);
                    changeInputType(); // to toggle editability of fields
                    // enable onclickListener on DoB field
                    etxtDOB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new DatePickerDialog(getContext(), mDate, dobCalendar
                                    .get(Calendar.YEAR), dobCalendar.get(Calendar.MONTH),
                                    dobCalendar.get(Calendar.DAY_OF_MONTH)).show();
                        }
                    });
                    Toast.makeText(getActivity().getApplicationContext(), "Edit Profile", Toast.LENGTH_SHORT).show();
                }
            });
            mFabProfileSave = (FloatingActionButton) mRootView.findViewById(R.id.fabProfileSave);
            mFabProfileSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isUpdateFailed=false;
                    if(mIsCursorEmpty){
                        //insert
                        createAndInsertDetail();
                    } else{
                        //update
                        isUpdateFailed = updateDetail();
                    }
                    // change edit mode only if update successful
                    if(!isUpdateFailed) {
                        mFabProfileSave.setVisibility(View.GONE);
                        mFabProfileEdit.setVisibility(View.VISIBLE);
                        //toggleEditable(false);
                        changeInputType();
                        // disable onclick listener on DoB
                        etxtDOB.setOnClickListener(null);
                        Toast.makeText(getActivity().getApplicationContext(), "Profile Saved", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            // creating dateSetListener
            mDate = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    dobCalendar.set(Calendar.YEAR, year);
                    dobCalendar.set(Calendar.MONTH, monthOfYear);
                    dobCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                }
            };


            return mRootView;
        }//End of onCreateView

        public void createAndInsertDetail(){
            PersonalBio personalBio;
            String name ;
            String addr = etxtAddr.getText().toString();
            String bloodType = etxtBloodType.getText().toString();
            Date dob;
            try {
                dob = sdfDB.parse(sdfDB.format(sdfView.parse(String.valueOf(etxtDOB.getText()))));
            } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(),"failed to save date",Toast.LENGTH_SHORT).show();
            return;
        }
            String height = etxtHeight.getText().toString();
            String idNum = etxtIDnum.getText().toString();
            String postalCode = etxtPostalCode.getText().toString();

            if(null!= etxtName.getText() && !etxtName.getText().toString().isEmpty()){
                name = etxtName.getText().toString();
                personalBio = new PersonalBio( name, dob, idNum, addr, postalCode, height, bloodType);
                PersonalBioDAO pbDAO = new PersonalBioDAO(this.getActivity());
                pbDAO.save(personalBio);
            }else{
                etxtName.setError("Field cannot be blank");
            }
        }

        public boolean updateDetail(){
            PersonalBio personalBio;
            String name ;
            String addr = etxtAddr.getText().toString();
            if(addr.length()>30){
                etxtAddr.setError("Max. length is 30 characters");
                return true;
            }
            String bloodType = etxtBloodType.getText().toString();
            if(bloodType.length()>10){
                etxtBloodType.setError("Enter a valid blood type");
                return true;
            }
            Date dob;
            try {
                dob = sdfDB.parse(sdfDB.format(sdfView.parse(String.valueOf(etxtDOB.getText()))));
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(),"failed to save dob",Toast.LENGTH_SHORT).show();
                return true;
            }
            String height = etxtHeight.getText().toString();
            if(height.length()>3) {
                etxtHeight.setError("Enter a valid height in cm");
                return true;
            }
            String idNum = etxtIDnum.getText().toString();
            if(idNum.length()>20){
                etxtIDnum.setError("Enter a valid ID number");
                return true;
            }
            String postalCode = etxtPostalCode.getText().toString();
            if(postalCode.length()>10){
                etxtPostalCode.setError("Enter a valid postal code");
                return true;
            }

            if(mCursor!=null)mCursor.moveToFirst();
            String id = mCursor.getString(mCursor.getColumnIndex("_id"));

            if(null!= etxtName.getText() && !etxtName.getText().toString().isEmpty()
                    && etxtDOB.getText()!=null && !etxtDOB.getText().toString().isEmpty()){

                name = etxtName.getText().toString();
                if(name.length()>30){
                    etxtName.setError("Max. length is 30 characters");
                    return true;
                } else {
                    personalBio = new PersonalBio(name, dob, idNum, addr, postalCode, height, bloodType);

                    PersonalBioDAO pbDAO = new PersonalBioDAO(this.getActivity());
                    // Update in DB
                    pbDAO.update(personalBio, "_id=" + id, null);
                    return false;
                }
            }else{
                etxtName.setError("Field cannot be blank");
                return true;
            }
        }

        @Override
        public void onClick(View v) {

        }// End of onClick



        public void findAllViewsById(){
            etxtName = ((EditText)mRootView.findViewById(R.id.etxtName));
            etxtAddr = ((EditText)mRootView.findViewById(R.id.etxtAddr));
            etxtBloodType = ((EditText)mRootView.findViewById(R.id.etxtBloodType));
            etxtDOB = ((EditText)mRootView.findViewById(R.id.etxtDOB));
            etxtHeight = ((EditText)mRootView.findViewById(R.id.etxtHeight));
            etxtIDnum = ((EditText)mRootView.findViewById(R.id.etxtIDnum));
            etxtPostalCode = ((EditText)mRootView.findViewById(R.id.etxtPostalCode));
        }

        public void changeInputType(){
            if(etxtName.getInputType() == InputType.TYPE_NULL) {
                etxtName.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
            } else{
                etxtName.setInputType(InputType.TYPE_NULL);
            }
            if(etxtAddr.getInputType() == InputType.TYPE_NULL) {
                etxtAddr.setInputType(InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);
            } else{
                etxtAddr.setInputType(InputType.TYPE_NULL);
            }
            if(etxtBloodType.getInputType() == InputType.TYPE_NULL) {
                etxtBloodType.setInputType(InputType.TYPE_CLASS_TEXT);
            } else{
                etxtBloodType.setInputType(InputType.TYPE_NULL);
            }
            if(etxtDOB.getInputType() == InputType.TYPE_NULL) {
                etxtDOB.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
            } else{
                etxtDOB.setInputType(InputType.TYPE_NULL);
            }
            if(etxtHeight.getInputType() == InputType.TYPE_NULL) {
                etxtHeight.setInputType(InputType.TYPE_CLASS_NUMBER);
            } else{
                etxtHeight.setInputType(InputType.TYPE_NULL);
            }
            if(etxtIDnum.getInputType() == InputType.TYPE_NULL) {
                etxtIDnum.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
            } else{
                etxtIDnum.setInputType(InputType.TYPE_NULL);
            }
            if(etxtPostalCode.getInputType() == InputType.TYPE_NULL) {
                etxtPostalCode.setInputType(InputType.TYPE_CLASS_NUMBER);
            } else{
                etxtPostalCode.setInputType(InputType.TYPE_NULL);
            }
        }

        private void updateLabel() {
            etxtDOB.setText(sdfView.format(dobCalendar.getTimeInMillis()));
        }


        // Loader
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            String[] projection = {
                    MedipalContract.PersonalEntry.USER_ID,
                    MedipalContract.PersonalEntry.USER_ADDRESS,
                    MedipalContract.PersonalEntry.USER_BLOOD_TYPE,
                    MedipalContract.PersonalEntry.USER_DOB,
                    MedipalContract.PersonalEntry.USER_HEIGHT,
                    MedipalContract.PersonalEntry.USER_ID_NO,
                    MedipalContract.PersonalEntry.USER_NAME,
                    MedipalContract.PersonalEntry.USER_POSTAL_CODE,
            };
            return new CursorLoader(this.getContext(), MedipalContract.PersonalEntry.CONTENT_URI_PERSONAL, projection, null, null, null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            //cursor.moveToNext()
            mCursor = cursor;
            populatePersonalBio(cursor);
            //getLoaderManager().restartLoader(0,null,this);
        }

        public void populatePersonalBio(Cursor cursor){
            if(cursor.moveToFirst()) {

                etxtName.setText(cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.USER_NAME)).trim());
                etxtPostalCode.setText(cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.USER_POSTAL_CODE)).trim());
                etxtIDnum.setText(cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.USER_ID_NO)).trim());
                etxtHeight.setText("" + cursor.getInt(cursor.getColumnIndex(MedipalContract.PersonalEntry.USER_HEIGHT)));
                etxtBloodType.setText(cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.USER_BLOOD_TYPE)).trim());
                etxtAddr.setText(cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.USER_ADDRESS)).trim());
                try {
                    etxtDOB.setText(
                            sdfView.format(sdfDB.parse(cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.USER_DOB)))));
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "error date personal bio", Toast.LENGTH_SHORT).show();
                }
            } else{
                mIsCursorEmpty = true;
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }

    } // End of PersonalBio Fragment class



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
            switch (position) {
                case 0:
                    return new PersonalBioFragment();
                case 1:
                    return new HealthBioFragment();

            }
            return PlaceholderFragment.newInstance(position + 1); // TODO remove this and return 2 fragments: for profile and health bio
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Profile";
                case 1:
                    return "Health Bio";

            }
            return null;
        }
    } //End of SectionsPagerAdapter class
}
