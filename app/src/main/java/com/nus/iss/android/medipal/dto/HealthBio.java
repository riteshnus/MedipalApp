package com.nus.iss.android.medipal.dto;

import java.util.Date;

/**
 * Created by siddharth on 3/11/2017.
 */

public class HealthBio {
    private String condition;
    private int id;
    private Date startDate;
    private String conditionType;

    public HealthBio(String condition, Date startDate, String conditionType) {
        this.condition = condition;
        this.startDate = startDate;
        this.conditionType = conditionType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }
}
