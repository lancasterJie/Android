// UserDao.java
package com.example.dialectgame.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.dialectgame.model.User;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    User login(String username, String password);

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    User findByUsername(String username);

    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    User findById(int userId);

    @Query("UPDATE users SET nickname = :nickname, avatar = :avatar WHERE id = :userId")
    void updateUserInfo(int userId, String nickname, String avatar);
}