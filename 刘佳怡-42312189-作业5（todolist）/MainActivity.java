package com.example.work5listview;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etTaskInput;
    private Button btnAddTask;
    private ListView lvTasks;
    private ArrayList<TaskItem> taskItems;
    private com.example.work5listview.TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化控件
        etTaskInput = findViewById(R.id.etTaskInput);
        btnAddTask = findViewById(R.id.btnAddTask);
        lvTasks = findViewById(R.id.lvTasks);

        // 初始化任务列表
        taskItems = new ArrayList<>();
        taskItems.add(new TaskItem("Complete Android Homework", "2025-12-20", true));
        taskItems.add(new TaskItem("Buy groceries", "2024-05-01"));
        taskItems.add(new TaskItem("Walk the dog", "2024-04-20"));
        taskItems.add(new TaskItem("Call John", "2024-04-23", true));

        // 设置适配器
        adapter = new com.example.work5listview.TaskAdapter(this, taskItems);
        lvTasks.setAdapter(adapter);

        // 添加任务按钮点击事件
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = etTaskInput.getText().toString().trim();
                if (!taskName.isEmpty()) {
                    // 简单默认日期，实际可使用 DatePicker
                    String dueDate = "2025-01-01"; // 示例
                    TaskItem newItem = new TaskItem(taskName, dueDate);
                    taskItems.add(newItem);
                    adapter.notifyDataSetChanged();
                    etTaskInput.setText("");
                }
            }
        });
    }
}