package com.nus.iss.android.medipal.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;

import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.data.MedipalDBHelper;

public class SplashScreenActivity extends Activity {



    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 500;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_slash_screen);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run()
            {
                if (!isGuestLaunch()) {
                    Intent intent = new Intent();
                    intent.setClass(SplashScreenActivity.this, NavDrawerActivity.class);

                    SplashScreenActivity.this.startActivity(intent);
                    SplashScreenActivity.this.finish();
                } else{
                    Intent intent = new Intent();
                    intent.setClass(SplashScreenActivity.this, PersonalInfoActivity.class);
                    startActivity(intent);
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private boolean isGuestLaunch(){
        boolean result=true;
        MedipalDBHelper dbHelper = new MedipalDBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT "+ MedipalContract.PersonalEntry.USER_NAME+" FROM " +MedipalContract.PersonalEntry.USER_TABLE_NAME,null);
        if(c.moveToFirst())  {
            if(!(c.getString(c.getColumnIndex(MedipalContract.PersonalEntry.USER_NAME)).isEmpty())) {
                result = false;
            }
        }
        if(!c.isClosed()) c.close();
        if(db.isOpen())db.close();
        return result;
    }

}
