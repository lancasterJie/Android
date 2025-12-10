package com.example.dialectgame.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "follows", primaryKeys = {"userId", "followedUserId"})
public class Follow {
    private int userId;         // 关注者ID
    private int followedUserId; // 被关注者ID
    private long followTime;    // 关注时间

    public Follow(int userId, int followedUserId) {
        this.userId = userId;
        this.followedUserId = followedUserId;
        this.followTime = System.currentTimeMillis();
    }

    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getFollowedUserId() { return followedUserId; }
    public void setFollowedUserId(int followedUserId) { this.followedUserId = followedUserId; }
    public long getFollowTime() { return followTime; }
    public void setFollowTime(long followTime) { this.followTime = followTime; }
}
