package com.bit.userz;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "accounts_table")
public class Account {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String email;
    private String password;
    private String category;
    private String platform;
    private int icon;

    //setters
    public void setId(int id) {
        this.id = id;
    }

    //getters
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCategory() {
        return category;
    }

    public String getPlatform() {
        return platform;
    }

    public int getIcon() {
        return icon;
    }
}