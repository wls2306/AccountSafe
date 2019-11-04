package com.bcu.accountsafe.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bcu.accountsafe.model.Info;

import java.util.List;

@Dao
public interface InfoDao {

    @Insert
    void insert(Info info);

    @Query("select * from info")
    LiveData<List<Info>> getAllInfoLive();

    @Query("select * from info where id=:id")
    Info getInfoById(int id);

    @Query("select * from info where category=:category")
    List<Info> getByCategory(String category);

    @Query("select * from info where title like '%'||:title||'%'")
    List<Info> getInfoByTitle(String title);

    @Query("select * from info where username=:username")
    List<Info> getInfoByUserName(String username);

    @Update
    void updateInfo(Info info);

    @Query("delete from info where id=:id")
    void deleteById(int id);




}
