package com.nus.iss.android.medipal.dto;

import java.util.Date;

/**
 * Created by thushara on 3/22/2017.
 */

public class Weight extends Measurement {
    private int weight;

    public Weight (int weight , Date date){

        super(date);
        this.weight=weight;
    }


    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

}
