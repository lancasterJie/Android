package com.example.myproject4;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "myapp.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "records";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_TIME = "time";

    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_CONTENT + " TEXT," +
                COLUMN_TIME + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS records");
        onCreate(db);
    }

    public long addrecord(String title,String content)//插入数据
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(COLUMN_TITLE,title);
        values.put(COLUMN_CONTENT,content);
        values.put(COLUMN_TIME,getCurrentTime());

        return db.insert(TABLE_NAME,null,values);
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    @SuppressLint("Range")
    public List<Record> getAllRecords()//获取所有的记录
    {
        List<Record> recordList=new ArrayList<>();

        SQLiteDatabase db=this.getReadableDatabase();

        String sql="SELECT * FROM records ORDER BY time DESC";
        Cursor cursor=db.rawQuery(sql,null);

        if (cursor.moveToFirst())
        {
            do
            {
                Record record = new Record();
                record.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                record.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                record.setContent(cursor.getString(cursor.getColumnIndex("content")));
                record.setTime(cursor.getString(cursor.getColumnIndex("time")));
                recordList.add(record);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordList;
    }

    @SuppressLint("Range")
    public Record getRecordById(int id) //根据特定的ID获取记录
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Record record = null;

        String sql = "SELECT * FROM records WHERE _id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst())
        {
            record = new Record();
            record.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            record.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            record.setContent(cursor.getString(cursor.getColumnIndex("content")));
            record.setTime(cursor.getString(cursor.getColumnIndex("time")));
        }

        cursor.close();
        db.close();

        return record;
    }

    public int deleteRecord(int id)//删除指定ID的记录
    {
        SQLiteDatabase db=this.getWritableDatabase();

        int result = db.delete("records", "_id = ?", new String[]{String.valueOf(id)});

        db.close();
        return result;
    }

    public int updateRecord(Record record)//更新数据库记录
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("title", record.getTitle());
        values.put("content", record.getContent());
        values.put("time", getCurrentTime());
        int result = db.update("records", values, "_id = ?",
                new String[]{String.valueOf(record.getId())});

        db.close();
        return result;
    }
}
