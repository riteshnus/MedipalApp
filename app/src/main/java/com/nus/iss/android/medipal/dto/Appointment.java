package com.nus.iss.android.medipal.dto;

import java.util.Date;

/**
 * Created by siddharth on 3/11/2017.
 */

public class Appointment {

    private String location;
    private Date appontmentTime;
    private String description;

    public Appointment(String description, String location, Date appontmentTime) {
        this.description = description;
        this.location = location;
        this.appontmentTime = appontmentTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getAppontmentTime() {
        return appontmentTime;
    }

    public void setAppontmentTime(Date appontmentTime) {
        this.appontmentTime = appontmentTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
