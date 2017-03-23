package com.nus.iss.android.medipal.listener;

import android.view.View;

/**
 * Created by Gautam on 21/03/17.
 */

public interface RecyclerClickListener {

    void onClick(View view, int position);

    void onLongClick(View view, int position);

}
