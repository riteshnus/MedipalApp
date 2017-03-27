package com.nus.iss.android.medipal;

import android.content.ContentProvider;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ProviderTestCase2;
import android.util.Log;

import com.nus.iss.android.medipal.activity.AddMedicineActivity;
import com.nus.iss.android.medipal.dao.MedicineDAO;
import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.data.MedipalDBHelper;
import com.nus.iss.android.medipal.dto.Categories;
import com.nus.iss.android.medipal.dto.Medicine;
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
public class MedicineProviderTest extends ProviderTestCase2<ContentProvider> {
    /**
     * Constructor.
     *
     * @param providerClass     The class name of the provider under test
     * @param providerAuthority The provider's authority string
     */

    public static final String LOG_TAG = MedicineProviderTest.class.getSimpleName();
    MedipalDBHelper database;
    AddMedicineActivity addMedicineActivity;

    public MedicineProviderTest() {
        super(ContentProvider.class, MedipalContract.CONTENT_AUTHORITY);
    }

    @Rule
    public ActivityTestRule<AddMedicineActivity> intentsTestRule = new ActivityTestRule<>(AddMedicineActivity.class);

    @Before
    public void setUp() throws Exception {
        database = new MedipalDBHelper(getTargetContext());
        addMedicineActivity = intentsTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        database.onUpgrade(sqLiteDatabase,1,2);
    }

    @Test
    public void testInsert() {
        Medicine medicine = new Medicine(
                TestInputValues.TEST_MEDICINE_MEDICINE_NAME,
                TestInputValues.TEST_MEDICINE_DESCRIPTION,
                TestInputValues.TEST_MEDICINE_REMIND,
                TestInputValues.TEST_MEDICINE_QUANTITY,
                TestInputValues.TEST_MEDICINE_DOSAGE,
                TestInputValues.TEST_MEDICINE_CONSUME_QUANTITY,
                TestInputValues.TEST_MEDICINE_THRESHOLD,
                TestInputValues.CURRENT_DATE,
                TestInputValues.TEST_MEDICINE_EXPIRE_FACTOR);

        Reminder reminder = new Reminder(
                TestInputValues.TEST_REMINDER_FREQUENCY,
                TestInputValues.CURRENT_DATE,
                TestInputValues.TEST_REMINDER_INTERVAL);

        Categories categories = new Categories(
                TestInputValues.TEST_CATEGORIES_ID,
                TestInputValues.TEST_CATEGORIES_CATEGORY_NAME,
                TestInputValues.TEST_CATEGORIES_CODE,
                TestInputValues.TEST_CATEGORIES_DESCRIPTION,
                TestInputValues.TEST_CATEGORIES_REMIND);

        medicine.setCategory(categories);
        medicine.setReminder(reminder);
        MedicineDAO medicineDAO = new MedicineDAO(addMedicineActivity);
        Uri saveUri = medicineDAO.save(medicine);
        Log.i(LOG_TAG, "URI insert: " + saveUri);
        assertNotNull(saveUri);
    }

    @Test
    public void testUpdate() {
        Medicine medicine = new Medicine(
                TestInputValues.TEST_MEDICINE_MEDICINE_NAME,
                TestInputValues.TEST_MEDICINE_DESCRIPTION,
                TestInputValues.TEST_MEDICINE_REMIND,
                TestInputValues.TEST_MEDICINE_QUANTITY,
                TestInputValues.TEST_MEDICINE_DOSAGE,
                TestInputValues.TEST_MEDICINE_CONSUME_QUANTITY,
                TestInputValues.TEST_MEDICINE_THRESHOLD,
                TestInputValues.CURRENT_DATE,
                TestInputValues.TEST_MEDICINE_EXPIRE_FACTOR);

        Reminder reminder = new Reminder(
                TestInputValues.TEST_REMINDER_FREQUENCY,
                TestInputValues.CURRENT_DATE,
                TestInputValues.TEST_REMINDER_INTERVAL);

        Categories categories = new Categories(
                TestInputValues.TEST_CATEGORIES_ID,
                TestInputValues.TEST_CATEGORIES_CATEGORY_NAME,
                TestInputValues.TEST_CATEGORIES_CODE,
                TestInputValues.TEST_CATEGORIES_DESCRIPTION,
                TestInputValues.TEST_CATEGORIES_REMIND);

        medicine.setCategory(categories);
        medicine.setReminder(reminder);
        MedicineDAO medicineDAO = new MedicineDAO(addMedicineActivity);
        Uri saveUriBeforeUpdate = medicineDAO.save(medicine);
        int rowsAffected = medicineDAO.update(medicine, saveUriBeforeUpdate);
        Log.i(LOG_TAG, "URI update: " + saveUriBeforeUpdate);
        assertNotNull(saveUriBeforeUpdate);
        assertTrue(rowsAffected == 1);
    }

    @Test
    public void testDelete() {
        Medicine medicine = new Medicine(
                TestInputValues.TEST_MEDICINE_MEDICINE_NAME,
                TestInputValues.TEST_MEDICINE_DESCRIPTION,
                TestInputValues.TEST_MEDICINE_REMIND,
                TestInputValues.TEST_MEDICINE_QUANTITY,
                TestInputValues.TEST_MEDICINE_DOSAGE,
                TestInputValues.TEST_MEDICINE_CONSUME_QUANTITY,
                TestInputValues.TEST_MEDICINE_THRESHOLD,
                TestInputValues.CURRENT_DATE,
                TestInputValues.TEST_MEDICINE_EXPIRE_FACTOR);

        Reminder reminder = new Reminder(
                TestInputValues.TEST_REMINDER_FREQUENCY,
                TestInputValues.CURRENT_DATE,
                TestInputValues.TEST_REMINDER_INTERVAL);

        Categories categories = new Categories(
                TestInputValues.TEST_CATEGORIES_ID,
                TestInputValues.TEST_CATEGORIES_CATEGORY_NAME,
                TestInputValues.TEST_CATEGORIES_CODE,
                TestInputValues.TEST_CATEGORIES_DESCRIPTION,
                TestInputValues.TEST_CATEGORIES_REMIND);

        medicine.setCategory(categories);
        medicine.setReminder(reminder);
        MedicineDAO medicineDAO = new MedicineDAO(addMedicineActivity);
        Uri saveUriBeforeDelete = medicineDAO.save(medicine);
        int rowsAffected = medicineDAO.delete(saveUriBeforeDelete);
        Log.i(LOG_TAG, "URI delete: " + saveUriBeforeDelete);
        assertNotNull(saveUriBeforeDelete);
        assertTrue(rowsAffected == 1);
    }
}
