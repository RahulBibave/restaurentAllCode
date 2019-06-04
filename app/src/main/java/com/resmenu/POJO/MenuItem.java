package com.resmenu.POJO;

import java.io.Serializable;

public class MenuItem implements Serializable {

    int ItemId;
    String ItemName;
    String ItemDescription;
    Double Price, Disscount;
    Boolean IsActive;
    Double Quantity;
    String Img;

    public MenuItem(int itemId, String itemName, String itemDescription, Double price, Double disscount, Boolean isActive, Double quantity, String img) {
        ItemId = itemId;
        ItemName = itemName;
        ItemDescription = itemDescription;
        Price = price;
        Disscount = disscount;
        IsActive = isActive;
        Quantity = quantity;
        Img = img;
    }

    public int getItemId() {
        return ItemId;
    }

    public void setItemId(int itemId) {
        ItemId = itemId;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemDescription() {
        return ItemDescription;
    }

    public void setItemDescription(String itemDescription) {
        ItemDescription = itemDescription;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public Double getDisscount() {
        return Disscount;
    }

    public void setDisscount(Double disscount) {
        Disscount = disscount;
    }

    public Boolean getActive() {
        return IsActive;
    }

    public void setActive(Boolean active) {
        IsActive = active;
    }

    public Double getQuantity() {
        return Quantity;
    }

    public void setQuantity(Double quantity) {
        Quantity = quantity;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }
}