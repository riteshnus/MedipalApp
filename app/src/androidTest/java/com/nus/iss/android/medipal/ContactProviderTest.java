package com.nus.iss.android.medipal;

import android.content.ContentProvider;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ProviderTestCase2;
import android.util.Log;

import com.nus.iss.android.medipal.activity.AddContactActivity;
import com.nus.iss.android.medipal.dao.ContactDAO;
import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.data.MedipalDBHelper;
import com.nus.iss.android.medipal.dto.ICEContact;
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
public class ContactProviderTest extends ProviderTestCase2<ContentProvider> {
    /**
     * Constructor.
     *
     * @param providerClass     The class name of the provider under test
     * @param providerAuthority The provider's authority string
     */

    public static final String LOG_TAG = ContactProviderTest.class.getSimpleName();
    MedipalDBHelper database;
    AddContactActivity addContactActivity;

    public ContactProviderTest() {
        super(ContentProvider.class, MedipalContract.CONTENT_AUTHORITY);
    }

    @Rule
    public ActivityTestRule<AddContactActivity> intentsTestRule = new ActivityTestRule<>(AddContactActivity.class);

    @Before
    public void setUp() throws Exception {
        database = new MedipalDBHelper(getTargetContext());
        addContactActivity = intentsTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        database.onUpgrade(sqLiteDatabase,1,2);
    }

    @Test
    public void testInsert() {
        ICEContact iceContact = new ICEContact(
                TestInputValues.TEST_ICE_NAME,
                TestInputValues.TEST_ICE_CONTACT_NUMBER,
                TestInputValues.TEST_ICE_CONTACT_TYPE,
                TestInputValues.TEST_ICE_DESCRIPTION,
                TestInputValues.TEST_ICE_SEQUENCE);
        ContactDAO contactDAO = new ContactDAO(addContactActivity);
        Uri saveUri = contactDAO.saveIce(iceContact);
        Log.i(LOG_TAG, "URI insert: " + saveUri);
        assertNotNull(saveUri);
    }

    @Test
    public void testUpdate() {
        ICEContact iceContact = new ICEContact(
                TestInputValues.TEST_ICE_NAME,
                TestInputValues.TEST_ICE_CONTACT_NUMBER,
                TestInputValues.TEST_ICE_CONTACT_TYPE,
                TestInputValues.TEST_ICE_DESCRIPTION,
                TestInputValues.TEST_ICE_SEQUENCE);
        ContactDAO contactDAO = new ContactDAO(addContactActivity);
        Uri saveUriBeforeUpdate = contactDAO.saveIce(iceContact);
        int rowsAffected = contactDAO.update(iceContact,saveUriBeforeUpdate);
        Log.i(LOG_TAG, "URI update: " + saveUriBeforeUpdate);
        assertNotNull(saveUriBeforeUpdate);
        assertTrue(rowsAffected == 1);
    }

    @Test
    public void testDelete() {
        ICEContact iceContact = new ICEContact(
                TestInputValues.TEST_ICE_NAME,
                TestInputValues.TEST_ICE_CONTACT_NUMBER,
                TestInputValues.TEST_ICE_CONTACT_TYPE,
                TestInputValues.TEST_ICE_DESCRIPTION,
                TestInputValues.TEST_ICE_SEQUENCE);
        ContactDAO contactDAO = new ContactDAO(addContactActivity);
        Uri saveUriBeforeDelete = contactDAO.saveIce(iceContact);
        int rowsAffected = contactDAO.delete(saveUriBeforeDelete);
        Log.i(LOG_TAG, "URI update: " + saveUriBeforeDelete);
        assertNotNull(saveUriBeforeDelete);
        assertTrue(rowsAffected == 1);
    }
}
