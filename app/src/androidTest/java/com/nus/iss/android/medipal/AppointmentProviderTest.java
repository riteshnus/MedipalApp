package com.nus.iss.android.medipal;

import android.content.ContentProvider;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ProviderTestCase2;
import android.util.Log;

import com.nus.iss.android.medipal.activity.AddAppointmentActivity;
import com.nus.iss.android.medipal.dao.AppointmentDAO;
import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.data.MedipalDBHelper;
import com.nus.iss.android.medipal.dto.Appointment;
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
public class AppointmentProviderTest extends ProviderTestCase2<ContentProvider> {
    /**
     * Constructor.
     *
     * @param providerClass     The class name of the provider under test
     * @param providerAuthority The provider's authority string
     */

    public static final String LOG_TAG = AppointmentProviderTest.class.getSimpleName();
    MedipalDBHelper database;
    AddAppointmentActivity addAppointmentActivity;

    public AppointmentProviderTest() {
        super(ContentProvider.class, MedipalContract.CONTENT_AUTHORITY);
    }

    @Rule
    public ActivityTestRule<AddAppointmentActivity> intentsTestRule = new ActivityTestRule<>(AddAppointmentActivity.class);

    @Before
    public void setUp() throws Exception {
        database = new MedipalDBHelper(getTargetContext());
        addAppointmentActivity = intentsTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        database.onUpgrade(sqLiteDatabase,1,2);
    }

    @Test
    public void testInsert() {
        Appointment appointment = new Appointment(
                TestInputValues.TEST_APPOINTMENT_DESCRIPTION,
                TestInputValues.TEST_APPOINTMENT_LOCATION,
                TestInputValues.CURRENT_DATE);

        AppointmentDAO appointmentDAO = new AppointmentDAO(addAppointmentActivity);
        Uri saveUri = appointmentDAO.save(appointment);
        Log.i(LOG_TAG, "URI insert: " + saveUri);
        assertNotNull(saveUri);
    }

    @Test
    public void testUpdate() {
        Appointment appointment = new Appointment(
                TestInputValues.TEST_APPOINTMENT_DESCRIPTION,
                TestInputValues.TEST_APPOINTMENT_LOCATION,
                TestInputValues.CURRENT_DATE);

        AppointmentDAO appointmentDAO = new AppointmentDAO(addAppointmentActivity);
        Uri saveUriBeforeUpdate = appointmentDAO.save(appointment);
        int rowsAffected = appointmentDAO.update(appointment,saveUriBeforeUpdate);
        Log.i(LOG_TAG, "URI update: " + saveUriBeforeUpdate);
        assertNotNull(saveUriBeforeUpdate);
        assertTrue(rowsAffected == 1);
    }

    @Test
    public void testDelete() {
        Appointment appointment = new Appointment(
                TestInputValues.TEST_APPOINTMENT_DESCRIPTION,
                TestInputValues.TEST_APPOINTMENT_LOCATION,
                TestInputValues.CURRENT_DATE);

        AppointmentDAO appointmentDAO = new AppointmentDAO(addAppointmentActivity);
        Uri saveUriBeforeDelete = appointmentDAO.save(appointment);
        int rowsAffected = appointmentDAO.delete(saveUriBeforeDelete);
        Log.i(LOG_TAG, "URI delete: " + saveUriBeforeDelete);
        assertNotNull(saveUriBeforeDelete);
        assertTrue(rowsAffected == 1);
    }
}
