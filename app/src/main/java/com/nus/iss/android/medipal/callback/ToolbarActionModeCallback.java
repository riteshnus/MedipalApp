package com.nus.iss.android.medipal.callback;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.adapter.HealthBioAdapter;
import com.nus.iss.android.medipal.fragment.HealthBioFragment;


/**
 * Created by Shubhanshu Gautam on 21/03/17.
 */

public class ToolbarActionModeCallback implements ActionMode.Callback {

    private Context mContext ;
    private HealthBioAdapter mHealthBioAdapter;
    private Fragment mFragment;

    public ToolbarActionModeCallback(Context context, HealthBioAdapter healthBioAdapter, Fragment fragment) {
        this.mContext = context;
        this.mHealthBioAdapter = healthBioAdapter;
        this.mFragment = fragment;
    }


    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.action_menu, menu);//Inflate the menu over action mode
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        menu.findItem(R.id.action_delete).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_delete:
                HealthBioFragment hbFragment = (HealthBioFragment)mFragment;
                if (hbFragment != null)
                    hbFragment.deleteRows();
                break;
        }
        return false;

    }
    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mHealthBioAdapter.removeSelection();
        HealthBioFragment hbFragment = (HealthBioFragment) mFragment;
            if (hbFragment != null)
                hbFragment.setNullToActionMode();
    }
}

