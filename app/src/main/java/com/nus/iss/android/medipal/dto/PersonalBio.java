package com.nus.iss.android.medipal.dto;

import java.util.Date;

/**
 * Created by siddharth on 3/11/2017.
 */

public class PersonalBio {

    private String mName;
    private Date mDOB;
    private String mIDNo;
    private String mAddress;
    private String mPostalCode;
    private String mHeight;
    private String mBloodType;

    public PersonalBio(String mName, Date mDOB, String mIDNo, String mAddress, String mPostalCode, String mHeight, String mBloodType) {
        this.mName = mName;
        this.mDOB = mDOB;
        this.mIDNo = mIDNo;
        this.mAddress = mAddress;
        this.mPostalCode = mPostalCode;
        this.mHeight = mHeight;
        this.mBloodType = mBloodType;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmBloodType() {
        return mBloodType;
    }

    public void setmBloodType(String mBloodType) {
        this.mBloodType = mBloodType;
    }

    public Date getmDOB() {
        return mDOB;
    }

    public void setmDOB(Date mDOB) {
        this.mDOB = mDOB;
    }

    public String getmHeight() {
        return mHeight;
    }

    public void setmHeight(String mHeight) {
        this.mHeight = mHeight;
    }

    public String getmIDNo() {
        return mIDNo;
    }

    public void setmIDNo(String mIDNo) {
        this.mIDNo = mIDNo;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPostalCode() {
        return mPostalCode;
    }

    public void setmPostalCode(String mPostalCode) {
        this.mPostalCode = mPostalCode;
    }
}
