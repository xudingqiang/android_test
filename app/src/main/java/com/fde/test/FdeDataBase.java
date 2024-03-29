package com.fde.test;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.fde.test.bean.WifiHistory;
import com.fde.test.dao.WifiDao;

@Database(entities = {WifiHistory.class}, version = 1 ,exportSchema = false)
public abstract  class FdeDataBase extends RoomDatabase {
    public abstract WifiDao mWifiDao();

    private static FdeDataBase instance;

    public static synchronized FdeDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context,
                            FdeDataBase.class, "fde_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
