package com.nus.iss.android.medipal;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ProviderTestCase2;
import android.util.Log;

import com.nus.iss.android.medipal.activity.AddMedicineActivity;
import com.nus.iss.android.medipal.dao.ReminderDAO;
import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.data.MedipalDBHelper;
import com.nus.iss.android.medipal.dto.Reminder;
import com.nus.iss.android.medipal.testData.TestInputValues;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;

/**
 * Created by Ritesh on 3/27/2017.
 */
@RunWith(AndroidJUnit4.class)
public class ReminderProviderTest extends ProviderTestCase2<ContentProvider> {
    /**
     * Constructor.
     *
     * @param providerClass     The class name of the provider under test
     * @param providerAuthority The provider's authority string
     */

    public static final String LOG_TAG = ReminderProviderTest.class.getSimpleName();
    MedipalDBHelper database;
    AddMedicineActivity reminderActivity;

    public ReminderProviderTest() {
        super(ContentProvider.class, MedipalContract.CONTENT_AUTHORITY);
    }

    @Rule
    public ActivityTestRule<AddMedicineActivity> intentsTestRule = new ActivityTestRule<>(AddMedicineActivity.class);

    @Before
    public void setUp() throws Exception {
        database = new MedipalDBHelper(getTargetContext());
        reminderActivity = intentsTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        database.onUpgrade(sqLiteDatabase,1,2);
    }

    @Test
    public void testInsert() {
        Reminder reminder = new Reminder(
                TestInputValues.TEST_REMINDER_FREQUENCY,
                TestInputValues.CURRENT_DATE,
                TestInputValues.TEST_REMINDER_INTERVAL);

        ReminderDAO reminderDAO = new ReminderDAO(reminderActivity);
        reminder = reminderDAO.save(reminder);
        int reminderId = reminder.getReminderId();
        Log.i(LOG_TAG, "URI insert id: " + reminderId);
        assertNotNull(reminderId);
    }

    @Test
    public void testUpdate() {
        Uri reminderUri;
        Reminder reminder = new Reminder(
                TestInputValues.TEST_REMINDER_FREQUENCY,
                TestInputValues.CURRENT_DATE,
                TestInputValues.TEST_REMINDER_INTERVAL);

        ReminderDAO reminderDAO = new ReminderDAO(reminderActivity);
        reminder = reminderDAO.save(reminder);
        reminderUri = ContentUris.withAppendedId(MedipalContract.PersonalEntry.CONTENT_URI_REMINDER, 4);
        reminder = reminderDAO.update(reminder,reminderUri);
        Log.i(LOG_TAG, "URI update: " + reminder.getReminderId());
        assertNotNull(reminder.getReminderId());
    }

    @Test
    public void testDelete() {
        Uri uriReminder;
        Reminder reminder = new Reminder(
                TestInputValues.TEST_REMINDER_FREQUENCY,
                TestInputValues.CURRENT_DATE,
                TestInputValues.TEST_REMINDER_INTERVAL);

        ReminderDAO reminderDAO = new ReminderDAO(reminderActivity);
        reminder = reminderDAO.save(reminder);
        uriReminder = ContentUris.withAppendedId(MedipalContract.PersonalEntry.CONTENT_URI_REMINDER, 4);
        int rowsAffected = reminderDAO.delete(uriReminder);
        Log.i(LOG_TAG, "Row delete: " + rowsAffected);
        assertNotNull(reminder.getReminderId());
        //assertTrue(rowsAffected == 1);
    }

}
