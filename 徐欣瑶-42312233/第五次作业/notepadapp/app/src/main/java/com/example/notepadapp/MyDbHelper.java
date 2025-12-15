package com.example.notepadapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "myapp.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_RECORDS = "records";

    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_RECORDS + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "content TEXT," +
                "time TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORDS);
        onCreate(db);
    }

    public long addRecord(String title, String content, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", content);
        values.put("time", time);
        long id = db.insert(TABLE_RECORDS, null, values);
        db.close();
        return id;
    }

    public List<Map<String, String>> getAllRecords() {
        List<Map<String, String>> records = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECORDS,
                new String[]{"_id", "title", "content", "time"},
                null, null, null, null, "time DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Map<String, String> record = new HashMap<>();
                record.put("id", cursor.getString(0));
                record.put("title", cursor.getString(1));
                record.put("content", cursor.getString(2));
                record.put("time", cursor.getString(3));
                records.add(record);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return records;
    }

    public void deleteRecord(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECORDS, "_id = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}