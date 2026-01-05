package com.example.homework4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "notepad.db";
    private static final int DATABASE_VERSION = 1;

    // 表名和列名
    public static final String TABLE_RECORDS = "records";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_TIME = "time";

    // 创建表的SQL语句
    private static final String CREATE_TABLE_RECORDS =
            "CREATE TABLE " + TABLE_RECORDS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_CONTENT + " TEXT, " +
                    COLUMN_TIME + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_RECORDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORDS);
        onCreate(db);
    }

    // 添加记录
    public long addRecord(Record record) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, record.getTitle());
        values.put(COLUMN_CONTENT, record.getContent());
        values.put(COLUMN_TIME, record.getTime());

        long id = db.insert(TABLE_RECORDS, null, values);
        db.close();
        return id;
    }

    // 获取所有记录
    public List<Record> getAllRecords() {
        List<Record> records = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_RECORDS + " ORDER BY " + COLUMN_TIME + " DESC";
        Cursor cursor = db.rawQuery(query, null);

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

    // 根据ID获取记录
    public Record getRecordById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECORDS,
                new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_CONTENT, COLUMN_TIME},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null);

        Record record = null;
        if (cursor != null && cursor.moveToFirst()) {
            record = new Record();
            record.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            record.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
            record.setContent(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)));
            record.setTime(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME)));
            cursor.close();
        }
        db.close();
        return record;
    }

    // 更新记录
    public int updateRecord(Record record) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, record.getTitle());
        values.put(COLUMN_CONTENT, record.getContent());
        values.put(COLUMN_TIME, record.getTime());

        int result = db.update(TABLE_RECORDS, values,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(record.getId())});
        db.close();
        return result;
    }

    // 删除记录
    public void deleteRecord(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECORDS, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}