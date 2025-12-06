package com.example.expriment04;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MyDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "myapp.db";
    private static final int DB_VERSION = 1;

    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE tasks (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "content TEXT," +
                "time TEXT)";
        db.execSQL(sql);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS records");
        onCreate(db);
    }
    public ArrayList<TaskListAdapter.TaskData> getTaskList() {
        ArrayList<TaskListAdapter.TaskData> taskDataList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor tasks = db.query("tasks", new String[]{"content", "time"}, null, null, null, null, null);
        int contentIndex = tasks.getColumnIndex("content"),
                timeIndex = tasks.getColumnIndex("time");
        while (tasks.moveToNext()) {
            taskDataList.add(new TaskListAdapter.TaskData(tasks.getString(contentIndex), tasks.getString(timeIndex)));
        }
        return taskDataList;
    }
    public void addTaskData(TaskListAdapter.TaskData data) {
        String content, title, time;
        SQLiteDatabase db = getReadableDatabase();
        if (data.getTitle().isEmpty()) {
            title = "";
            content = "";
        }
        else {
            content = data.getTitle();
            title = new String(content.getBytes(),0,1);

        }
        time = data.getTime();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("content", content);
        contentValues.put("time", time);
        db.insert("tasks", null, contentValues);
    }
}
