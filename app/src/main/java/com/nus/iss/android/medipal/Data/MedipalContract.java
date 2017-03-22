package com.nus.iss.android.medipal.Data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Ritesh on 3/8/2017.
 */

public class MedipalContract {
    public static final String CONTENT_AUTHORITY = "com.nus.iss.android.medipal";
    public static final Uri BASE_CONTENT = Uri.parse("content://"+ CONTENT_AUTHORITY);
    public static final String PATH_PERSONAL = "personal";

    public static final class PersonalEntry implements BaseColumns {

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_PERSONAL;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_PERSONAL;
        public final static Uri CONTENT_URI_PERSONAL = Uri.withAppendedPath(BASE_CONTENT,PersonalEntry.USER_TABLE_NAME);
        public final static Uri CONTENT_URI_MEDICINE = Uri.withAppendedPath(BASE_CONTENT,PersonalEntry.MEDICINE_TABLE_NAME);
        public final static Uri CONTENT_URI_CATEGORY = Uri.withAppendedPath(BASE_CONTENT,PersonalEntry.CATEGORIES_TABLE_NAME);
        public final static Uri CONTENT_URI_REMINDER = Uri.withAppendedPath(BASE_CONTENT,PersonalEntry.REMINDER_TABLE_NAME);
        public final static Uri CONTENT_URI_APPOINTMENT = Uri.withAppendedPath(BASE_CONTENT,PersonalEntry.APPOINTMENT_TABLE_NAME);
        /*
        * Table Details for Personal Bio table
        * */
        public static final String USER_TABLE_NAME = "personalBio";
        public static final String USER_ID = BaseColumns._ID;
        public static final String USER_NAME = "Name";
        public static final String USER_DOB = "DOB";
        public static final String USER_ID_NO="IDNo";
        public static final String USER_ADDRESS="Address";
        public static final String USER_POSTAL_CODE="PostalCode";
        public static final String USER_HEIGHT="Height";
        public static final String USER_BLOOD_TYPE="BloodType";

        /*
       * Table Details for Health Bio table
       * */
        public static final String HEALTH_BIO_TABLE_NAME="healthBio";
        public static final String HEALTH_BIO_ID=BaseColumns._ID;
        public static final String HEALTH_CONDITION="Condition";
        public static final String HEALTH_START_DATE="StartDate";
        public static final String HEALTH_CONDITION_TYPE="ConditionType";

        /*
       * Table Details for Categories table
       * */
        public static final String CATEGORIES_TABLE_NAME="categories";
        public static final String CATEGORIES_ID=BaseColumns._ID;
        public static final String CATEGORIES_CATEGORY_NAME="Category";
        public static final String CATEGORIES_CODE="Code";
        public static final String CATEGORIES_DESCRIPTION="Description";
        public static final String CATEGORIES_REMIND="Remind";

        /*
       * Table Details for Medicine table
       * */
        public static final String MEDICINE_TABLE_NAME="medicine";
        public static final String MEDICINE_ID=BaseColumns._ID;
        public static final String MEDICINE_MEDICINE_NAME="Medicine";
        public static final String MEDICINE_CATID="CatID";
        public static final String MEDICINE_DESCRIPTION="Description";
        public static final String MEDICINE_REMIND="Remind";
        public static final String MEDICINE_REMINDERID="ReminderID";
        public static final String MEDICINE_QUANTITY="Quantity";
        public static final String MEDICINE_DOSAGE="Dosage";
        public static final String MEDICINE_CONSUME_QUANTITY="ConsumeQuantity";
        public static final String MEDICINE_THRESHOLD="Threshold";
        public static final String MEDICINE_DATE_ISSUED="DateIssued";
        public static final String MEDICINE_EXPIRE_FACTOR="ExpireFactor";

        /*
       * Table Details for Measurement table
       * */
        public static final String MEASUREMENT_TABLE_NAME="measurement";
        public static final String MEASUREMENT_ID=BaseColumns._ID;
        public static final String MEASUREMENT_SYSTOLIC="Systolic";
        public static final String MEASUREMENT_DIASTOLIC="Diastolic";
        public static final String MEASUREMENT_PULSE="Pulse";
        public static final String MEASUREMENT_TEMPERATURE="Temperature";
        public static final String MEASUREMENT_WEIGHT="Weight";
        public static final String MEASUREMENT_MEASURED_ON="MeasuredOn";

        /*
       * Table Details for Consumption table
       * */
        public static final String CONSUMPTION_TABLE_NAME="consumption";
        public static final String CONSUMPTION_ID=BaseColumns._ID;
        public static final String CONSUMPTION_MEDICINE_ID="MedicineID";
        public static final String CONSUMPTION_QUANTITY="Quantity";
        public static final String CONSUMPTION_CONSUMED_ON="ConsumedOn";

        /*
         * Table Details for Reminder table
         * */
        public static final String REMINDER_TABLE_NAME="reminder";
        public static final String REMINDER_ID=BaseColumns._ID;
        public static final String REMINDER_FREQUENCY="Frequency";
        public static final String REMINDER_START_TIME="StartTime";
        public static final String REMINDER_INTERVAL="Interval";

        /*
         * Table Details for Appointment table
         * */
        public static final String APPOINTMENT_TABLE_NAME="appointment";
        public static final String APPOINTMENT_ID=BaseColumns._ID;
        public static final String APPOINTMENT_LOCATION="Location";
        public static final String APPOINTMENT_DATE_TIME="Appointment";
        public static final String APPOINTMENT_DESCRIPTION="Description";

        /*
         * Table Details for ICE table
         * */
        public static final String ICE_TABLE_NAME="ICE";
        public static final String ICE_ID=BaseColumns._ID;
        public static final String ICE_NAME="Name";
        public static final String ICE_CONTACT_NUMBER="ContactNo";
        public static final String ICE_CONTACT_TYPE="ContactType";
        public static final String ICE_SEQUENCE="Sequence";
        public static final String ICE_DESCRIPTION="Description";

    }
}
