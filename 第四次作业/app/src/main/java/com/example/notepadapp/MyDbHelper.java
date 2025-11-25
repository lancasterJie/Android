package com.example.notepadapp;

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
    private static final String TABLE_NAME = "records";
    
    private static final String COL_ID = "_id";
    private static final String COL_TITLE = "title";
    private static final String COL_CONTENT = "content";
    private static final String COL_TIME = "time";

    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_TITLE + " TEXT," +
                COL_CONTENT + " TEXT," +
                COL_TIME + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * 插入一条记录
     */
    public long insertRecord(String title, String content, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TITLE, title);
        values.put(COL_CONTENT, content);
        values.put(COL_TIME, time);
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }

    /**
     * 获取所有记录
     */
    public List<Record> getAllRecords() {
        List<Record> records = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, COL_TIME + " DESC");
        
        if (cursor.moveToFirst()) {
            do {
                Record record = new Record();
                record.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                record.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COL_TITLE)));
                record.setContent(cursor.getString(cursor.getColumnIndexOrThrow(COL_CONTENT)));
                record.setTime(cursor.getString(cursor.getColumnIndexOrThrow(COL_TIME)));
                records.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return records;
    }

    /**
     * 根据ID获取记录
     */
    public Record getRecordById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COL_ID + "=?", 
                new String[]{String.valueOf(id)}, null, null, null);
        
        Record record = null;
        if (cursor.moveToFirst()) {
            record = new Record();
            record.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
            record.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COL_TITLE)));
            record.setContent(cursor.getString(cursor.getColumnIndexOrThrow(COL_CONTENT)));
            record.setTime(cursor.getString(cursor.getColumnIndexOrThrow(COL_TIME)));
        }
        cursor.close();
        db.close();
        return record;
    }

    /**
     * 删除记录
     */
    public int deleteRecord(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result;
    }

    /**
     * 更新记录
     */
    public int updateRecord(int id, String title, String content, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TITLE, title);
        values.put(COL_CONTENT, content);
        values.put(COL_TIME, time);
        int result = db.update(TABLE_NAME, values, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result;
    }
}

