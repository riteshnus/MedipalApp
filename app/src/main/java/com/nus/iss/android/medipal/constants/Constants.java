package com.nus.iss.android.medipal.constants;

/**
 * Created by siddharth on 3/12/2017.
 */

public  class Constants {
    public static final String SIMPLE_DATE_FORMAT="dd-MM-yyyy";
    public static final int ADD_REMINDER_VALUE=0;
    public static final String CATEGORY_FOR_MEDICINE= "category_for_medicine";
    public static final String REMINDER_SET="reminder_set";
    public static final String REMINDER_START_TIME="reminder_start_time";
    public static final String MEDICINE_THRESHOLD="medicine_threshold";
    public static final String REMINDER_INTERVAL="reminder_interval";
    public static final int ADD_MEASUREMENT_LOADER=0;
    public static final String SIMPLE_TIME_FORMAT="HH:mm";
    public static final int ADD_MEDICINE_LOADER=1;
    public static final int PENDING_INTENT_ID = 1000;
    public static final int PENDINGINTENT_APPOINTMENT_ID = 2000;
    public static final int PENDING_INTENT_THRESHOLD_ID = 3000;
    public static final String MEDICINE_TIME = "Time";

    public enum DOSAGE {pills,cc,ml,gr,mg,drops,pieces,puffs,units,teaspoon,tablespoon,patch,mcg,
        l,meq,spray};
}
