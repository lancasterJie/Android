package com.example.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView taskListView;
    private TextView taskCountTextView;
    private Button addTaskButton;
    private ArrayList<TaskItem> taskList;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskListView = findViewById(R.id.taskListView);
        taskCountTextView = findViewById(R.id.taskCountTextView);
        addTaskButton = findViewById(R.id.addTaskButton);

        taskList = new ArrayList<>();
        taskList.add(new TaskItem("Complete Android Homework", "2025-12-20"));
        taskList.add(new TaskItem("Buy groceries", "2024-05-01"));
        taskList.add(new TaskItem("Walk the dog", "2024-04-20"));
        taskList.add(new TaskItem("Call John", "2024-04-23"));

        taskAdapter = new TaskAdapter(this, taskList);
        taskListView.setAdapter(taskAdapter);

        updateTaskCount();

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddTaskDialog();
            }
        });

        taskListView.setOnItemLongClickListener((parent, view, position, id) -> {
            showDeleteTaskDialog(position);
            return true;
        });
    }

    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("添加新任务");

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_task, null);
        builder.setView(dialogView);

        EditText taskNameEditText = dialogView.findViewById(R.id.taskNameEditText);
        EditText dueDateEditText = dialogView.findViewById(R.id.dueDateEditText);

        builder.setPositiveButton("添加", (dialog, which) -> {
            String taskName = taskNameEditText.getText().toString().trim();
            String dueDate = dueDateEditText.getText().toString().trim();

            if (taskName.isEmpty()) {
                Toast.makeText(MainActivity.this, "请输入任务名称", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dueDate.isEmpty()) {
                Toast.makeText(MainActivity.this, "请输入截止日期", Toast.LENGTH_SHORT).show();
                return;
            }

            addNewTask(taskName, dueDate);
        });

        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    private void addNewTask(String taskName, String dueDate) {
        TaskItem newTask = new TaskItem(taskName, dueDate);
        taskList.add(newTask);
        taskAdapter.notifyDataSetChanged();
        updateTaskCount();
        Toast.makeText(this, "任务添加成功", Toast.LENGTH_SHORT).show();
    }

    public void showDeleteTaskDialog(final int position) {
        TaskItem task = taskList.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("删除任务");
        builder.setMessage("确定要删除这个任务吗？\n\n" +
                "任务: " + task.getTaskName() + "\n" +
                "截止日期: " + task.getDueDate());

        builder.setPositiveButton("删除", (dialog, which) -> deleteTask(position));
        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    private void deleteTask(int position) {
        String taskName = taskList.get(position).getTaskName();
        taskList.remove(position);
        taskAdapter.notifyDataSetChanged();
        updateTaskCount();
        Toast.makeText(this, "已删除任务: " + taskName, Toast.LENGTH_SHORT).show();
    }

    public void updateTaskCount() {
        int totalTasks = taskList.size();
        int completedTasks = 0;
        int pendingTasks = 0;

        for (TaskItem task : taskList) {
            if (task.isCompleted()) {
                completedTasks++;
            } else {
                pendingTasks++;
            }
        }

        taskCountTextView.setText(
                "总任务: " + totalTasks +
                        " | 已完成: " + completedTasks +
                        " | 未完成: " + pendingTasks
        );
    }
}
