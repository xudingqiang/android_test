package com.fde.test.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fde.test.bean.WifiHistory;

import java.util.List;

@Dao
public interface WifiDao {
    @Query("select *from wifi_history")
    List<WifiHistory> getAllWifiList();

    @Insert
    void insert(WifiHistory wifiHistory);

    @Update
    void update(WifiHistory wifiHistory);


    @Delete
    void delete(WifiHistory wifiHistory);

}
