package com.resmenu.Database.DaoAccess;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.resmenu.Database.Entity.MyCart;

import java.util.List;

@Dao
public interface MyCartDao {

    @Query("SELECT * FROM mycart")
    List<MyCart> getAll();

    @Insert
    void insert(MyCart myCart);

    @Delete
    void delete(MyCart myCart);

    @Query("DELETE FROM MyCart WHERE id = :id")
    void deleteByUserId(int id);

    @Update
    void update(MyCart myCart);

    @Query("UPDATE MyCart SET itemQuantity = :quantity WHERE id =:id")
    void updateById(int id, int quantity);

    @Query("SELECT SUM(itemPrice * itemQuantity) FROM MyCart WHERE tableNo = :tableNo")
    double getTotal(int tableNo);

    @Query("DELETE FROM MyCart")
    public void deleteAll();
}