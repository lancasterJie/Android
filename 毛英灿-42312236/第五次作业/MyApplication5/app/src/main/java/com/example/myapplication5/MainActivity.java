package com.example.myapplication5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<TaskItem> tasks;
    private TaskListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasks = new ArrayList<>();
        tasks.add(new TaskItem("Complete Android Homework", "2025-12-20"));
        tasks.add(new TaskItem("Buy groceries", "2024-05-01"));
        tasks.add(new TaskItem("Walk the dog", "2024-04-20"));
        tasks.add(new TaskItem("Call John", "2024-04-23"));

        ListView lvTasks = findViewById(R.id.lv_tasks);
        adapter = new TaskListAdapter(this, R.layout.list_item_task, tasks);
        lvTasks.setAdapter(adapter);

        lvTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TaskItem clickedTask = tasks.get(position);
                Toast.makeText(MainActivity.this,
                        "任务：" + clickedTask.getTaskName() + "\n截止日期：" + clickedTask.getDueDate(),
                        Toast.LENGTH_LONG).show();
            }
        });

        lvTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("删除确认")
                        .setMessage("确定要删除「" + tasks.get(position).getTaskName() + "」吗？")
                        .setPositiveButton("删除", (dialog, which) -> {
                            tasks.remove(position);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(MainActivity.this, "任务已删除", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("取消", null)
                        .show();
                return true;
            }
        });
    }
}