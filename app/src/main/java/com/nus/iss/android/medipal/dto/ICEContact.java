package com.nus.iss.android.medipal.dto;

/**
 * Created by siddharth on 3/11/2017.
 */

public class ICEContact {

    private String name;
    private String contactNo;
    private String contactType;
    private String description;
    private String sequence;

    public ICEContact(String name, String contactNo, String contactType, String description, String sequence) {
        this.name = name;
        this.contactNo = contactNo;
        this.contactType = contactType;
        this.description = description;
        this.sequence = sequence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
}
