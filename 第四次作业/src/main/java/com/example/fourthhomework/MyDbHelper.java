package com.example.fourthhomework;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {
    // 数据库名和版本
    private static final String DB_NAME = "myapp.db";
    private static final int DB_VERSION = 1;

    // 表名和字段名常量（便于后续引用，避免拼写错误）
    public static final String TABLE_NAME = "records";
    public static final String COL_ID = "_id";
    public static final String COL_TITLE = "title";
    public static final String COL_CONTENT = "content";
    public static final String COL_TIME = "time";

    // 创建表的 SQL 语句
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COL_TITLE + " TEXT," +
            COL_CONTENT + " TEXT," +
            COL_TIME + " TEXT)";

    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // 数据库首次创建时调用（创建表）
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    // 数据库版本更新时调用（这里直接删除旧表重建，实际项目可写数据迁移逻辑）
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}