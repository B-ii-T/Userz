package com.bit.userz;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AccountDao {
    @Insert
    void insert (Account account);
    @Update
    void update (Account account);
    @Delete
    void delete (Account account);
    @Query("DELETE FROM accounts_table")
    void deleteAll();
    @Query("SELECT * FROM accounts_table")
    LiveData<List<Account>> getAllAccounts();
}
