package com.example.todolist;

public class TaskItem {
    private String taskName;   // 任务名称
    private String dueDate;    // 截止日期
    private boolean isImportant; // 是否为重要任务（星星标记）

    // 构造方法
    public TaskItem(String taskName, String dueDate, boolean isImportant) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.isImportant = isImportant;
    }

    // Getter方法（Adapter需要获取数据）
    public String getTaskName() {
        return taskName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public boolean isImportant() {
        return isImportant;
    }
}