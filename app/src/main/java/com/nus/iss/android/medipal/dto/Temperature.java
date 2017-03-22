package com.nus.iss.android.medipal.dto;

import java.util.Date;

/**
 * Created by thushara on 3/22/2017.
 */

public class Temperature extends Measurement {
    private float temperature;

    public Temperature(float temperature, Date date){
        super(date);
        this.temperature=temperature;

    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }


}
