package com.example.todolist;

public class TaskItem {
    private String taskName;
    private String dueDate;
    private boolean isImportant; // 新增：标记是否为重要任务

    // 构造方法
    public TaskItem(String taskName, String dueDate, boolean isImportant) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.isImportant = isImportant;
    }

    // Getter方法
    public String getTaskName() {
        return taskName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public boolean isImportant() { // 新增
        return isImportant;
    }
}