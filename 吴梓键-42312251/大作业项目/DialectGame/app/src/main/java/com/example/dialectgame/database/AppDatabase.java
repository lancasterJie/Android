package com.example.dialectgame.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import com.example.dialectgame.dao.CardDao;
import com.example.dialectgame.dao.FavoriteDao;
import com.example.dialectgame.dao.FollowDao;
import com.example.dialectgame.dao.ForumDao;
import com.example.dialectgame.dao.LikeDao;
import com.example.dialectgame.dao.ProgressDao;
import com.example.dialectgame.dao.UserDao;
import com.example.dialectgame.model.CollectedCard;
import com.example.dialectgame.model.Comment;
import com.example.dialectgame.model.Favorite;
import com.example.dialectgame.model.Follow;
import com.example.dialectgame.model.ForumPost;
import com.example.dialectgame.model.Like;
import com.example.dialectgame.model.User;
import com.example.dialectgame.model.UserPuzzleProgress;

@Database(entities = {CollectedCard.class, User.class, ForumPost.class, Comment.class,
        UserPuzzleProgress.class, Follow.class, Like.class, Favorite.class},
        version = 8, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract CardDao cardDao();
    public abstract UserDao userDao();
    public abstract ForumDao forumDao();
    public abstract ProgressDao progressDao();
    public abstract FollowDao followDao();
    public abstract LikeDao likeDao();
    public abstract FavoriteDao favoriteDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "dialect_game_db"
                            )
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}