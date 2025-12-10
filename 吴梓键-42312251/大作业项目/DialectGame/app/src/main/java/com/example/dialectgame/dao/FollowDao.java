package com.example.dialectgame.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.dialectgame.model.Follow;
import java.util.List;

@Dao
public interface FollowDao {
    @Insert
    void follow(Follow follow);

    @Delete
    void unfollow(Follow follow);

    @Query("SELECT COUNT(*) FROM follows WHERE userId = :userId")
    int getFollowingCount(int userId);

    @Query("SELECT COUNT(*) FROM follows WHERE followedUserId = :userId")
    int getFollowerCount(int userId);

    @Query("SELECT * FROM follows WHERE userId = :userId")
    List<Follow> getFollowing(int userId);

    @Query("SELECT * FROM follows WHERE followedUserId = :userId")
    List<Follow> getFollowers(int userId);

    @Query("SELECT EXISTS(SELECT * FROM follows WHERE userId = :userId AND followedUserId = :followedUserId)")
    boolean isFollowing(int userId, int followedUserId);
}