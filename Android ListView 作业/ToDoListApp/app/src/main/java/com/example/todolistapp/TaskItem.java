package com.example.todolistapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TaskItem {
    private String taskName;
    private String dueDate;
    private boolean isCompleted;
    private int priority; // 1-高, 2-中, 3-低
    private long createdAt;

    public TaskItem(String taskName, String dueDate) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.isCompleted = false;
        this.priority = 2; // 默认中等优先级
        this.createdAt = System.currentTimeMillis();
    }

    public TaskItem(String taskName, String dueDate, int priority) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.isCompleted = false;
        this.priority = priority;
        this.createdAt = System.currentTimeMillis();
    }

    // Getter 和 Setter 方法
    public String getTaskName() { return taskName; }
    public void setTaskName(String taskName) { this.taskName = taskName; }

    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public long getCreatedAt() { return createdAt; }

    // 获取优先级文本
    public String getPriorityText() {
        switch (priority) {
            case 1: return "高";
            case 2: return "中";
            case 3: return "低";
            default: return "中";
        }
    }

    // 获取优先级颜色资源ID
    public int getPriorityColor() {
        switch (priority) {
            case 1: return android.R.color.holo_red_light;
            case 2: return android.R.color.holo_orange_light;
            case 3: return android.R.color.holo_green_light;
            default: return android.R.color.darker_gray;
        }
    }

    // 检查是否过期
    public boolean isOverdue() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date due = sdf.parse(dueDate);
            Date today = new Date();
            return due != null && due.before(today);
        } catch (ParseException e) {
            return false;
        }
    }

    // 获取格式化日期
    public String getFormattedDate() {
        return "截止: " + dueDate;
    }
}