package com.nus.iss.android.medipal.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.activity.AddHealthBioActivity;
import com.nus.iss.android.medipal.adapter.HealthBioAdapter;
import com.nus.iss.android.medipal.callback.ToolbarActionModeCallback;
import com.nus.iss.android.medipal.data.MedipalContract;
import com.nus.iss.android.medipal.listener.RecyclerClickListener;
import com.nus.iss.android.medipal.listener.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.Date;


public class HealthBioFragment extends android.support.v4.app.Fragment {


    private static View view;
    private static RecyclerView recyclerView;
    private static MatrixCursor dummyCursor;
    private static HealthBioAdapter hbAdapter;
    private ActionMode mActionMode;
    private FloatingActionButton addHealthBio;

    public HealthBioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_health_bio, container, false);
        populateRecyclerView();
        implementRecyclerViewClickListeners();
        return view;
    }

    // ToDo : implement fetch cursor
    //Populate Recycler with dummy data
    private void populateRecyclerView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dummyCursor = new MatrixCursor(new String[]{
                MedipalContract.HealthBioEntry.HEALTH_BIO_ID,
                MedipalContract.HealthBioEntry.HEALTH_CONDITION,
                MedipalContract.HealthBioEntry.HEALTH_CONDITION_TYPE,
                MedipalContract.HealthBioEntry.HEALTH_START_DATE});
        // item_models = new ArrayList<>();
        for (int i = 1; i <= 40; i++){
            //item_models.add(new HealthBio("Condition " + i, new Date(System.currentTimeMillis()), "C" + i));
            dummyCursor.addRow(new String[]{i+"","Condition " + i, new Date(System.currentTimeMillis()).toString(), "C" + i});
        }

        hbAdapter = new HealthBioAdapter(getActivity(), dummyCursor);
        recyclerView.setAdapter(hbAdapter);
        hbAdapter.notifyDataSetChanged();
    }


    //Implement item click and long click over recycler view
    private void implementRecyclerViewClickListeners() {
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
                //If ActionMode not null select item
                if (mActionMode != null)
                    onListItemSelect(position);
            }

            @Override
            public void onLongClick(View view, int position) {
                //Select item on long click
                onListItemSelect(position);
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

    public void sendAddHealthBio(View view)
    {
        Intent intent = new Intent(getContext(),AddHealthBioActivity.class);
        startActivity(intent);
    }


    //Delete selected rows
    public void deleteRows() {
        SparseBooleanArray selected = hbAdapter
                .getSelectedIds();//Get selected ids

        //Loop all selected ids
        for (int i = (selected.size() - 1); i >= 0; i--) {
            if (selected.valueAt(i)) {
                //ToDo : db.delete
                //If current id is selected remove the item via key
                //item_models.remove(selected.keyAt(i));
                //dummyCursor.
                //hbAdapter.notifyDataSetChanged(); //notify hbAdapter

            }
        }
        Toast.makeText(getActivity(), selected.size() + " item(s) deleted.", Toast.LENGTH_SHORT).show();//Show Toast
        mActionMode.finish();//Finish action mode after use

    }
}
