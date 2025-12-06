package com.example.fourth;

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

    // Table and column names
    public static final String TABLE_RECORDS = "records";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_TIME = "time";

    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_RECORDS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_CONTENT + " TEXT," +
                COLUMN_TIME + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORDS);
        onCreate(db);
    }

    // Insert a new record
    public long insertRecord(String title, String content, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_CONTENT, content);
        values.put(COLUMN_TIME, time);
        long id = db.insert(TABLE_RECORDS, null, values);
        db.close();
        return id;
    }

    // Get all records
    public List<Record> getAllRecords() {
        List<Record> records = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECORDS, null, null, null, null, null, 
                COLUMN_TIME + " DESC");
        
        if (cursor.moveToFirst()) {
            do {
                Record record = new Record();
                record.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                record.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                record.setContent(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)));
                record.setTime(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME)));
                records.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return records;
    }

    // Get a single record by ID
    public Record getRecordById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECORDS, null, 
                COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, 
                null, null, null);
        
        Record record = null;
        if (cursor.moveToFirst()) {
            record = new Record();
            record.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            record.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
            record.setContent(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)));
            record.setTime(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME)));
        }
        cursor.close();
        db.close();
        return record;
    }

    // Update a record
    public int updateRecord(int id, String title, String content, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_CONTENT, content);
        values.put(COLUMN_TIME, time);
        int rowsAffected = db.update(TABLE_RECORDS, values, 
                COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected;
    }

    // Delete a record
    public int deleteRecord(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_RECORDS, 
                COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted;
    }
}
