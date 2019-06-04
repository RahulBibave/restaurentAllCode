package com.resmenu.POJO;

public class Table {
    int TableId;
    String TableName,TableDescription;
    Boolean IsActive,IsBusy;

    public Table(int tableId, String tableName, String tableDescription, Boolean isActive, Boolean isBusy) {
        TableId = tableId;
        TableName = tableName;
        TableDescription = tableDescription;
        IsActive = isActive;
        IsBusy = isBusy;
    }

    public int getTableId() {
        return TableId;
    }

    public void setTableId(int tableId) {
        TableId = tableId;
    }

    public String getTableName() {
        return TableName;
    }

    public void setTableName(String tableName) {
        TableName = tableName;
    }

    public String getTableDescription() {
        return TableDescription;
    }

    public void setTableDescription(String tableDescription) {
        TableDescription = tableDescription;
    }

    public Boolean getActive() {
        return IsActive;
    }

    public void setActive(Boolean active) {
        IsActive = active;
    }

    public Boolean getBusy() {
        return IsBusy;
    }

    public void setBusy(Boolean busy) {
        IsBusy = busy;
    }
}
