package com.example.dialectgame.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.dialectgame.model.Like;

@Dao
public interface LikeDao {
    @Insert
    void likePost(Like like);

    @Delete
    void unlikePost(Like like);

    @Query("SELECT EXISTS(SELECT * FROM likes WHERE userId = :userId AND postId = :postId)")
    boolean hasLiked(int userId, int postId);

    @Query("SELECT COUNT(*) FROM likes WHERE postId = :postId")
    int getLikeCount(int postId);
}