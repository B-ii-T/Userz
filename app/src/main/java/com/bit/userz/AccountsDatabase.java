package com.bit.userz;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Account.class, version = 1)
public abstract class AccountsDatabase extends RoomDatabase {
    private static AccountsDatabase instance;
    public abstract AccountDao useDao();
    public static synchronized AccountsDatabase getInstance(Context c){
        if(instance == null){
            instance = Room.databaseBuilder(c.getApplicationContext(), AccountsDatabase.class, "accounts_db")
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
