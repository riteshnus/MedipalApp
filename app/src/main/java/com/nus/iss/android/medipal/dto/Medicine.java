package com.nus.iss.android.medipal.dto;

import java.util.Date;

/**
 * Created by siddharth on 3/11/2017.
 */

public class Medicine {

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

    public Medicine(String medicine, String description, Categories category,
                    Reminder reminder, boolean remind, int quantity,
                    int dosage, int consumeQuantity, int threshold, Date dateIssued, int expireFactor) {
        this.medicine = medicine;
        this.description = description;
        this.category = category;
        this.reminder = reminder;
        this.remind = remind;
        this.quantity = quantity;
        this.dosage = dosage;
        this.consumeQuantity = consumeQuantity;
        this.threshold = threshold;
        this.dateIssued = dateIssued;
        this.expireFactor = expireFactor;
    }
}
