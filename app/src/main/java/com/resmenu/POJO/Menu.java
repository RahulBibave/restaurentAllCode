package com.resmenu.POJO;

import java.io.Serializable;

public class Menu implements Serializable {
    Integer CategoryId;
    String CategoryName,CategoryDescription;
    Boolean Active;

    public Menu(Integer categoryId, String categoryName, String categoryDescription, Boolean active) {
        CategoryId = categoryId;
        CategoryName = categoryName;
        CategoryDescription = categoryDescription;
        Active = active;
    }

    public void setCategoryId(Integer categoryId) {
        CategoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public void setCategoryDescription(String categoryDescription) {
        CategoryDescription = categoryDescription;
    }

    public void setActive(Boolean active) {
        Active = active;
    }

    public Integer getCategoryId() {
        return CategoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public String getCategoryDescription() {
        return CategoryDescription;
    }

    public Boolean getActive() {
        return Active;
    }
}
