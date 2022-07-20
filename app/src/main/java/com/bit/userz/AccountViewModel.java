package com.bit.userz;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AccountViewModel extends AndroidViewModel {
    private AccountsRepo repository;
    private LiveData<List<Account>> allAccounts;
    public AccountViewModel(@NonNull Application application) {
        super(application);
        repository = new AccountsRepo(application);
        allAccounts = repository.getAllAccounts();
    }
    public void insertAccount(Account account){
        repository.insert(account);
    }
    public void updateAccount(Account account){
        repository.update(account);
    }
    public void deleteAccount(Account account){
        repository.delete(account);
    }
    public void deleteAllAccounts(){
        repository.deleteAll();
    }
    public LiveData<List<Account>> getAllAccounts() {
        return allAccounts;
    }
}
