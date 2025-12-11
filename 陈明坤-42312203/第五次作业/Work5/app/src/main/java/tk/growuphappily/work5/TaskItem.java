package tk.growuphappily.work5;

public class TaskItem {
    public String taskName;
    public String dueDate;
    public boolean marked;

    public TaskItem(String taskName, String dueDate) {
        this(taskName, dueDate, false);
    }

    public TaskItem(String taskName, String dueDate, boolean marked) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.marked = marked;
    }
}
