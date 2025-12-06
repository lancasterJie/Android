package com.example.thirdtask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class RecordDao {
    private MyDbHelper dbHelper;

    public RecordDao(Context context) {
        dbHelper = new MyDbHelper(context);
    }

    // 添加记录
    public long addRecord(Record record) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyDbHelper.COLUMN_TITLE, record.getTitle());
        values.put(MyDbHelper.COLUMN_CONTENT, record.getContent());
        values.put(MyDbHelper.COLUMN_TIME, record.getTime());

        long id = db.insert(MyDbHelper.TABLE_RECORDS, null, values);
        db.close();
        return id;
    }

    // 获取所有记录
    public List<Record> getAllRecords() {
        List<Record> records = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                MyDbHelper.COLUMN_ID,
                MyDbHelper.COLUMN_TITLE,
                MyDbHelper.COLUMN_CONTENT,
                MyDbHelper.COLUMN_TIME
        };

        Cursor cursor = db.query(
                MyDbHelper.TABLE_RECORDS,
                columns,
                null, null, null, null,
                MyDbHelper.COLUMN_TIME + " DESC"
        );

        while (cursor.moveToNext()) {
            Record record = new Record();
            record.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MyDbHelper.COLUMN_ID)));
            record.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MyDbHelper.COLUMN_TITLE)));
            record.setContent(cursor.getString(cursor.getColumnIndexOrThrow(MyDbHelper.COLUMN_CONTENT)));
            record.setTime(cursor.getString(cursor.getColumnIndexOrThrow(MyDbHelper.COLUMN_TIME)));
            records.add(record);
        }

        cursor.close();
        db.close();
        return records;
    }

    // 删除记录
    public int deleteRecord(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete(
                MyDbHelper.TABLE_RECORDS,
                MyDbHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
        db.close();
        return result;
    }

    // 更新记录
    public int updateRecord(Record record) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyDbHelper.COLUMN_TITLE, record.getTitle());
        values.put(MyDbHelper.COLUMN_CONTENT, record.getContent());
        values.put(MyDbHelper.COLUMN_TIME, record.getTime());

        int result = db.update(
                MyDbHelper.TABLE_RECORDS,
                values,
                MyDbHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(record.getId())}
        );
        db.close();
        return result;
    }

    // 根据ID获取记录
    public Record getRecordById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                MyDbHelper.COLUMN_ID,
                MyDbHelper.COLUMN_TITLE,
                MyDbHelper.COLUMN_CONTENT,
                MyDbHelper.COLUMN_TIME
        };

        String selection = MyDbHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(
                MyDbHelper.TABLE_RECORDS,
                columns,
                selection,
                selectionArgs,
                null, null, null
        );

        Record record = null;
        if (cursor.moveToFirst()) {
            record = new Record();
            record.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MyDbHelper.COLUMN_ID)));
            record.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MyDbHelper.COLUMN_TITLE)));
            record.setContent(cursor.getString(cursor.getColumnIndexOrThrow(MyDbHelper.COLUMN_CONTENT)));
            record.setTime(cursor.getString(cursor.getColumnIndexOrThrow(MyDbHelper.COLUMN_TIME)));
        }

        cursor.close();
        db.close();
        return record;
    }
}