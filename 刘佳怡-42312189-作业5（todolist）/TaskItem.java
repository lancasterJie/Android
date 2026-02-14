// src/main/java/com/example/todolistapp/TaskItem.java
package com.example.work5listview;

public class TaskItem {
    private String taskName;
    private String dueDate;
    private boolean isPriority; // 是否为高优先级（带星标）

    public TaskItem(String taskName, String dueDate) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.isPriority = false; // 默认非优先级
    }

    public TaskItem(String taskName, String dueDate, boolean isPriority) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.isPriority = isPriority;
    }

    // Getters and Setters
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

    public boolean isPriority() {
        return isPriority;
    }

    public void setPriority(boolean priority) {
        isPriority = priority;
    }
}