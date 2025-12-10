package com.example.dialectgame.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites", primaryKeys = {"userId", "postId"})
public class Favorite {
    private int userId;         // 用户ID
    private int postId;         // 帖子ID
    private long favoriteTime;  // 收藏时间

    public Favorite(int userId, int postId) {
        this.userId = userId;
        this.postId = postId;
        this.favoriteTime = System.currentTimeMillis();
    }

    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getPostId() { return postId; }
    public void setPostId(int postId) { this.postId = postId; }
    public long getFavoriteTime() { return favoriteTime; }
    public void setFavoriteTime(long favoriteTime) { this.favoriteTime = favoriteTime; }
}