package com.dyk.homework;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "my_db.db";
    private static final String TABLE_NAME = "to_do_list";
    private static final int DB_VERSION = 1;
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TASK_CONTENT = "TaskContent";
    private static final String COLUMN_TASK_DATE = "TaskDate";
    private static final String COLUMN_DONE = "done";
    private static final String COLUMN_IMPORTANCE = "importance";

    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+ TABLE_NAME +" (" +
                COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TASK_CONTENT +" TEXT," +
                COLUMN_TASK_DATE+" TEXT," +
                COLUMN_DONE + " INTEGER," +
                COLUMN_IMPORTANCE + " INTEGER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    long insert(ToDoItem item){
        SQLiteDatabase db = null;

        try{
            db = this.getWritableDatabase();
            ContentValues value = item.getValue();
            return db.insert(TABLE_NAME, null,value);
        }finally {
            if(db!=null){
                db.close();
            }
        }
    }

    long update(ToDoItem item){
        SQLiteDatabase db = null;
        try{
            db = this.getWritableDatabase();
            ContentValues value = item.getValue();

            return db.update(TABLE_NAME,value,COLUMN_ID+"=?",new String[]{String.valueOf(item.getId())});
        }finally {
            if(db!=null && db.isOpen()){
                db.close();
            }
        }
    }

    List<ToDoItem> queryAll(){
        SQLiteDatabase db = null;
        Cursor cursor = null;
        List res = null;

        try{
            db = this.getReadableDatabase();
            cursor = db.query(TABLE_NAME,
                    new String[]{COLUMN_ID,COLUMN_TASK_CONTENT,COLUMN_TASK_DATE,COLUMN_DONE,COLUMN_IMPORTANCE},
                    null,null,null,null,COLUMN_ID
                    );
            res = new ArrayList<ToDoItem>();
            if(cursor != null && cursor.moveToFirst()){
                do{
                    long id = cursor.getInt(0);
                    String TaskContent = cursor.getString(1);
                    String TaskDate = cursor.getString(2);
                    boolean done = cursor.getInt(3)==1?true:false;
                    boolean importance = cursor.getInt(4)==1?true:false;

                    res.add(new ToDoItem(id,TaskContent,TaskDate,done,importance));
                }while (cursor.moveToNext());
            }
        }finally {
            if(cursor!=null && !cursor.isClosed()){
                cursor.close();
            }
            if(db!=null && db.isOpen()){
                db.close();
            }
        }

        return res;
    }

    List<ToDoItem> queryByKeyword(String keyword){
        SQLiteDatabase db = null;
        Cursor cursor = null;
        List res = null;

        try{
            db = this.getReadableDatabase();
            cursor = db.query(TABLE_NAME,
                    new String[]{COLUMN_ID,COLUMN_TASK_CONTENT,COLUMN_TASK_DATE,COLUMN_DONE,COLUMN_IMPORTANCE},
                    COLUMN_TASK_CONTENT + " LIKE ?",new String[]{"%"+ keyword + "%"},null,null,COLUMN_ID
            );
            res = new ArrayList<ToDoItem>();
            if(cursor != null && cursor.moveToFirst()){
                do{
                    long id = cursor.getInt(0);
                    String TaskContent = cursor.getString(1);
                    String TaskDate = cursor.getString(2);
                    boolean done = cursor.getInt(3)==1?true:false;
                    boolean importance = cursor.getInt(4)==1?true:false;

                    res.add(new ToDoItem(id,TaskContent,TaskDate,done,importance));
                }while (cursor.moveToNext());
            }
        }finally {
            if(cursor!=null && !cursor.isClosed()){
                cursor.close();
            }
            if(db!=null && db.isOpen()){
                db.close();
            }
        }

        return res;
    }

    public ToDoItem queryByID(long ID){
        SQLiteDatabase db = null;
        Cursor cursor = null;
        ToDoItem res = null;

        try{
            db = this.getReadableDatabase();
            cursor = db.query(TABLE_NAME,
                    new String[]{COLUMN_ID,COLUMN_TASK_CONTENT,COLUMN_TASK_DATE,COLUMN_DONE,COLUMN_IMPORTANCE},
                    COLUMN_ID + "=?",new String[]{String.valueOf(ID)},null,null,COLUMN_ID
            );
            if(cursor != null && cursor.moveToFirst()){
                long id = cursor.getInt(0);
                String TaskContent = cursor.getString(1);
                String TaskDate = cursor.getString(2);
                boolean done = cursor.getInt(3)==1?true:false;
                boolean importance = cursor.getInt(4)==1?true:false;

                res = new ToDoItem(id,TaskContent,TaskDate,done,importance);
            }
        }finally {
            if(cursor!=null && !cursor.isClosed()){
                cursor.close();
            }
            if(db!=null && db.isOpen()){
                db.close();
            }
        }
        return res;
    }
}
