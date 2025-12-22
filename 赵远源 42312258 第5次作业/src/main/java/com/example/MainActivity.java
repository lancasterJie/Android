package com.example;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<TaskItem> tasks;
    private TaskAdapter adapter;
    private EditText taskNameEditText;
    private EditText dueDateEditText;
    private Button addTaskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 初始化示例数据
        tasks = new ArrayList<>();
        tasks.add(new TaskItem("Complete Android Homework", "2025-12-20"));
        tasks.add(new TaskItem("Buy groceries", "2024-05-01"));
        tasks.add(new TaskItem("Walk the dog", "2024-04-20"));
        tasks.add(new TaskItem("Call John", "2024-04-23"));

        // 获取视图组件
        ListView taskListView = findViewById(R.id.taskListView);
        taskNameEditText = findViewById(R.id.taskNameEditText);
        dueDateEditText = findViewById(R.id.dueDateEditText);
        addTaskButton = findViewById(R.id.addTaskButton);

        // 设置适配器
        adapter = new TaskAdapter(this, tasks);
        taskListView.setAdapter(adapter);

        // 设置添加按钮的点击事件
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });
    }

    private void addTask() {
        String taskName = taskNameEditText.getText().toString().trim();
        String dueDate = dueDateEditText.getText().toString().trim();

        // 验证输入
        if (TextUtils.isEmpty(taskName)) {
            Toast.makeText(this, "请输入任务名称", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(dueDate)) {
            Toast.makeText(this, "请输入截止日期", Toast.LENGTH_SHORT).show();
            return;
        }

        // 添加新任务
        TaskItem newTask = new TaskItem(taskName, dueDate);
        tasks.add(newTask);
        adapter.notifyDataSetChanged();

        // 清空输入框
        taskNameEditText.setText("");
        dueDateEditText.setText("");

        // 显示提示信息
        Toast.makeText(this, "任务已添加", Toast.LENGTH_SHORT).show();
    }
}