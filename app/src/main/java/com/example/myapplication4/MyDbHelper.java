package com.example.myapplication4;

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
    private static final String TABLE_RECORDS = "records";
    private static final String KEY_ID = "_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_TIME = "time";

    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_RECORDS + "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_TITLE + " TEXT," +
                KEY_CONTENT + " TEXT," +
                KEY_TIME + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORDS);
        onCreate(db);
    }

    // 添加记录
    public void addRecord(Record record) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, record.getTitle());
        values.put(KEY_CONTENT, record.getContent());
        values.put(KEY_TIME, record.getTime());

        db.insert(TABLE_RECORDS, null, values);
        db.close();
    }

    // 获取所有记录
    public List<Record> getAllRecords() {
        List<Record> recordList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_RECORDS + " ORDER BY " + KEY_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Record record = new Record();
                record.setId(cursor.getInt(0));
                record.setTitle(cursor.getString(1));
                record.setContent(cursor.getString(2));
                record.setTime(cursor.getString(3));
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return recordList;
    }

    // 根据ID获取记录
    public Record getRecord(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECORDS,
                new String[]{KEY_ID, KEY_TITLE, KEY_CONTENT, KEY_TIME},
                KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null) cursor.moveToFirst();

        Record record = new Record(
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3));
        record.setId(cursor.getInt(0));

        cursor.close();
        db.close();
        return record;
    }

    // 删除记录
    public void deleteRecord(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECORDS, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}