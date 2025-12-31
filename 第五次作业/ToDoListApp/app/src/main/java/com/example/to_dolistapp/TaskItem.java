package com.example.to_dolistapp;

public class TaskItem {
    private String taskName;
    private String dueDate;

    public TaskItem(String taskName, String dueDate){
        this.taskName = taskName;
        this.dueDate = dueDate;
    }

    public String getTaskName(){
        return taskName;
    }

    public String getDueDate(){
        return dueDate;
    }
}



