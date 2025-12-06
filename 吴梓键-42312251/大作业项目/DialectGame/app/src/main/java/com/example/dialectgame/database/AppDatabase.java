package com.example.dialectgame.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import com.example.dialectgame.dao.CardDao;
import com.example.dialectgame.dao.ForumDao;
import com.example.dialectgame.dao.UserDao;
import com.example.dialectgame.model.CollectedCard;
import com.example.dialectgame.model.Comment;
import com.example.dialectgame.model.ForumPost;
import com.example.dialectgame.model.User;

// 更新AppDatabase.java，添加用户表
@Database(entities = {CollectedCard.class, User.class, ForumPost.class, Comment.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract CardDao cardDao();
    public abstract UserDao userDao();
    public abstract ForumDao forumDao();

    // 单例模式获取数据库实例（代码不变）
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