package com.nus.iss.android.medipal;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.test.runner.AndroidJUnit4;
import android.test.ProviderTestCase2;
import android.util.Log;

import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.data.MedipalDBHelper;
import com.nus.iss.android.medipal.dto.Consumption;
import com.nus.iss.android.medipal.testData.TestInputValues;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;

/**
 * Created by Ritesh on 3/27/2017.
 */
@RunWith(AndroidJUnit4.class)
public class ConsumptionProviderTest extends ProviderTestCase2<ContentProvider> {
    /**
     * Constructor.
     *
     * @param providerClass     The class name of the provider under test
     * @param providerAuthority The provider's authority string
     */

    public static final String LOG_TAG = ConsumptionProviderTest.class.getSimpleName();
    MedipalDBHelper database;

    public ConsumptionProviderTest() {
        super(ContentProvider.class, MedipalContract.CONTENT_AUTHORITY);
    }

    @Before
    public void setUp() throws Exception {
        database = new MedipalDBHelper(getTargetContext());
    }

    @After
    public void tearDown() throws Exception {
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        database.onUpgrade(sqLiteDatabase,1,2);
    }

    @Test
    public void testInsert() {
        Consumption consumption = new Consumption(
                TestInputValues.TEST_CONSUMPTION_QUANTITY,
                TestInputValues.CURRENT_DATE);

        int medicineId = 1;
        ContentValues values=new ContentValues();
        values.put(MedipalContract.PersonalEntry.CONSUMPTION_MEDICINE_ID,medicineId);
        values.put(MedipalContract.PersonalEntry.CONSUMPTION_QUANTITY,String.valueOf(consumption.getQuantity()));
        values.put(MedipalContract.PersonalEntry.CONSUMPTION_CONSUMED_ON, String.valueOf(consumption.getConsumedOn()));
        Uri newUri=getInstrumentation().getTargetContext().getContentResolver().insert(MedipalContract.PersonalEntry.CONTENT_URI_CONSUMPTION,values);
        Log.i(LOG_TAG, "uri: " + newUri);
        assertNotNull(newUri);
    }
}
