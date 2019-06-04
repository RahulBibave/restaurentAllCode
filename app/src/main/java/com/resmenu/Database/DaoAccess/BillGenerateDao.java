package com.resmenu.Database.DaoAccess;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.resmenu.Database.Entity.BillGenarateTable;

import java.util.List;

@Dao
public interface BillGenerateDao {

    @Query("SELECT * FROM billgenaratetable")
    List<BillGenarateTable> getAll();

    @Insert
    void insert(BillGenarateTable billGenarateTable);

    @Delete
    void delete(BillGenarateTable billGenarateTable);

    @Query("DELETE FROM billgenaratetable WHERE id = :id")
    void deleteByUserId(int id);

    @Update
    void update(BillGenarateTable billGenarateTable);

    @Query("UPDATE billGenarateTable SET itemQuantity = :quantity WHERE id =:id")
    void updateById(int id, int quantity);

}
