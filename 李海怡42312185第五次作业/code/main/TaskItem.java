package com.example.todolist;

public class TaskItem {
    private String taskName;
    private String dueDate;
    private boolean isCompleted;
    private int id;
    private static int counter = 0;

    public TaskItem(String taskName, String dueDate) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.isCompleted = false;
        this.id = counter++;
    }

    public TaskItem(String taskName, String dueDate, boolean isCompleted) {
        this(taskName, dueDate);
        this.isCompleted = isCompleted;
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

    public int getId() {
        return id;
    }
}
