package com.nus.iss.android.medipal.dto;

/**
 * Created by siddharth on 3/11/2017.
 */

public class Categories {

    private int categoryId;
    private String category;
    private String  code;
    private String description;
    private boolean remind;

    public Categories(int categoryId ,String category, String code, String description, boolean remind) {
        this.category = category;
        this.code = code;
        this.description = description;
        this.remind = remind;
        this.categoryId=categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRemind() {
        return remind;
    }

    public void setRemind(boolean remind) {
        this.remind = remind;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
