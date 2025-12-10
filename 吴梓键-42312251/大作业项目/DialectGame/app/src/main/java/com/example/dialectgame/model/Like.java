package com.example.dialectgame.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "likes", primaryKeys = {"userId", "postId"})
public class Like {
    private int userId;     // 用户ID
    private int postId;     // 帖子ID
    private long likeTime;  // 点赞时间

    public Like(int userId, int postId) {
        this.userId = userId;
        this.postId = postId;
        this.likeTime = System.currentTimeMillis();
    }

    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getPostId() { return postId; }
    public void setPostId(int postId) { this.postId = postId; }
    public long getLikeTime() { return likeTime; }
    public void setLikeTime(long likeTime) { this.likeTime = likeTime; }
}