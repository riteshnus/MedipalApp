package com.nus.iss.android.medipal;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ProviderTestCase2;
import android.util.Log;

import com.nus.iss.android.medipal.activity.AddHealthBioActivity;
import com.nus.iss.android.medipal.dao.HealthBioDAO;
import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.data.MedipalDBHelper;
import com.nus.iss.android.medipal.dto.HealthBio;
import com.nus.iss.android.medipal.testData.TestInputValues;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.InstrumentationRegistry.getTargetContext;

/**
 * Created by Ritesh on 3/27/2017.
 */
@RunWith(AndroidJUnit4.class)
public class HealthBioProviderTest extends ProviderTestCase2<ContentProvider> {
    /**
     * Constructor.
     *
     * @param providerClass     The class name of the provider under test
     * @param providerAuthority The provider's authority string
     */

    public static final String LOG_TAG = HealthBioProviderTest.class.getSimpleName();
    MedipalDBHelper database;
    AddHealthBioActivity addHealthBioActivity;

    public HealthBioProviderTest() {
        super(ContentProvider.class, MedipalContract.CONTENT_AUTHORITY);
    }

    @Rule
    public ActivityTestRule<AddHealthBioActivity> intentsTestRule = new ActivityTestRule<>(AddHealthBioActivity.class);

    @Before
    public void setUp() throws Exception {
        database = new MedipalDBHelper(getTargetContext());
        addHealthBioActivity = intentsTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        database.onUpgrade(sqLiteDatabase,1,2);
    }

    @Test
    public void testInsert() {
        HealthBioDAO healthBioDAO = new HealthBioDAO(addHealthBioActivity);
        for(int i=1;i<5;i++){
            HealthBio healthBio = new HealthBio(
                    TestInputValues.TEST_HEALTH_CONDITION+i,
                    TestInputValues.CURRENT_DATE,
                    TestInputValues.TEST_HEALTH_CONDITION_TYPE);
            Uri saveUri = healthBioDAO.save(healthBio);
            Log.i(LOG_TAG, "URI insert: " + saveUri);
            assertNotNull(saveUri);
        }
    }

    @Test
    public void testDelete() {
        HealthBioDAO healthBioDAO = new HealthBioDAO(addHealthBioActivity);
        List<String> selectedDeleteIds = new ArrayList<String>();
        for(int i=1;i<3;i++){
            HealthBio healthBio = new HealthBio(
                    TestInputValues.TEST_HEALTH_CONDITION+i,
                    TestInputValues.CURRENT_DATE,
                    TestInputValues.TEST_HEALTH_CONDITION_TYPE);
            Uri saveUri = healthBioDAO.save(healthBio);
            Log.i(LOG_TAG, "URI insert: " + saveUri);
            assertNotNull(saveUri);
            selectedDeleteIds.add(String.valueOf(ContentUris.parseId(saveUri)));
        }

        for(String id : selectedDeleteIds){
            int rowsAffected = healthBioDAO.delete("_id="+id);
            Log.i(LOG_TAG, "URI delete id: " + id);
            assertTrue(rowsAffected == 1);
        }
    }
}

