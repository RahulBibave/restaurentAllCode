package com.resmenu.POJO;

public class Bill {
    int ItemId,Quantity;
    String ItemName;
    Double Price,Amount,Disscount;

    public Bill(int itemId, int quantity, String itemName, Double price, Double amount, Double disscount) {
        ItemId = itemId;
        Quantity = quantity;
        ItemName = itemName;
        Price = price;
        Amount = amount;
        Disscount = disscount;
    }

    public int getItemId() {
        return ItemId;
    }

    public void setItemId(int itemId) {
        ItemId = itemId;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public Double getAmount() {
        return Amount;
    }

    public void setAmount(Double amount) {
        Amount = amount;
    }

    public Double getDisscount() {
        return Disscount;
    }

    public void setDisscount(Double disscount) {
        Disscount = disscount;
    }
}
