package com.example.homework6;


public class TaskItem {
    private String taskName;
    private String dueDate;
    private boolean isCompleted; // 新增属性

    public TaskItem(String taskName, String dueDate) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.isCompleted = false; // 默认未完成
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() { // 新增getter方法
        return isCompleted;
    }

    public void setCompleted(boolean completed) { // 新增setter方法
        isCompleted = completed;
    }
}