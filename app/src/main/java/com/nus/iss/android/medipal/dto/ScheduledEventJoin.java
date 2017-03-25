package com.nus.iss.android.medipal.dto;

import java.util.Date;

/**
 * Created by Gautam on 24/03/17.
 */

public class ScheduledEventJoin {
    private String medicine;
    private int consumeQuantity;
    private int dosage;
    private Reminder reminder;

    private String location;
    private Date appointmentTime;
    private String description;

    private boolean isAppointment;

    public ScheduledEventJoin(String medicine, int consumeQuantity, int dosage, Reminder reminder, boolean isAppointment) {
        this.medicine = medicine;
        this.consumeQuantity = consumeQuantity;
        this.dosage = dosage;
        this.reminder = reminder;
        this.isAppointment=isAppointment;
    }

    public ScheduledEventJoin(String location, Date appointmentTime, String description, boolean isAppointment  ) {

        this.location = location;
        this.appointmentTime = appointmentTime;
        this.description = description;
        this.isAppointment=isAppointment;
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

    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
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

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public boolean isAppointment() {
        return isAppointment;
    }

    public void setAppointment(boolean appointment) {
        isAppointment = appointment;
    }
    /*    public TodaysSchedule(String medicineName,int medicineConsumeQty, int medicineDosage,
                    int reminderId, int reminderFrequency, Date reminderStartTime, int reminderInterval){
        this.medicine = medicineName;
        this.consumeQuantity = medicineConsumeQty;
        this.dosage = medicineDosage;

        this.reminder = new Reminder(reminderFrequency,reminderStartTime,reminderInterval);
        this.reminder.setReminderId(reminderId);
    }*/


}
