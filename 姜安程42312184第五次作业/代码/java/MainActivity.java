package com.example.homework5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView taskListView;
    private TaskAdapter taskAdapter;
    private ArrayList<TaskItem> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化
        taskListView = findViewById(R.id.taskListView);
        initializeTasks();

        // 设置适配器
        taskAdapter = new TaskAdapter(this, tasks);
        taskListView.setAdapter(taskAdapter);

        // 1. 点击查看任务详情
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TaskItem task = tasks.get(position);

                // 创建对话框显示详情
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("任务详情")
                        .setMessage("任务名称: " + task.getTaskName() +
                                "\n截止日期: " + task.getDueDate())
                        .setPositiveButton("确定", null)
                        .show();
            }
        });

        // 2. 长按删除任务
        taskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // 显示删除确认对话框
                showDeleteDialog(position);
                return true;
            }
        });
    }

    private void showDeleteDialog(final int position) {
        new AlertDialog.Builder(this)
                .setTitle("删除任务")
                .setMessage("确定要删除 \"" + tasks.get(position).getTaskName() + "\" 吗？")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String taskName = tasks.get(position).getTaskName();
                        tasks.remove(position);
                        taskAdapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this,
                                "已删除: " + taskName, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void initializeTasks() {
        tasks = new ArrayList<>();
        tasks.add(new TaskItem("Complete Android Homework", "2025-12-20"));
        tasks.add(new TaskItem("Buy groceries", "2024-05-01"));
        tasks.add(new TaskItem("Walk the dog", "2024-04-20"));
        tasks.add(new TaskItem("Call John", "2024-04-23"));
    }
}
