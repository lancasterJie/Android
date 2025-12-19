package com.example.todolist;

import java.io.Serializable;

public class TaskItem implements Serializable {
    private String taskName;
    private String dueDate;
    private boolean isCompleted;
    private boolean isFavorite;
    private long id;
    private static long idCounter = 0;

    public TaskItem(String taskName, String dueDate) {
        this.id = idCounter++;
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.isCompleted = false;
        this.isFavorite = false;
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

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public void toggleFavorite() {
        isFavorite = !isFavorite;
    }

    public long getId() {
        return id;
    }

    public boolean containsKeyword(String keyword) {
        return taskName.toLowerCase().contains(keyword.toLowerCase()) ||
                dueDate.contains(keyword);
    }
}