package com.bcu.accountsafe;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.bcu.accountsafe.dao.InfoDao;
import com.bcu.accountsafe.model.Info;

@Database(entities = Info.class,version = 1,exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    private static MyDatabase INSTANCE;

    public static synchronized MyDatabase getDatabase(Context c){
        if (INSTANCE==null) {
            INSTANCE = Room.databaseBuilder(c.getApplicationContext(), MyDatabase.class, "account_safe")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public abstract InfoDao getInfoDao();

}
