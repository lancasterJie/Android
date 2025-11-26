package com.dyk.homework.entity;

import android.content.ContentValues;

public class MyEntity {
    private long _id=-1L;
    private String title;
    private String content;
    private String time;


    public MyEntity() {
    }

    public MyEntity(long _id, String title, String content, String time) {
        this._id = _id;
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public MyEntity(String title, String content, String time) {
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public ContentValues getValue(){
        ContentValues value = new ContentValues();

        if(this._id!=-1L){
            value.put("_id",_id);
        }

        value.put("title",title);
        value.put("content",content);
        value.put("time",time);

        return value;
    }

    /**
     * 获取
     * @return _id
     */
    public long get_id() {
        return _id;
    }

    /**
     * 设置
     * @param _id
     */
    public void set_id(long _id) {
        this._id = _id;
    }

    /**
     * 获取
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取
     * @return time
     */
    public String getTime() {
        return time;
    }

    /**
     * 设置
     * @param time
     */
    public void setTime(String time) {
        this.time = time;
    }

    public String toString() {
        return "_id = " + _id + ",\n title = " + title + ",\n content = " + content + ",\n time = " + time + "\n";
    }
}
