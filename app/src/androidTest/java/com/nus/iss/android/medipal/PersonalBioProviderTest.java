package com.nus.iss.android.medipal;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ProviderTestCase2;
import android.util.Log;

import com.nus.iss.android.medipal.activity.PersonalInfoActivity;
import com.nus.iss.android.medipal.dao.PersonalBioDAO;
import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.data.MedipalDBHelper;
import com.nus.iss.android.medipal.dto.PersonalBio;
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
public class PersonalBioProviderTest extends ProviderTestCase2<ContentProvider> {
    /**
     * Constructor.
     *
     * @param providerClass     The class name of the provider under test
     * @param providerAuthority The provider's authority string
     */

    public static final String LOG_TAG = PersonalBioProviderTest.class.getSimpleName();
    MedipalDBHelper database;
    PersonalInfoActivity personalInfoActivity;

    public PersonalBioProviderTest() {
        super(ContentProvider.class, MedipalContract.CONTENT_AUTHORITY);
    }

    @Rule
    public ActivityTestRule<PersonalInfoActivity> intentsTestRule = new ActivityTestRule<>(PersonalInfoActivity.class);

    @Before
    public void setUp() throws Exception {
        database = new MedipalDBHelper(getTargetContext());
        personalInfoActivity = intentsTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        database.onUpgrade(sqLiteDatabase,1,2);
    }

    @Test
    public void testInsert() {
        PersonalBio personalBio = new PersonalBio(
                TestInputValues.TEST_USER_NAME,
                TestInputValues.CURRENT_DATE,
                TestInputValues.TEST_USER_ID_NO,
                TestInputValues.TEST_USER_ADDRESS,
                TestInputValues.TEST_USER_POSTAL_CODE,
                TestInputValues.TEST_USER_HEIGHT,
                TestInputValues.TEST_USER_BLOOD_TYPE);

        PersonalBioDAO personalBioDAO = new PersonalBioDAO(personalInfoActivity);
        Uri saveUri = personalBioDAO.save(personalBio);
        Log.i(LOG_TAG, "URI insert: " + saveUri);
        assertNotNull(saveUri);
    }

    @Test
    public void testUpdate() {
        PersonalBio personalBio = new PersonalBio(
                TestInputValues.TEST_USER_NAME,
                TestInputValues.CURRENT_DATE,
                TestInputValues.TEST_USER_ID_NO,
                TestInputValues.TEST_USER_ADDRESS,
                TestInputValues.TEST_USER_POSTAL_CODE,
                TestInputValues.TEST_USER_HEIGHT,
                TestInputValues.TEST_USER_BLOOD_TYPE);

        PersonalBioDAO personalBioDAO = new PersonalBioDAO(personalInfoActivity);
        Uri saveUriBeforeUpdate = personalBioDAO.save(personalBio);
        String updateId = String.valueOf(ContentUris.parseId(saveUriBeforeUpdate));
        int rowsAffected = personalBioDAO.update(personalBio,"_id=" + updateId,null);
        Log.i(LOG_TAG, "URI delete: " + saveUriBeforeUpdate);
        assertNotNull(saveUriBeforeUpdate);
        assertTrue(rowsAffected == 1);
    }

}
