package com.nus.iss.android.medipal.dto;

import java.util.Date;

/**
 * Created by siddharth on 3/11/2017.
 */

public class Measurement {
    private int systolic;
    private int diastolic;
    private int pulse;
    private float temperature;
    private int weight;
    private Date measuredOn;

    public Measurement(int systolic, int diastolic, int pulse, float temperature, int weight,Date measuredOn) {
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.pulse = pulse;
        this.temperature = temperature;
        this.weight = weight;
        this.measuredOn=measuredOn;
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

    public int getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Date getMeasuredOn() {
        return measuredOn;
    }

    public void setMeasuredOn(Date measuredOn) {
        this.measuredOn = measuredOn;
    }
}
