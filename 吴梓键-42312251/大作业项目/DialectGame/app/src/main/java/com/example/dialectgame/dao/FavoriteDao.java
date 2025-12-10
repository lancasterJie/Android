package com.example.dialectgame.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.dialectgame.model.Favorite;
import java.util.List;

@Dao
public interface FavoriteDao {
    @Insert
    void favoritePost(Favorite favorite);

    @Delete
    void unfavoritePost(Favorite favorite);

    @Query("SELECT EXISTS(SELECT * FROM favorites WHERE userId = :userId AND postId = :postId)")
    boolean hasFavorited(int userId, int postId);

    @Query("SELECT COUNT(*) FROM favorites WHERE postId = :postId")
    int getFavoriteCount(int postId);

    @Query("SELECT * FROM favorites WHERE userId = :userId")
    List<Favorite> getUserFavorites(int userId);
}