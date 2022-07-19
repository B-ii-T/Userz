package com.bit.userz;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AccountsRepo {
    private AccountDao accountDao;
    private LiveData<List<Account>> allAccounts;
    //constructor
    public AccountsRepo(Application application){
        AccountsDatabase database = AccountsDatabase.getInstance(application);
        accountDao = database.useDao();
        allAccounts = accountDao.getAllAccounts();
    }
    //API operations
    public void insert(Account account){
        new InsertTask(accountDao).execute(account);
    }
    public void update(Account account){
        new UpdateTask(accountDao).execute(account);
    }
    public void delete(Account account){
        new DeleteTask(accountDao).execute(account);
    }
    public void deleteAll(Account account){
        new DeleteAllTask(accountDao).execute();
    }
    public LiveData<List<Account>> getAllAccounts() {
        return allAccounts;
    }
    //Async Tasks
    private static class InsertTask extends AsyncTask<Account, Void, Void>{
        private AccountDao accountDao;
        private InsertTask(AccountDao accountDao){this.accountDao = accountDao;}
        @Override
        protected Void doInBackground(Account... accounts) {
            accountDao.insert(accounts[0]);
            return null;
        }
    }
    private static class UpdateTask extends AsyncTask<Account, Void, Void>{
        private AccountDao accountDao;
        private UpdateTask(AccountDao accountDao){this.accountDao = accountDao;}
        @Override
        protected Void doInBackground(Account... accounts) {
            accountDao.update(accounts[0]);
            return null;
        }
    }
    private static class DeleteTask extends AsyncTask<Account, Void, Void>{
        private AccountDao accountDao;
        private DeleteTask(AccountDao accountDao){this.accountDao = accountDao;}
        @Override
        protected Void doInBackground(Account... accounts) {
            accountDao.delete(accounts[0]);
            return null;
        }
    }
    private static class DeleteAllTask extends AsyncTask<Void, Void, Void>{
        private AccountDao accountDao;
        private DeleteAllTask(AccountDao accountDao){this.accountDao = accountDao;}
        @Override
        protected Void doInBackground(Void... voids) {
            accountDao.deleteAll();
            return null;
        }
    }
}
