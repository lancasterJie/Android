package com.example.txte5;

/**
 * 简单的任务数据模型，包含任务名称和截止日期。
 */
public class TaskItem {
    private final String taskName;
    private final String dueDate;

    public TaskItem(String taskName, String dueDate) {
        this.taskName = taskName;
        this.dueDate = dueDate;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDueDate() {
        return dueDate;
    }
}

