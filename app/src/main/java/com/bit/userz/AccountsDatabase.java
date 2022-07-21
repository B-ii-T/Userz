package com.bit.userz;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

@Database(entities = Account.class, version = 1)
public abstract class AccountsDatabase extends RoomDatabase {
    private static AccountsDatabase instance;
    public abstract AccountDao useDao();
    public static synchronized AccountsDatabase getInstance(Context c){
        if(instance == null){
            instance = Room.databaseBuilder(c.getApplicationContext(), AccountsDatabase.class, "accounts_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback).build();
        }
        return instance;
    }
    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateTask(instance).execute();
        }
    };
    private static class PopulateTask extends AsyncTask<Void, Void, Void> {
        private AccountDao accountDao;
        private PopulateTask(AccountsDatabase db){accountDao = db.useDao();}
        @Override
        protected Void doInBackground(Void... voids) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            accountDao.insert(new Account("BiBo Moh", "bibo@gmail.com", "0000", "social media", "Messenger", R.drawable.messenger, sdf.format(new Date())));
            accountDao.insert(new Account("Fatah BNB", "fatah@gmail.com", "0000", "social media", "Messenger", R.drawable.messenger,  sdf.format(new Date())));
            return null;
        }
    }
}
