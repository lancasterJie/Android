package com.example.list;

public class TaskItem {
    private String taskName;
    private String dueDate;
    private boolean isFavorite;
    private boolean isCompleted;

    public TaskItem(String taskName, String dueDate) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.isFavorite = false;
        this.isCompleted = false;
    }

    public TaskItem(String taskName, String dueDate, boolean isFavorite) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.isFavorite = isFavorite;
        this.isCompleted = false;
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

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}

