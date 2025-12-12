package com.example.task5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<TaskItem> allTasks;       // 存储所有任务（原始数据）
    private ArrayList<TaskItem> filteredTasks;  // 搜索筛选后的任务
    private TaskListAdapter adapter;            // 列表适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. 初始化所有任务（初始无星星）
        initAllTasks();

        // 2. 初始筛选列表 = 所有任务
        filteredTasks = new ArrayList<>(allTasks);

        // 3. 绑定ListView和适配器
        ListView lvTasks = findViewById(R.id.lv_tasks);
        adapter = new TaskListAdapter(this, filteredTasks);
        lvTasks.setAdapter(adapter);

        // 4. 初始化搜索功能
        initSearchFunction();

        // 5. 初始化Add Task功能
        initAddTaskFunction();
    }

    // 初始化原始任务数据（初始无星星）
    private void initAllTasks() {
        allTasks = new ArrayList<>();
        allTasks.add(new TaskItem("Complete Android Homework", "2025-12-20"));
        allTasks.add(new TaskItem("Buy groceries", "2024-05-01"));
        allTasks.add(new TaskItem("Walk the dog", "2024-04-20"));
        allTasks.add(new TaskItem("Call John", "2024-04-23"));
    }

    // 初始化搜索功能：实时筛选任务
    private void initSearchFunction() {
        EditText etSearch = findViewById(R.id.et_search);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 关键词为空：显示所有任务；否则筛选包含关键词的任务
                filterTasks(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    // 搜索筛选核心逻辑（忽略大小写）
    private void filterTasks(String keyword) {
        filteredTasks.clear();
        if (keyword.isEmpty()) {
            filteredTasks.addAll(allTasks);
        } else {
            String lowerKeyword = keyword.toLowerCase();
            for (TaskItem task : allTasks) {
                // 同时搜索任务名称和日期
                if (task.getTaskName().toLowerCase().contains(lowerKeyword) ||
                        task.getDueDate().toLowerCase().contains(lowerKeyword)) {
                    filteredTasks.add(task);
                }
            }
        }
        adapter.notifyDataSetChanged(); // 直接通知数据集变化
    }

    // 初始化Add Task功能：弹出对话框添加任务
    private void initAddTaskFunction() {
        Button btnAdd = findViewById(R.id.btn_add_task);
        btnAdd.setOnClickListener(v -> {
            // 加载添加任务的对话框布局
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_task, null);
            EditText etTaskName = dialogView.findViewById(R.id.et_task_name);
            EditText etDueDate = dialogView.findViewById(R.id.et_due_date);

            // 构建对话框
            new AlertDialog.Builder(this)
                    .setTitle("添加新任务")
                    .setView(dialogView)
                    .setPositiveButton("确认", (dialog, which) -> {
                        // 获取输入内容
                        String taskName = etTaskName.getText().toString().trim();
                        String dueDate = etDueDate.getText().toString().trim();

                        // 校验输入（不能为空）
                        if (taskName.isEmpty() || dueDate.isEmpty()) {
                            Toast.makeText(MainActivity.this, "任务名称和日期不能为空！", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // 添加新任务到原始列表
                        allTasks.add(new TaskItem(taskName, dueDate));
                        String currentKeyword = ((EditText) findViewById(R.id.et_search)).getText().toString().trim();
                        filterTasks(currentKeyword);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "任务添加成功！", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("取消", null)
                    .show();
        });
    }
}