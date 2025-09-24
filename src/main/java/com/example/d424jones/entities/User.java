package com.example.d424jones.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NonBlocking;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private  int id;

    @NonNull
    private String username;
    @NonNull
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public User(@NonNull String username, @NonNull String password){
            this.username = username;
            this.password = password;
    }
}
