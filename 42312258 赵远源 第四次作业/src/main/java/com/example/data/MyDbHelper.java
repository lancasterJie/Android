package com.example.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 统一管理数据库建表、升级以及简单的插入操作。
 */
public class MyDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "myapp.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "records";

    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "content TEXT," +
                "time TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * 将一条记录写入数据库，返回 rowId，失败返回 -1。
     */
    public long insertRecord(String title, String content, String time) {
        // 封装 ContentValues 的重复代码
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", content);
        values.put("time", time);
        return db.insert(TABLE_NAME, null, values);
    }
}

