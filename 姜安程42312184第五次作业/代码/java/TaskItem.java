package com.example.homework5;

public class TaskItem {
    private String taskName;
    private String dueDate;

    public TaskItem(String taskName, String dueDate) {
        this.taskName = taskName;
        this.dueDate = dueDate;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}