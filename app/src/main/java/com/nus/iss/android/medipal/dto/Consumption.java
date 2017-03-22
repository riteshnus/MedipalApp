package com.nus.iss.android.medipal.dto;

import java.util.Date;

/**
 * Created by siddharth on 3/11/2017.
 */

public class Consumption {

    private Medicine medicine;
    private int quantity;
    private Date consumedOn;

    public Consumption(Medicine medicine, int quantity, Date consumedOn) {
        this.medicine = medicine;
        this.quantity = quantity;
        this.consumedOn = consumedOn;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getConsumedOn() {
        return consumedOn;
    }

    public void setConsumedOn(Date consumedOn) {
        this.consumedOn = consumedOn;
    }
}
