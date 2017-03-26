package com.nus.iss.android.medipal.dto;

import java.util.Date;

/**
 * Created by Gautam on 24/03/17.
 */

public class ScheduledItem {
    private int id;
    private String medicine;
    private int consumeQuantity;
    private int dosage;
    private Date medicineTime;

    private String location;
    private Date appointmentTime;
    private String description;

    private boolean isAppointment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ScheduledItem(int id, String medicine, int consumeQuantity, int dosage, Date medicineTime, boolean isAppointment) {
        this.medicine = medicine;
        this.consumeQuantity = consumeQuantity;
        this.dosage = dosage;
        this.medicineTime = medicineTime;
        this.isAppointment = isAppointment;
        this.id = id;
    }

    public ScheduledItem(String location, Date appointmentTime, String description, boolean isAppointment) {
        this.location = location;
        this.appointmentTime = appointmentTime;
        this.description = description;
        this.isAppointment = isAppointment;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public int getConsumeQuantity() {
        return consumeQuantity;
    }

    public void setConsumeQuantity(int consumeQuantity) {
        this.consumeQuantity = consumeQuantity;
    }

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public Date getMedicineTime() {
        return medicineTime;
    }

    public void setMedicineTime(Date medicineTime) {
        this.medicineTime = medicineTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAppointment() {
        return isAppointment;
    }

    public void setAppointment(boolean appointment) {
        isAppointment = appointment;
    }
}
