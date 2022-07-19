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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setIcon(int icon) {
        this.icon = icon;
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