// Record.java (保持你原有结构，仅补全)
package com.example.work4;

import android.os.Bundle;

public class Record {
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_TIME = "time";

    public int id;
    public String title;
    public String content;
    public String time;

    // 用于新增记录（无 id）
    public Record(String content) {
        this.content = content;
        int end = Math.min(content.indexOf('\n'), 10);
        if (end == -1) end = Math.min(content.length(), 10);
        this.title = content.substring(0, end);
        this.time = java.text.DateFormat.getDateTimeInstance().format(new java.util.Date());
    }

    // 用于从数据库读取（有 id）
    public Record(int id, String title, String content, String time) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.time = time;
    }

    // Getter for MyDbHelper
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getTime() { return time; }
    public int getId() { return id; }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_ID, id);
        bundle.putString(KEY_TITLE, title);
        bundle.putString(KEY_CONTENT, content);
        bundle.putString(KEY_TIME, time);
        return bundle;
    }

    public static Record fromBundle(Bundle bundle) {
        return new Record(
                bundle.getInt(KEY_ID),
                bundle.getString(KEY_TITLE),
                bundle.getString(KEY_CONTENT),
                bundle.getString(KEY_TIME)
        );
    }
}