// User.java
package com.example.dialectgame.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private long registerTime;
    // 新增字段
    private String gender;  // "male" 或 "female"
    private String hometown;
    private String email;

    // 构造函数、getter和setter
    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.registerTime = System.currentTimeMillis();
        this.avatar = "default_avatar";
        this.gender = "";
        this.hometown = "";
        this.email = "";
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public long getRegisterTime() { return registerTime; }
    public void setRegisterTime(long registerTime) { this.registerTime = registerTime; }

    // 新增字段的getter和setter
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getHometown() { return hometown; }
    public void setHometown(String hometown) { this.hometown = hometown; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}