// UserManager.java
package com.example.dialectgame.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.dialectgame.database.AppDatabase;
import com.example.dialectgame.model.User;

public class UserManager {
    private static final String PREF_NAME = "user_prefs";
    private static final String KEY_CURRENT_USER_ID = "current_user_id";
    private static UserManager instance;
    private AppDatabase db;
    private SharedPreferences prefs;
    private User currentUser;

    private UserManager(Context context) {
        db = AppDatabase.getInstance(context);
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        loadCurrentUser();
    }

    public static synchronized UserManager getInstance(Context context) {
        if (instance == null) {
            instance = new UserManager(context.getApplicationContext());
        }
        return instance;
    }

    private void loadCurrentUser() {
        int userId = prefs.getInt(KEY_CURRENT_USER_ID, -1);
        if (userId != -1) {
            currentUser = db.userDao().findById(userId);
        }
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean login(String username, String password) {
        User user = db.userDao().login(username, password);
        if (user != null) {
            currentUser = user;
            prefs.edit().putInt(KEY_CURRENT_USER_ID, user.getId()).apply();
            return true;
        }
        return false;
    }

    public boolean register(String username, String password, String nickname) {
        if (db.userDao().findByUsername(username) != null) {
            return false; // 用户名已存在
        }
        db.userDao().insertUser(new User(username, password, nickname));
        return true;
    }

    public void logout() {
        currentUser = null;
        prefs.edit().remove(KEY_CURRENT_USER_ID).apply();
    }

    public boolean updateUserInfo(String nickname, String avatar) {
        if (currentUser == null) return false;

        currentUser.setNickname(nickname);
        currentUser.setAvatar(avatar);
        db.userDao().updateUserInfo(currentUser.getId(), nickname, avatar);
        return true;
    }
}