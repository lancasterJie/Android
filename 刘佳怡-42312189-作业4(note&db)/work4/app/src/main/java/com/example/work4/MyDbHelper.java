// MyDbHelper.java (在你原有基础上补全 insert)
package com.example.work4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "myapp.db";
    private static final int DB_VERSION = 1;

    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE records (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "content TEXT," +
                "time TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS records");
        onCreate(db);
    }

    // ✅ 补全 insert 方法（你原文件缺失）
    public long insert(Record record) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("title", record.title);
        cv.put("content", record.content);
        cv.put("time", record.time);
        return db.insert("records", null, cv);
    }

    public List<Record> getAllRecords() {
        List<Record> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        try (Cursor c = db.query("records", null, null, null, null, null, "_id DESC")) {
            while (c.moveToNext()) {
                int id = c.getInt(c.getColumnIndexOrThrow("_id"));
                String title = c.getString(c.getColumnIndexOrThrow("title"));
                String content = c.getString(c.getColumnIndexOrThrow("content"));
                String time = c.getString(c.getColumnIndexOrThrow("time"));
                list.add(new Record(id, title, content, time));
            }
        }
        return list;
    }

    public void delete(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("records", "_id=?", new String[]{String.valueOf(id)});
    }

    public void update(int id, String newContent) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        int end = Math.min(newContent.indexOf('\n'), 10);
        if (end == -1) end = Math.min(newContent.length(), 10);
        String newTitle = newContent.substring(0, end);
        String newTime = java.text.DateFormat.getDateTimeInstance().format(new java.util.Date());
        cv.put("content", newContent);
        cv.put("title", newTitle);
        cv.put("time", newTime);
        db.update("records", cv, "_id=?", new String[]{String.valueOf(id)});
    }
}