package com.nus.iss.android.medipal.dto;

import java.util.Date;

/**
 * Created by siddharth on 3/11/2017.
 */

public class Appointment {

    private String location;
    private Date appointmentTime;
    private String description;

    public Appointment(String description, String location, Date appointmentTime) {
        this.description = description;
        this.location = location;
        this.appointmentTime = appointmentTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getAppontmentTime() {
        return appointmentTime;
    }

    public void setAppontmentTime(Date appontmentTime) {
        this.appointmentTime = appontmentTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
