package com.resmenu.Database.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class UserTable implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "tableNo")
    private int tableNo;

    @Ignore
    @ColumnInfo(name = "tableStatus")
    private Boolean tableStatus;

    @Ignore
    @ColumnInfo(name = "userName")
    private String userName;

    @Ignore
    @ColumnInfo(name = "mobileNo")
    private String mobileNo;

    @Ignore
    @ColumnInfo(name = "userEmail")
    private String userEmail;

    @Ignore
    @ColumnInfo(name = "waiterId")
    private int waiterId;

    @Ignore
    @ColumnInfo(name = "itemName")
    private String menuName;

    @Ignore
    @ColumnInfo(name = "itemId")
    private String itemId;

    @Ignore
    @ColumnInfo(name = "itemPrice")
    private double menuPrice;

    @Ignore
    @ColumnInfo(name = "itemQuantity")
    private int itemQuantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTableNo() {
        return tableNo;
    }

    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }

    public Boolean getTableStatus() {
        return tableStatus;
    }

    public void setTableStatus(Boolean tableStatus) {
        this.tableStatus = tableStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getWaiterId() {
        return waiterId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setWaiterId(int waiterId) {
        this.waiterId = waiterId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public double getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(double menuPrice) {
        this.menuPrice = menuPrice;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}