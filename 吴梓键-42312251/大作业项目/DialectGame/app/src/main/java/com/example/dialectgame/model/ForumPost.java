// ForumPost.java
package com.example.dialectgame.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "forum_posts")
public class ForumPost {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId;
    private String title;
    private String content;
    private long createTime;
    private int likeCount;

    // 构造函数、getter和setter
    public ForumPost(int userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.createTime = System.currentTimeMillis();
        this.likeCount = 0;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public long getCreateTime() { return createTime; }
    public void setCreateTime(long createTime) { this.createTime = createTime; }
    public int getLikeCount() { return likeCount; }
    public void setLikeCount(int likeCount) { this.likeCount = likeCount; }
}