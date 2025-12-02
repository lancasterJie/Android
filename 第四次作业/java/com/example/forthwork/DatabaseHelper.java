package com.example.forthwork;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "forthwork.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_RECORDS = "records";

    // 列名
    private static final String KEY_ID = "_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_TIME = "time";

    // 创建表SQL
    private static final String CREATE_TABLE_RECORDS =
            "CREATE TABLE " + TABLE_RECORDS + "("
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_TITLE + " TEXT,"
                    + KEY_CONTENT + " TEXT,"
                    + KEY_TIME + " TEXT"
                    + ")";

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

    // 新增记录
    public long addRecord(Record record) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, record.getTitle());
        values.put(KEY_CONTENT, record.getContent());
        values.put(KEY_TIME, record.getTime());

        long id = db.insert(TABLE_RECORDS, null, values);
        db.close();
        return id;
    }

    // 获取所有记录
    public List<Record> getAllRecords() {
        List<Record> records = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_RECORDS + " ORDER BY " + KEY_TIME + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Record record = new Record();
                record.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                record.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
                record.setContent(cursor.getString(cursor.getColumnIndex(KEY_CONTENT)));
                record.setTime(cursor.getString(cursor.getColumnIndex(KEY_TIME)));

                records.add(record);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return records;
    }

    // 获取单个记录
    public Record getRecord(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECORDS,
                new String[]{KEY_ID, KEY_TITLE, KEY_CONTENT, KEY_TIME},
                KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null) cursor.moveToFirst();

        Record record = new Record(
                cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                cursor.getString(cursor.getColumnIndex(KEY_TITLE)),
                cursor.getString(cursor.getColumnIndex(KEY_CONTENT)),
                cursor.getString(cursor.getColumnIndex(KEY_TIME))
        );

        cursor.close();
        db.close();
        return record;
    }

    // 删除记录
    public void deleteRecord(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECORDS, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    // 更新记录
    public int updateRecord(Record record) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, record.getTitle());
        values.put(KEY_CONTENT, record.getContent());
        values.put(KEY_TIME, record.getTime());

        return db.update(TABLE_RECORDS, values,
                KEY_ID + " = ?",
                new String[]{String.valueOf(record.getId())});
    }
}