package com.example.task5;

public class TaskItem {
    private String taskName;    // 任务名称
    private String dueDate;     // 截止日期
    private boolean isStarred;  // 星星状态
    private boolean isCompleted; // 完成状态（是否划横线）

    // 构造方法（初始无星星、无划线）
    public TaskItem(String taskName, String dueDate) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.isStarred = false;    // 初始无星星
        this.isCompleted = false;  // 初始无划线
    }

    // Getter & Setter
    public String getTaskName() { return taskName; }
    public String getDueDate() { return dueDate; }
    public boolean isStarred() { return isStarred; }
    public void setStarred(boolean starred) { isStarred = starred; }
    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
}