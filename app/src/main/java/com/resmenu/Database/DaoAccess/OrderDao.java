package com.resmenu.Database.DaoAccess;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.resmenu.Database.Entity.OrderTable;

import java.util.List;

@Dao
public interface OrderDao {

    @Query("SELECT * FROM ordertable")
    List<OrderTable> getAll();

    @Insert
    void insert(OrderTable ordertable);

    @Update
    void update(OrderTable ordertable);

    @Query("SELECT * FROM OrderTable where tableNo = :tableNo")
    List<OrderTable> getDataByTableNo(int tableNo);

    @Query("DELETE FROM OrderTable WHERE tableNo = :tableNo")
    void deleteByTableId(int tableNo);

    @Query("SELECT SUM(itemPrice * itemQuantity) FROM ordertable WHERE tableNo = :tableNo")
    double getTotal(int tableNo);

    @Query("DELETE FROM OrderTable")
    public void deleteAll();

    @Query("SELECT COUNT(*) from ordertable WHERE tableNo = :tableNo")
    int orderCount(int tableNo);
}
