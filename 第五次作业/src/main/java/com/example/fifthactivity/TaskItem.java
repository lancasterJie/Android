package com.example.fifthactivity;

public class TaskItem {
    private String taskName;
    private String dueDate;

    // 构造方法
    public TaskItem(String taskName, String dueDate) {
        this.taskName = taskName;
        this.dueDate = dueDate;
    }

    // Getter方法
    public String getTaskName() {
        return taskName;
    }

    public String getDueDate() {
        return dueDate;
    }
}
