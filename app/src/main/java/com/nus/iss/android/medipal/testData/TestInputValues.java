package com.nus.iss.android.medipal.testData;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ritesh on 3/27/2017.
 */

public class TestInputValues {

    public static final Date CURRENT_DATE = Calendar.getInstance().getTime();

    // for Appointment table
    public static final String TEST_APPOINTMENT_LOCATION="TestLocation";
    public static final String TEST_APPOINTMENT_DESCRIPTION="TestAppointment";

    // for Medicine table
    public static final String TEST_MEDICINE_MEDICINE_NAME="TestMedicine";
    public static final String TEST_MEDICINE_DESCRIPTION="TestDescription";
    public static final boolean TEST_MEDICINE_REMIND=false;
    public static final int TEST_MEDICINE_QUANTITY=10;
    public static final int TEST_MEDICINE_DOSAGE=1;
    public static final int TEST_MEDICINE_CONSUME_QUANTITY=1;
    public static final int TEST_MEDICINE_THRESHOLD=5;
    public static final int TEST_MEDICINE_EXPIRE_FACTOR=5;
    public static final int TEST_MEDICINE_CATID=1;

    // for Remind table
    public static final int TEST_REMINDER_FREQUENCY=3;
    public static final int TEST_REMINDER_INTERVAL=3;

    // for Categories Table
    public static final int TEST_CATEGORIES_ID= 1;
    public static final String TEST_CATEGORIES_CATEGORY_NAME="TestCategory";
    public static final String TEST_CATEGORIES_CODE="TestCode";
    public static final String TEST_CATEGORIES_DESCRIPTION="TestDescription";
    public static final boolean TEST_CATEGORIES_REMIND=false;

    // for Measurement Table
    public static final int TEST_MEASUREMENT_SYSTOLIC=60;
    public static final int TEST_MEASUREMENT_DIASTOLIC=120;
    public static final int TEST_MEASUREMENT_PULSE=100;
    public static final float TEST_MEASUREMENT_TEMPERATURE=100.2f;
    public static final int TEST_MEASUREMENT_WEIGHT=70;

    // for ICE contact Table
    public static final String TEST_ICE_NAME="TestName";
    public static final String TEST_ICE_CONTACT_NUMBER="TestContactNo";
    public static final String TEST_ICE_CONTACT_TYPE="TestContactType";
    public static final String TEST_ICE_SEQUENCE="TestSequence";
    public static final String TEST_ICE_DESCRIPTION="TestDescription";

    // for Consumption Table
    public static final int TEST_CONSUMPTION_QUANTITY=1;

    //for HealthBio Table
    public static final String TEST_HEALTH_CONDITION="TestCondition";
    public static final String TEST_HEALTH_CONDITION_TYPE="TestConditionType";

    //for Personal Bio Table
    public static final String TEST_USER_NAME = "TestName";
    public static final String TEST_USER_DOB = "TestDOB";
    public static final String TEST_USER_ID_NO="TestIDNo";
    public static final String TEST_USER_ADDRESS="TestAddress";
    public static final String TEST_USER_POSTAL_CODE="TestPostalCode";
    public static final String TEST_USER_HEIGHT="TestHeight";
    public static final String TEST_USER_BLOOD_TYPE="TestBloodType";

}
