package com.example.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyDBHelper  extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "StudentDB";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "records";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_CONTENT = "content";
    private static final String COLUMN_TIME = "time";

    public MyDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +" ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, "+
                COLUMN_CONTENT + " TEXT, "+
                COLUMN_TIME + " TEXT) ";
        db.execSQL(CREATE_TABLE);
    }
    public Cursor getRecordById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +
                COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }
    public long addRecord(String title, String content){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Long timeStamp = System.currentTimeMillis();
        String time = formatTime(timeStamp);
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_CONTENT, content);
        values.put(COLUMN_TIME, time);
        return  db.insert(TABLE_NAME, null, values);
    }
    private String formatTime(long timeStamp){
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            return simpleDateFormat.format(new Date(timeStamp));
        } catch (Exception e) {
            return "Invalid Time";
        }
    }
    public Cursor getAllRecord(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
    public int updateStudent(int id, String title, String content){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Long timeStamp = System.currentTimeMillis();
        String time = formatTime(timeStamp);
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_CONTENT, content);
        values.put(COLUMN_TIME, time);
        return db.update(TABLE_NAME, values,
                COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public int deleteRecord(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,
                COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 创建新表
        onCreate(db);
    }
}
