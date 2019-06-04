package com.resmenu.POJO;

public class GetOrder {
    int ItemId,CategoryId,TableId;
    int Quantity;
    String ItemName,CategoryName,WaiterId,tableName;

    public GetOrder(int itemId, int categoryId, int tableId, int quantity, String itemName, String categoryName, String waiterId, String tableName) {
        ItemId = itemId;
        CategoryId = categoryId;
        TableId = tableId;
        Quantity = quantity;
        ItemName = itemName;
        CategoryName = categoryName;
        WaiterId = waiterId;
        this.tableName = tableName;
    }

    public int getItemId() {
        return ItemId;
    }

    public void setItemId(int itemId) {
        ItemId = itemId;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public int getTableId() {
        return TableId;
    }

    public void setTableId(int tableId) {
        TableId = tableId;
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

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getWaiterId() {
        return WaiterId;
    }

    public void setWaiterId(String waiterId) {
        WaiterId = waiterId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}