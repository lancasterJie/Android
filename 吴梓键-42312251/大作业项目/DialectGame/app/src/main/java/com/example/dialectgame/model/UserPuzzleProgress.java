package com.example.dialectgame.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_puzzle_progress")
public class UserPuzzleProgress {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId; // 关联用户
    private String puzzleId; // 关联谜题ID
    private boolean isTranslationCompleted; // 翻译模块是否完成
    private boolean isVoiceCompleted; // 语音模块是否完成

    public UserPuzzleProgress(int userId, String puzzleId) {
        this.userId = userId;
        this.puzzleId = puzzleId;
        this.isTranslationCompleted = false;
        this.isVoiceCompleted = false;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getPuzzleId() { return puzzleId; }
    public void setPuzzleId(String puzzleId) { this.puzzleId = puzzleId; }
    public boolean isTranslationCompleted() { return isTranslationCompleted; }
    public void setTranslationCompleted(boolean translationCompleted) { isTranslationCompleted = translationCompleted; }
    public boolean isVoiceCompleted() { return isVoiceCompleted; }
    public void setVoiceCompleted(boolean voiceCompleted) { isVoiceCompleted = voiceCompleted; }
}