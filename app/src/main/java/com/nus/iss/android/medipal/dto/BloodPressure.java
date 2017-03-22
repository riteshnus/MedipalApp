package com.nus.iss.android.medipal.dto;

import java.util.Date;

/**
 * Created by thushara on 3/22/2017.
 */

public class BloodPressure extends Measurement {
    private int systolic;
    private int diastolic;

    public  BloodPressure (int systolic, int diastolic, Date date){
        super(date);
        this.systolic=systolic;
        this.diastolic=diastolic;

    }


    public int getSystolic() {
        return systolic;
    }

    public void setSystolic(int systolic) {
        this.systolic = systolic;
    }

    public int getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(int diastolic) {
        this.diastolic = diastolic;
    }


}

