package com.example.datapersistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private MyDbHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseManager(Context context) {
        dbHelper = new MyDbHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // 新增记录
    public long addRecord(Record record) {
        ContentValues values = new ContentValues();
        values.put(MyDbHelper.COLUMN_TITLE, record.getTitle());
        values.put(MyDbHelper.COLUMN_CONTENT, record.getContent());
        values.put(MyDbHelper.COLUMN_TIME, record.getTime());

        return database.insert(MyDbHelper.TABLE_RECORDS, null, values);
    }

    // 获取所有记录
    public List<Record> getAllRecords() {
        List<Record> records = new ArrayList<>();

        Cursor cursor = database.query(
                MyDbHelper.TABLE_RECORDS,
                null, null, null, null, null,
                MyDbHelper.COLUMN_TIME + " DESC"
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Record record = cursorToRecord(cursor);
            records.add(record);
            cursor.moveToNext();
        }
        cursor.close();

        return records;
    }

    // 根据ID获取记录
    public Record getRecordById(int id) {
        Cursor cursor = database.query(
                MyDbHelper.TABLE_RECORDS,
                null,
                MyDbHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null
        );

        if (cursor != null) {
            cursor.moveToFirst();
            Record record = cursorToRecord(cursor);
            cursor.close();
            return record;
        }
        return null;
    }

    // 删除记录
    public void deleteRecord(int id) {
        database.delete(
                MyDbHelper.TABLE_RECORDS,
                MyDbHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
    }

    // 更新记录
    public int updateRecord(Record record) {
        ContentValues values = new ContentValues();
        values.put(MyDbHelper.COLUMN_TITLE, record.getTitle());
        values.put(MyDbHelper.COLUMN_CONTENT, record.getContent());
        values.put(MyDbHelper.COLUMN_TIME, record.getTime());

        return database.update(
                MyDbHelper.TABLE_RECORDS,
                values,
                MyDbHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(record.getId())}
        );
    }

    private Record cursorToRecord(Cursor cursor) {
        Record record = new Record();
        record.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MyDbHelper.COLUMN_ID)));
        record.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MyDbHelper.COLUMN_TITLE)));
        record.setContent(cursor.getString(cursor.getColumnIndexOrThrow(MyDbHelper.COLUMN_CONTENT)));
        record.setTime(cursor.getString(cursor.getColumnIndexOrThrow(MyDbHelper.COLUMN_TIME)));
        return record;
    }
}