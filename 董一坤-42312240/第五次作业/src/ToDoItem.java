package com.dyk.homework;

import android.content.ContentValues;

public class ToDoItem {
    private long id;
    private String TaskContent;
    private String TaskDate;
    private boolean done;
    private boolean importance;
    public static final int STAR_IMAGE_ID = R.drawable.star_full;
    public static final int EMPTY_IMAGE_ID = R.drawable.star_empty;


    public ToDoItem() {
    }

    public ToDoItem(long id, String TaskContent, String TaskDate, boolean done, boolean importance) {
        this.id = id;
        this.TaskContent = TaskContent;
        this.TaskDate = TaskDate;
        this.done = done;
        this.importance = importance;
    }

    public ToDoItem(String TaskContent, String TaskDate, boolean done, boolean importance) {
        this.id = -1;
        this.TaskContent = TaskContent;
        this.TaskDate = TaskDate;
        this.done = done;
        this.importance = importance;
    }

    public ContentValues getValue(){
        ContentValues value = new ContentValues();

        if(this.id != -1){
            value.put("id",id);
        }
        value.put("TaskContent", TaskContent);
        value.put("TaskDate", TaskDate);
        value.put("done",done?1:0);
        value.put("importance",importance?1:0);

        return value;
    }

    /**
     * 获取
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * 获取
     * @return TaskContent
     */
    public String getTaskContent() {
        return TaskContent;
    }

    /**
     * 设置
     * @param TaskContent
     */
    public void setTaskContent(String TaskContent) {
        this.TaskContent = TaskContent;
    }

    /**
     * 获取
     * @return TaskDate
     */
    public String getTaskDate() {
        return TaskDate;
    }

    /**
     * 设置
     * @param TaskDate
     */
    public void setTaskDate(String TaskDate) {
        this.TaskDate = TaskDate;
    }

    /**
     * 获取
     * @return done
     */
    public boolean isDone() {
        return done;
    }

    /**
     * 设置
     * @param done
     */
    public void setDone(boolean done) {
        this.done = done;
    }

    /**
     * 获取
     * @return importance
     */
    public boolean isImportance() {
        return importance;
    }

    /**
     * 设置
     * @param importance
     */
    public void setImportance(boolean importance) {
        this.importance = importance;
    }
}
