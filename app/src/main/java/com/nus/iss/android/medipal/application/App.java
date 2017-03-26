package com.nus.iss.android.medipal.application;

import android.app.Application;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;

import com.nus.iss.android.medipal.dto.Medicine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by siddharth on 3/19/2017.
 */

public class App extends Application implements LoaderManager.LoaderCallbacks {
     public static List<Medicine> medicineList;
     private static final int MEDICINE_LOADER=0;
    @Override
    public void onCreate() {
        super.onCreate();
        medicineList=new ArrayList<>();

    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
