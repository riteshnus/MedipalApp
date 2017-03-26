package com.nus.iss.android.medipal.dto;

import java.util.Date;

/**
 * Created by siddharth on 3/11/2017.
 */

public class Measurement {

    private Date measuredOn;

    public Measurement(Date measuredOn) {
                this.measuredOn=measuredOn;
    }
    public Measurement(){}

        public Date getMeasuredOn() {
        return measuredOn;
    }

    public void setMeasuredOn(Date measuredOn) {
        this.measuredOn = measuredOn;
    }
}
