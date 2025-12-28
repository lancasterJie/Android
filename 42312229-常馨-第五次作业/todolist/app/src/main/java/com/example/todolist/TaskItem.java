package com.example.todolist;

public class TaskItem {
    private String taskName;
    private String dueDate;
    private boolean isCompleted;
    private String id;

    public TaskItem(String taskName, String dueDate) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.isCompleted = false;
        this.id = String.valueOf(System.currentTimeMillis());
    }

    public TaskItem(String taskName, String dueDate, boolean isCompleted) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
        this.id = String.valueOf(System.currentTimeMillis());
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

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getId() {
        return id;
    }
}