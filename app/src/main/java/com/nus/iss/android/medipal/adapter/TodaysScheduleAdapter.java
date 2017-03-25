package com.nus.iss.android.medipal.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nus.iss.android.medipal.R;
import com.nus.iss.android.medipal.dto.ScheduledEventJoin;
import com.nus.iss.android.medipal.dto.ScheduledItem;

import java.util.List;

/**
 * Created by Gautam on 24/03/17.
 */

public class TodaysScheduleAdapter extends ArrayAdapter<ScheduledItem> {

    private Context mContext;
    private List<ScheduledItem> scheduledActivities;


    public TodaysScheduleAdapter(@NonNull Context context, @NonNull List<ScheduledItem> scheduledActivities) {
        super(context, R.layout.item_today_schedule, scheduledActivities);
        this.mContext=context;
        this.scheduledActivities=scheduledActivities;
    }

    @Nullable
    @Override
    public ScheduledItem getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TodaysScheduleAdapter.ViewHolder viewholder=null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.item_today_schedule, null);
            viewholder.desc = (TextView) convertView.findViewById(R.id.schedule_item);
            viewholder.time = (TextView) convertView.findViewById(R.id.schedule_time);
            viewholder.subtitle1 = (TextView) convertView.findViewById(R.id.schedule_subtitle_1);
            convertView.setTag(viewholder);
        }else{
            viewholder = (TodaysScheduleAdapter.ViewHolder) convertView.getTag();
        }
        ScheduledItem scheduledEventJoin = (ScheduledItem)getItem(position);
        if(!scheduledEventJoin.isAppointment()) {
            //viewholder.desc.setText(scheduledActivity.get);
        }else{

        }
        return convertView;
    }
    @Override
    public int getCount() {
        return super.getCount();
    }

    private class ViewHolder{
        TextView desc;
        TextView time;
        TextView subtitle1;
        TextView subtitle2;
    }

}
