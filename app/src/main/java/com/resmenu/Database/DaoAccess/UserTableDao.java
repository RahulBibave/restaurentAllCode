package com.resmenu.Database.DaoAccess;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.resmenu.Database.Entity.UserTable;

import java.util.List;

@Dao
public interface UserTableDao {

    @Query("SELECT * FROM usertable")
    List<UserTable> getAll();

    @Insert
    void insert(UserTable userTable);

    @Update
    void update(UserTable userTable);

    @Query("SELECT * FROM UserTable where tableNo = :tableNo")
    List<UserTable> getDataByTableNo(int tableNo);

    @Query("DELETE FROM UserTable WHERE id = :id")
    void deleteByUserId(int id);

}
