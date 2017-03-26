package com.nus.iss.android.medipal.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.data.MedipalContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Shubhanshu Gautam on 19/03/17.
 */

public class HealthBioAdapter extends RecyclerView.Adapter<HealthBioAdapter.HealthBioViewHolder> {

    private Context mContext;
    private CursorAdapter mCursorAdapter;
    //to select multiple items
    private SparseBooleanArray mSelectedItemsIds;
    private SimpleDateFormat sdfView = new SimpleDateFormat("EEE, d MMM yyyy");
    private SimpleDateFormat sdfDB= new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy");


    // only Constructor
    public HealthBioAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        mSelectedItemsIds = new SparseBooleanArray();

        this.mCursorAdapter = new CursorAdapter(context,cursor,0) {

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                // inflating view item
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View rootView  = inflater.inflate(R.layout.item_health_bio,parent,false);
                return rootView;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                // ToDo: use view.getTag -> viewholder and remove below finds..
                TextView condition = (TextView) view.findViewById(R.id.hb_condition);
                TextView conditionType = (TextView)view.findViewById(R.id.hb_condition_type);
                TextView startDate = (TextView) view.findViewById(R.id.hb_start_date);

                condition.setText(
                        cursor.getString(
                                cursor.getColumnIndex(MedipalContract.PersonalEntry.HEALTH_CONDITION)));

                if( cursor.getString(cursor.getColumnIndex(MedipalContract.PersonalEntry.HEALTH_CONDITION_TYPE))
                        .equals("A") )
                conditionType.setText("Allergy");
                else{
                    conditionType.setText("Condition");
                }

                try {
                    startDate.setText(
                            sdfView.format(sdfDB.parse(cursor.getString(
                                    cursor.getColumnIndex(MedipalContract.PersonalEntry.HEALTH_START_DATE))))
                            );
                } catch (ParseException e) {
                    e.printStackTrace();
                    //Toast.makeText(context, "error setting date", Toast.LENGTH_SHORT).show();
                }

                TextView _id_holder = (TextView) view.findViewById(R.id.hb_item_id);
                _id_holder.setText(cursor.getString(cursor.getColumnIndex("_id")));
/*
                conditionType.setText(
                        cursor.getString(
                                cursor.getColumnIndex(MedipalContract.HealthBioEntry.HEALTH_CONDITION_TYPE)));

               startDate.setText(
                        cursor.getString(
                                cursor.getColumnIndex(MedipalContract.HealthBioEntry.HEALTH_START_DATE)));

 */           }
        };
    } // *** End of Constructor ***

    @Override
    public int getItemCount() {
        return mCursorAdapter.getCount();

    }

    @Override
    public HealthBioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // passing the inflater job to cursor-adapter
        View v = mCursorAdapter.newView(mContext,mCursorAdapter.getCursor(),parent);

        HealthBioViewHolder vHolder = new HealthBioViewHolder(v);
        //v.setTag(vHolder);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(HealthBioViewHolder holder, int position) {
        // passing the binding operation to cursor loader
        mCursorAdapter.getCursor().moveToPosition(position);
        mCursorAdapter.bindView(holder.itemView, mContext, mCursorAdapter.getCursor());
        // Note : holder interacts with AdapterView here. itemView is the rootView (layout).
        holder.itemView.setBackgroundColor(
                mSelectedItemsIds.get(position) ? Color.LTGRAY : Color.TRANSPARENT);
    }



    /**
     * Methods required to do selections, remove selections, etc.
     */

    //Toggle selection methods
    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }


    //Remove selected selections
    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }


    //Put or delete selected position into SparseBooleanArray
    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);

        notifyDataSetChanged();
    }

    //Get total selected count
    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    //Return all selected ids
    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }


    public void swapCursor(Cursor cursor){
        Cursor oldCursor = mCursorAdapter.swapCursor(cursor);

/*        if(oldCursor!=null){
            oldCursor.close();
        }
*/
    }



    /**
     *  View Holder inner class
     */
    public class HealthBioViewHolder extends RecyclerView.ViewHolder {

        public TextView condition, startDate, conditionType;


        public HealthBioViewHolder(View rootView) {
            super(rootView);

            this.condition = (TextView) rootView.findViewById(R.id.hb_condition);
            this.startDate = (TextView) rootView.findViewById(R.id.hb_start_date);
            this.conditionType = (TextView) rootView.findViewById(R.id.hb_condition);

        }
    }
}
