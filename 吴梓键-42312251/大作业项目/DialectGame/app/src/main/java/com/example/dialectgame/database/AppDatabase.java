package com.example.dialectgame.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import com.example.dialectgame.dao.CardDao;
import com.example.dialectgame.model.CollectedCard;

@Database(entities = {CollectedCard.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract CardDao cardDao();

    // 单例模式获取数据库实例
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
                            .fallbackToDestructiveMigration()// 简化操作（正式项目建议用异步）
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}