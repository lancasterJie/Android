package tk.growuphappily.work4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyDbHelper extends SQLiteOpenHelper {
    public static class Value {
        public static final String ARG_TITLE = "ARG_TITLE";
        public static final String ARG_CONTENT = "ARG_CONTENT";
        public static final String ARG_DATE = "ARG_DATE";
        public static final String ARG_ID = "ARG_ID";
        public int id;
        public String title;
        public String content;
        public String time;

        public Value(String content) {
            int pos = Math.min(content.indexOf(' '), content.indexOf('\n'));
            if(pos == -1) pos = 5;
            title = content.substring(0, pos);
            time = DateFormat.getDateTimeInstance().format(new Date());
            this.content = content;
        }

        public Value(int id, String title, String content, String time) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.time = time;
        }

        public ContentValues toContentValues() {
            var v = new ContentValues();
            v.put("title", title);
            v.put("content", content);
            v.put("time", time);
            return v;
        }

        public static Value fromBundle(Bundle bundle) {
            var title = bundle.getString(ARG_TITLE);
            var content = bundle.getString(ARG_CONTENT);
            var time = bundle.getString(ARG_DATE);
            var id = bundle.getInt(ARG_ID);
            return new Value(id, title, content, time);
        }

        public Bundle toBundle() {
            Bundle args = new Bundle();
            args.putString(ARG_TITLE, title);
            args.putString(ARG_CONTENT, content);
            args.putString(ARG_DATE, time);
            args.putInt(ARG_ID, id);
            return args;
        }
    }

    private static final String DB_NAME = "record.db";
    private static final int DB_VERSION = 1;

    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE records (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "content TEXT," +
                "time TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // db.execSQL("DROP TABLE IF EXISTS records");
        // onCreate(db);
    }

    public void insert(Value value) {
        getWritableDatabase().insert("records", null, value.toContentValues());
    }

    public Value[] readAll() {
        var list = new ArrayList<Value>();
        try(var data = getReadableDatabase().query("records", new String[]{"_id", "title", "content", "time"}, null, null, null, null, "_id DESC")) {
            while(data.moveToNext()) {
                var v = new Value(data.getInt(0), data.getString(1), data.getString(2), data.getString(3));
                list.add(v);
            }
        } catch (Exception e) {
            Log.e("SQLite", e.getMessage());
        }
        var arr = new Value[list.size()];
        return list.toArray(arr);
    }

    public void delete(Value v) {
        getWritableDatabase().delete("records", "_id=?", new String[]{String.valueOf(v.id)});
    }

    public void delete(int id) {
        getWritableDatabase().delete("records", "_id=?", new String[]{String.valueOf(id)});
    }

    public void edit(int id, String content) {
        getWritableDatabase().update("records", new Value(content).toContentValues(), "_id=?", new String[]{String.valueOf(id)});
    }
}