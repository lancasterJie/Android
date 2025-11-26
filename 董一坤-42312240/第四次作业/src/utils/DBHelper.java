package com.dyk.homework.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dyk.homework.SQLActivity;
import com.dyk.homework.entity.MyEntity;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "mydb.db";
    private static final String TABLE_NAME = "records";
    private static final int DB_VERSION = 1;
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_CONTENT = "content";
    private static final String COLUMN_TIME = "time";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+ TABLE_NAME +" (" +
                COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE +" TEXT," +
                COLUMN_CONTENT+" TEXT," +
                COLUMN_TIME + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public long insert(MyEntity obj){
        SQLiteDatabase db = null;

        try{
            db = this.getWritableDatabase();
            ContentValues value = obj.getValue();
            return db.insert(TABLE_NAME,null,value);
        }finally {
            if(db!=null){
                db.close();
            }
        }
    }

    public List<MyEntity> queryAll(){
        SQLiteDatabase db = null;
        List<MyEntity> list = null;
        Cursor cursor = null;

        try {
            db = this.getReadableDatabase();
            cursor = db.query(
                    TABLE_NAME,
                    new String[]{COLUMN_ID,COLUMN_TITLE,COLUMN_CONTENT,COLUMN_TIME},
                    null, null,null,null,COLUMN_ID);

            if(cursor != null && cursor.moveToFirst()){
                list = new ArrayList<>();
               do{
                    long _id = cursor.getLong(0);
                    String title = cursor.getString(1);
                    String content = cursor.getString(2);
                    String time = cursor.getString(3);

                    list.add(new MyEntity(_id,title,content,time));
                }while(cursor.moveToNext());
            }
        }finally {
            if(cursor!=null){
                cursor.close();
            }
            if(db!=null){
                db.close();
            }
        }
        return list;
    }

    public MyEntity queryById(long id){
        SQLiteDatabase db = null;
        Cursor cursor = null;
        MyEntity res = null;

        try {
            db = this.getReadableDatabase();
            cursor = db.query(TABLE_NAME,new String[]{COLUMN_ID,COLUMN_TITLE,COLUMN_CONTENT,COLUMN_TIME}
                    ,COLUMN_ID+" = ?",new String[]{String.valueOf(id)},null,null,null);
            if(cursor!=null && cursor.moveToFirst()){
                long _id = cursor.getLong(0);
                String title = cursor.getString(1);
                String content = cursor.getString(2);
                String time = cursor.getString(3);

                res = new MyEntity(_id,title,content,time);
            }
        }finally {
            if(cursor!=null){
                cursor.close();
            }
            if(db!=null){
                db.close();
            }
        }

        return res;
    }

    public long delete(long id){
        SQLiteDatabase db = null;
        try{
            db = this.getWritableDatabase();
            return db.delete(TABLE_NAME,COLUMN_ID + " = ?",new String[]{String.valueOf(id)});
        }finally {
            if(db!=null){
                db.close();
            }
        }
    }

    public long deleteAll(){
        SQLiteDatabase db = null;
        try{
            db = this.getWritableDatabase();
            return db.delete(TABLE_NAME,"1 = 1",null);
        }finally {
            if(db!=null){
                db.close();
            }
        }
    }

    public int upgrade(MyEntity obj){
        SQLiteDatabase db = null;

        try{
            db = this.getWritableDatabase();
            ContentValues value = obj.getValue();

            return db.update(TABLE_NAME,value,COLUMN_ID + " = ?",new String[]{String.valueOf(obj.get_id())});
        }finally {
            if(db!=null){
                db.close();
            }
        }
    }
}
