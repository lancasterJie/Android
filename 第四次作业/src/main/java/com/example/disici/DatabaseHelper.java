package com.example.disici;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private MyDbHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseHelper(Context context) {
        dbHelper = new MyDbHelper(context);
    }

    // 打开数据库连接
    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    // 关闭数据库连接
    public void close() {
        dbHelper.close();
    }

    // 插入记录
    public long insertRecord(Record record) {
        open();
        ContentValues values = new ContentValues();
        values.put(MyDbHelper.COLUMN_TITLE, record.getTitle());
        values.put(MyDbHelper.COLUMN_CONTENT, record.getContent());
        values.put(MyDbHelper.COLUMN_TIME, record.getTime());

        long result = database.insert(MyDbHelper.TABLE_RECORDS, null, values);
        close();
        return result;
    }

    // 获取所有记录
    public List<Record> getAllRecords() {
        List<Record> records = new ArrayList<>();
        open();

        Cursor cursor = database.query(
                MyDbHelper.TABLE_RECORDS,
                null,
                null,
                null,
                null,
                null,
                MyDbHelper.COLUMN_TIME + " DESC"
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Record record = new Record();
                record.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MyDbHelper.COLUMN_ID)));
                record.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MyDbHelper.COLUMN_TITLE)));
                record.setContent(cursor.getString(cursor.getColumnIndexOrThrow(MyDbHelper.COLUMN_CONTENT)));
                record.setTime(cursor.getString(cursor.getColumnIndexOrThrow(MyDbHelper.COLUMN_TIME)));
                records.add(record);
            } while (cursor.moveToNext());

            cursor.close();
        }

        close();
        return records;
    }

    // 根据ID获取记录
    public Record getRecordById(int id) {
        open();
        Record record = null;

        Cursor cursor = database.query(
                MyDbHelper.TABLE_RECORDS,
                null,
                MyDbHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            record = new Record();
            record.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MyDbHelper.COLUMN_ID)));
            record.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MyDbHelper.COLUMN_TITLE)));
            record.setContent(cursor.getString(cursor.getColumnIndexOrThrow(MyDbHelper.COLUMN_CONTENT)));
            record.setTime(cursor.getString(cursor.getColumnIndexOrThrow(MyDbHelper.COLUMN_TIME)));
            cursor.close();
        }

        close();
        return record;
    }

    // 删除记录
    public int deleteRecord(int id) {
        open();
        int result = database.delete(
                MyDbHelper.TABLE_RECORDS,
                MyDbHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
        close();
        return result;
    }

    // 更新记录
    public int updateRecord(Record record) {
        open();
        ContentValues values = new ContentValues();
        values.put(MyDbHelper.COLUMN_TITLE, record.getTitle());
        values.put(MyDbHelper.COLUMN_CONTENT, record.getContent());
        values.put(MyDbHelper.COLUMN_TIME, record.getTime());

        int result = database.update(
                MyDbHelper.TABLE_RECORDS,
                values,
                MyDbHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(record.getId())}
        );
        close();
        return result;
    }
}