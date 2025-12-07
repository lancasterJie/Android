package com.example.listview_homework;

/**
 * TaskItem 数据模型类
 * 用于表示待办事项的任务项
 */
public class TaskItem {
    private String taskName;  // 任务名称
    private String dueDate;   // 截止日期
    private boolean isCompleted;  // 任务完成状态

    /**
     * 构造函数
     * @param taskName 任务名称
     * @param dueDate 截止日期
     */
    public TaskItem(String taskName, String dueDate) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.isCompleted = false;  // 默认未完成
    }

    /**
     * 构造函数（包含完成状态）
     * @param taskName 任务名称
     * @param dueDate 截止日期
     * @param isCompleted 是否完成
     */
    public TaskItem(String taskName, String dueDate, boolean isCompleted) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
    }

    /**
     * 获取任务名称
     * @return 任务名称
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * 设置任务名称
     * @param taskName 任务名称
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * 获取截止日期
     * @return 截止日期
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * 设置截止日期
     * @param dueDate 截止日期
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * 获取任务完成状态
     * @return 是否完成
     */
    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * 设置任务完成状态
     * @param completed 是否完成
     */
    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
