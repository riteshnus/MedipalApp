package com.nus.iss.android.medipal.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.activity.AddHealthBioActivity;
import com.nus.iss.android.medipal.adapter.HealthBioAdapter;
import com.nus.iss.android.medipal.callback.ToolbarActionModeCallback;
import com.nus.iss.android.medipal.dao.HealthBioDAO;
import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.listener.RecyclerClickListener;
import com.nus.iss.android.medipal.listener.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;


public class HealthBioFragment extends android.support.v4.app.Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private static View view;
    private static RecyclerView recyclerView;
    private static MatrixCursor dummyCursor;
    private static HealthBioAdapter hbAdapter;
    private ActionMode mActionMode;
    private static final int HEALTH_BIO_LOADER=0;
    public List<String> selectedViewsIds = new ArrayList<String>();

    public HealthBioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_health_bio, container, false);
        populateRecyclerView();
        implementRecyclerViewClickListeners();

        FloatingActionButton addHealthBioFab;
        addHealthBioFab = (FloatingActionButton) view.findViewById(R.id.hb_fab);
        addHealthBioFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hbIntent = new Intent(getContext(),AddHealthBioActivity.class);
                startActivity(hbIntent);
            }
        });
        return view;
    }

    private void populateRecyclerView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getLoaderManager().initLoader(HEALTH_BIO_LOADER,null,this);
        hbAdapter = new HealthBioAdapter(getActivity(), null);
        recyclerView.setAdapter(hbAdapter);
        hbAdapter.notifyDataSetChanged();
    }


    //Implement item click and long click on recycler view
    private void implementRecyclerViewClickListeners() {
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
                //If ActionMode not null select item
                if (mActionMode != null) {
                    onListItemSelect(position);
                    selectedViewsIds.add((String.valueOf(((TextView)view).getText())));
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                //Select item on long click
                onListItemSelect(position);
                selectedViewsIds.add((String.valueOf(((TextView)view.findViewById(R.id.hb_item_id)).getText())));
            }
        }));
    }

    //List item select method
    private void onListItemSelect(int position) {
        hbAdapter.toggleSelection(position);//Toggle the selection

        boolean hasCheckedItems = hbAdapter.getSelectedCount() > 0;//Check if any items are already selected or not


        if (hasCheckedItems && mActionMode == null)
            // there are some selected items, start the actionMode
            mActionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(new ToolbarActionModeCallback(getActivity(), hbAdapter,this));
        else if (!hasCheckedItems && mActionMode != null)
            // there no selected items, finish the actionMode
            mActionMode.finish();

        if (mActionMode != null)
            //set action mode title on item selection
            mActionMode.setTitle(String.valueOf(hbAdapter
                    .getSelectedCount()) + " selected");


    }

    //Set action mode null after use
    public void setNullToActionMode() {
        if (mActionMode != null)
            mActionMode = null;
    }


    //Delete selected rows
    public void deleteRows() {

        HealthBioDAO hbDAO = new HealthBioDAO(this.getActivity());

        for(String id : selectedViewsIds){
            hbDAO.delete("_id="+id);
        }
        SparseBooleanArray selected = hbAdapter
                .getSelectedIds();//Get selected ids

        Toast.makeText(getActivity(), selected.size() + " item(s) deleted.", Toast.LENGTH_SHORT).show();//Show Toast
        mActionMode.finish();//Finish action mode after use

    }


    // Loader
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                MedipalContract.PersonalEntry.HEALTH_BIO_ID,
                MedipalContract.PersonalEntry.HEALTH_CONDITION,
                MedipalContract.PersonalEntry.HEALTH_CONDITION_TYPE,
                MedipalContract.PersonalEntry.HEALTH_START_DATE};
        return new CursorLoader(this.getContext(), MedipalContract.PersonalEntry.CONTENT_URI_HEALTH_BIO, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        //cursor.moveToNext()
        hbAdapter.swapCursor(cursor);
        hbAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


}
