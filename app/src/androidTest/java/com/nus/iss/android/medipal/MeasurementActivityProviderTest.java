package com.nus.iss.android.medipal;

import android.content.ContentProvider;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ProviderTestCase2;
import android.util.Log;

import com.nus.iss.android.medipal.activity.MeasurementActivity;
import com.nus.iss.android.medipal.dao.MeasurementDAO;
import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.data.MedipalDBHelper;
import com.nus.iss.android.medipal.dto.BloodPressure;
import com.nus.iss.android.medipal.dto.Pulse;
import com.nus.iss.android.medipal.dto.Temperature;
import com.nus.iss.android.medipal.dto.Weight;
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
public class MeasurementActivityProviderTest extends ProviderTestCase2<ContentProvider> {
    /**
     * Constructor.
     *
     * @param providerClass     The class name of the provider under test
     * @param providerAuthority The provider's authority string
     */

    public static final String LOG_TAG = MeasurementActivityProviderTest.class.getSimpleName();
    MedipalDBHelper database;
    MeasurementActivity measurementActivity;

    public MeasurementActivityProviderTest() {
        super(ContentProvider.class, MedipalContract.CONTENT_AUTHORITY);
    }

    @Rule
    public ActivityTestRule<MeasurementActivity> intentsTestRule = new ActivityTestRule<>(MeasurementActivity.class);

    @Before
    public void setUp() throws Exception {
        database = new MedipalDBHelper(getTargetContext());
        measurementActivity = intentsTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        database.onUpgrade(sqLiteDatabase,1,2);
    }

    @Test
    public void testInsertBp() {
        BloodPressure bloodPressure = new BloodPressure(
                TestInputValues.TEST_MEASUREMENT_SYSTOLIC,
                TestInputValues.TEST_MEASUREMENT_DIASTOLIC,
                TestInputValues.CURRENT_DATE);

        MeasurementDAO measurementDAO = new MeasurementDAO(measurementActivity);
        Uri saveUriBp = measurementDAO.saveBp(bloodPressure);
        Log.i(LOG_TAG, "URI insert: " + saveUriBp);
        assertNotNull(saveUriBp);
    }

    @Test
    public void testInsertPulse() {
        Pulse pulse = new Pulse(
                TestInputValues.TEST_MEASUREMENT_PULSE,
                TestInputValues.CURRENT_DATE);

        MeasurementDAO measurementDAO = new MeasurementDAO(measurementActivity);
        Uri saveUriPulse = measurementDAO.savePulse(pulse);
        Log.i(LOG_TAG, "URI insert: " + saveUriPulse);
        assertNotNull(saveUriPulse);
    }

    @Test
    public void testInsertWeight() {
        Weight weight = new Weight(
                TestInputValues.TEST_MEASUREMENT_WEIGHT,
                TestInputValues.CURRENT_DATE);

        MeasurementDAO measurementDAO = new MeasurementDAO(measurementActivity);
        Uri saveUriWeight = measurementDAO.saveWeight(weight);
        Log.i(LOG_TAG, "URI insert: " + saveUriWeight);
        assertNotNull(saveUriWeight);
    }

    @Test
    public void testInsertTemperature() {
        Temperature temperature = new Temperature(
                TestInputValues.TEST_MEASUREMENT_TEMPERATURE,
                TestInputValues.CURRENT_DATE);

        MeasurementDAO measurementDAO = new MeasurementDAO(measurementActivity);
        Uri saveUriTemperature = measurementDAO.saveTemp(temperature);
        Log.i(LOG_TAG, "URI insert: " + saveUriTemperature);
        assertNotNull(saveUriTemperature);
    }
}
