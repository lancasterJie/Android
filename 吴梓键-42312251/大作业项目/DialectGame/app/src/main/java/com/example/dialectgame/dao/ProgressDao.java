package com.example.dialectgame.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.dialectgame.model.UserPuzzleProgress;

@Dao
public interface ProgressDao {
    @Insert
    void insertProgress(UserPuzzleProgress progress);

    @Query("SELECT * FROM user_puzzle_progress WHERE userId = :userId AND puzzleId = :puzzleId LIMIT 1")
    UserPuzzleProgress getProgress(int userId, String puzzleId);

    @Update
    void updateProgress(UserPuzzleProgress progress);

    @Query("DELETE FROM user_puzzle_progress WHERE userId = :userId")
    void clearUserProgress(int userId);
}