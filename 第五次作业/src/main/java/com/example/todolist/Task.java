package com.example.todolist;

public class Task {
    private String title;
    private String date;
    private boolean isOverdue;

    public Task(String title, String date, boolean isOverdue) {
        this.title = title;
        this.date = date;
        this.isOverdue = isOverdue;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public boolean isOverdue() {
        return isOverdue;
    }
}