package com.example.listview_homework;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

/**
 * MainActivity - 主活动类
 * 实现待办事项清单的主界面
 */
public class MainActivity extends AppCompatActivity {

    private ListView listViewTasks;
    private Button buttonAddTask;
    private TaskAdapter taskAdapter;
    private ArrayList<TaskItem> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化视图
        initViews();

        // 初始化数据
        initData();

        // 设置适配器
        taskAdapter = new TaskAdapter(this, taskList);
        listViewTasks.setAdapter(taskAdapter);

        // 设置适配器的状态变化监听器
        taskAdapter.setOnTaskStatusChangeListener((position, isCompleted) -> {
            String status = isCompleted ? "已完成" : "未完成";
            Toast.makeText(MainActivity.this, 
                "任务状态已更新: " + status, 
                Toast.LENGTH_SHORT).show();
        });

        // 设置列表项点击事件（编辑任务）
        setListViewItemClickListener();

        // 设置列表项长按事件（删除任务）
        setListViewItemLongClickListener();

        // 设置添加任务按钮点击事件
        buttonAddTask.setOnClickListener(v -> showAddTaskDialog());
    }

    /**
     * 初始化视图组件
     */
    private void initViews() {
        listViewTasks = findViewById(R.id.listViewTasks);
        buttonAddTask = findViewById(R.id.buttonAddTask);
    }

    /**
     * 初始化示例数据
     */
    private void initData() {
        taskList = new ArrayList<>();
        taskList.add(new TaskItem("Complete Android Homework", "2025-12-20"));
        taskList.add(new TaskItem("Buy groceries", "2024-05-01"));
        taskList.add(new TaskItem("Walk the dog", "2024-04-20"));
        taskList.add(new TaskItem("Call John", "2024-04-23"));
    }

    /**
     * 设置列表项点击事件监听器（编辑任务）
     */
    private void setListViewItemClickListener() {
        listViewTasks.setOnItemClickListener((parent, view, position, id) -> {
            showEditTaskDialog(position);
        });
    }

    /**
     * 设置列表项长按事件监听器（删除任务）
     */
    private void setListViewItemLongClickListener() {
        listViewTasks.setOnItemLongClickListener((parent, view, position, id) -> {
            showDeleteConfirmDialog(position);
            return true;
        });
    }

    /**
     * 显示添加任务对话框
     */
    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("添加新任务");

        // 加载自定义布局
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_task, null);
        TextInputEditText editTextTaskName = dialogView.findViewById(R.id.editTextTaskName);
        TextInputEditText editTextDueDate = dialogView.findViewById(R.id.editTextDueDate);

        builder.setView(dialogView);

        builder.setPositiveButton("添加", (dialog, which) -> {
            String taskName = editTextTaskName.getText().toString().trim();
            String dueDate = editTextDueDate.getText().toString().trim();

            if (taskName.isEmpty()) {
                Toast.makeText(this, "任务名称不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dueDate.isEmpty()) {
                Toast.makeText(this, "截止日期不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            // 添加新任务
            TaskItem newTask = new TaskItem(taskName, dueDate);
            taskList.add(newTask);
            taskAdapter.notifyDataSetChanged();
            Toast.makeText(this, "任务添加成功", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    /**
     * 显示编辑任务对话框
     * @param position 任务在列表中的位置
     */
    private void showEditTaskDialog(int position) {
        TaskItem task = taskList.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("编辑任务");

        // 加载自定义布局
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_task, null);
        TextInputEditText editTextTaskName = dialogView.findViewById(R.id.editTextTaskName);
        TextInputEditText editTextDueDate = dialogView.findViewById(R.id.editTextDueDate);

        // 填充当前任务数据
        editTextTaskName.setText(task.getTaskName());
        editTextDueDate.setText(task.getDueDate());

        builder.setView(dialogView);

        builder.setPositiveButton("保存", (dialog, which) -> {
            String taskName = editTextTaskName.getText().toString().trim();
            String dueDate = editTextDueDate.getText().toString().trim();

            if (taskName.isEmpty()) {
                Toast.makeText(this, "任务名称不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dueDate.isEmpty()) {
                Toast.makeText(this, "截止日期不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            // 更新任务
            task.setTaskName(taskName);
            task.setDueDate(dueDate);
            taskAdapter.notifyDataSetChanged();
            Toast.makeText(this, "任务更新成功", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    /**
     * 显示删除确认对话框
     * @param position 任务在列表中的位置
     */
    private void showDeleteConfirmDialog(int position) {
        TaskItem task = taskList.get(position);

        new AlertDialog.Builder(this)
                .setTitle("删除任务")
                .setMessage("确定要删除任务 \"" + task.getTaskName() + "\" 吗？")
                .setPositiveButton("删除", (dialog, which) -> {
                    taskList.remove(position);
                    taskAdapter.notifyDataSetChanged();
                    Toast.makeText(this, "任务已删除", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                .show();
    }
}