package com.nus.iss.android.medipal.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by siddharth on 3/11/2017.
 */

public class Medicine implements Serializable {

    private int medicineId;
    private String medicine;
    private String description;
    private Categories category;
    private Reminder reminder;
    private boolean remind;
    private int quantity;
    private int dosage;
    private int consumeQuantity;
    private int threshold;
    private Date dateIssued;
    private int expireFactor;
    private List<Consumption> consumptionList;

    public Medicine() {
    }

    public Medicine(String medicine, String description, boolean remind, int quantity,
                    int dosage, int consumeQuantity, int threshold, Date dateIssued, int expireFactor) {
        this.medicine = medicine;
        this.description = description;
        this.remind = remind;
        this.quantity = quantity;
        this.dosage = dosage;
        this.consumeQuantity = consumeQuantity;
        this.threshold = threshold;
        this.dateIssued = dateIssued;
        this.expireFactor = expireFactor;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public int getConsumeQuantity() {
        return consumeQuantity;
    }

    public void setConsumeQuantity(int consumeQuantity) {
        this.consumeQuantity = consumeQuantity;
    }

    public Date getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(Date dateIssued) {
        this.dateIssued = dateIssued;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public int getExpireFactor() {
        return expireFactor;
    }

    public void setExpireFactor(int expireFactor) {
        this.expireFactor = expireFactor;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isRemind() {
        return remind;
    }

    public void setRemind(boolean remind) {
        this.remind = remind;
    }

    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public List<Consumption> getConsumptionList() {
        return consumptionList;
    }

    public void setConsumptionList(List<Consumption> consumptionList) {
        this.consumptionList = consumptionList;
    }

    public void addConsumption(Consumption consumption){
        if(consumptionList==null){
            consumptionList=new ArrayList<Consumption>();
        }
        consumptionList.add(consumption);
        consumption.setMedicine(this);
    }
}
