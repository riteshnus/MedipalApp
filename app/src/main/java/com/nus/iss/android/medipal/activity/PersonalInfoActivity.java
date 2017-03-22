package com.nus.iss.android.medipal.activity;

/**
 * Created by Shubhanshu Gautam on 14/03/17.
 */

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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
                // this.finish();
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_settings:
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
    public static class PersonalBioFragment extends Fragment implements View.OnClickListener{

        FloatingActionButton mFabProfileEdit;
        FloatingActionButton mFabProfileSave;
        View mRootView;
        View mGuardView;
        Calendar dobCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener mDate;

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
            findAllViewsById();
            populatePersonalBio(); // pre-populate
            //toggleEditable(false);
            changeInputType();
            mFabProfileEdit = (FloatingActionButton) mRootView.findViewById(R.id.fabProfileEdit);
            mFabProfileEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFabProfileEdit.setVisibility(View.GONE);
                    mFabProfileSave.setVisibility(View.VISIBLE);
                    //toggleEditable(true);
                    changeInputType();
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
                    mFabProfileSave.setVisibility(View.GONE);
                    mFabProfileEdit.setVisibility(View.VISIBLE);
                    //toggleEditable(false);
                    changeInputType();
                    // disable onclick listener on DoB
                    etxtDOB.setOnClickListener(null);
                    Toast.makeText(getActivity().getApplicationContext(), "Profile Saved", Toast.LENGTH_SHORT).show();
                }
            });

            // creating dateSetListener
            mDate = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {

                    dobCalendar.set(Calendar.YEAR, year);
                    dobCalendar.set(Calendar.MONTH, monthOfYear);
                    dobCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                }
            };

    /*     etxtDOB = ((EditText)mRootView.findViewById(R.id.etxtDOB));
           etxtDOB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(getContext(), mDate, dobCalendar
                            .get(Calendar.YEAR), dobCalendar.get(Calendar.MONTH),
                            dobCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
     */

 /*           mFabProfileEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isFabSave)
                    Snackbar.make(view, "fab Clicked", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });



            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
 */
            return mRootView;
        }

        @Override
        public void onClick(View v) {
/*            switch(v.getId()){
                case R.id.fabProfileEdit:
                    v.setVisibility(View.GONE);
                    mFabProfileSave.setVisibility(View.VISIBLE);
                    toggleEditable(true);
                    Toast.makeText(getActivity().getApplicationContext(), "Edit Profile", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.fabProfileSave:
                    v.setVisibility(View.GONE);
                    mFabProfileEdit.setVisibility(View.VISIBLE);
                    toggleEditable(false);
                    Toast.makeText(getActivity().getApplicationContext(), "Profile Saved", Toast.LENGTH_SHORT).show();
                    break;

            }*/

        }// End of onClick

/*        public void toggleEditable(boolean bool){

            etxtName.setFocusable(bool);
            etxtName.setClickable(bool);

            etxtAddr.setFocusable(bool);
            etxtAddr.setClickable(bool);

            etxtBloodType.setFocusable(bool);
            etxtBloodType.setClickable(bool);

            etxtDOB.setFocusable(bool);
            etxtDOB.setClickable(bool);

            etxtHeight.setFocusable(bool);
            etxtHeight.setClickable(bool);

            etxtIDnum.setFocusable(bool);
            etxtIDnum.setClickable(bool);

            etxtPostalCode.setFocusable(bool);
            etxtPostalCode.setClickable(bool);

            //changeInputType();
        }
*/

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
                etxtHeight.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);
            } else{
                etxtHeight.setInputType(InputType.TYPE_NULL);
            }
            if(etxtIDnum.getInputType() == InputType.TYPE_NULL) {
                etxtIDnum.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
            } else{
                etxtIDnum.setInputType(InputType.TYPE_NULL);
            }
            if(etxtPostalCode.getInputType() == InputType.TYPE_NULL) {
                etxtPostalCode.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
            } else{
                etxtPostalCode.setInputType(InputType.TYPE_NULL);
            }
        }

        private void updateLabel() {
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            etxtDOB.setText(sdf.format(dobCalendar.getTimeInMillis()));
        }

        public void populatePersonalBio(){

            etxtName.setText("Gautam");
            etxtPostalCode.setText("282005");
            etxtIDnum.setText("PAN12345");
            etxtHeight.setText("160");
            etxtBloodType.setText("B positive");
            etxtAddr.setText("Indrapuri, New Agra");
        }
    } // End of PersonalBio Fragment

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

            }
            return PlaceholderFragment.newInstance(position + 1); // TODO remove this and return 2 fragments for profile and health bio
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
    }
}
