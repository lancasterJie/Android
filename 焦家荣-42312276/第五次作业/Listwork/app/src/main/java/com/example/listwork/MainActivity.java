package com.example.listwork;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnTaskActionListener {

    private ArrayList<TaskItem> tasks;
    private TaskAdapter adapter;
    private TextView textViewStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化数据
        tasks = new ArrayList<>();
        tasks.add(new TaskItem("Complete Android Homework", "2025-12-20"));
        tasks.add(new TaskItem("Buy groceries", "2024-05-01"));
        tasks.add(new TaskItem("Walk the dog", "2024-04-20"));
        tasks.add(new TaskItem("Call John", "2024-04-23"));

        // 初始化UI组件
        textViewStats = findViewById(R.id.textView_stats);
        EditText editTextTask = findViewById(R.id.editText_task);
        EditText editTextDate = findViewById(R.id.editText_date);
        Button btnAdd = findViewById(R.id.btn_add);
        ListView taskListView = findViewById(R.id.task_list_view);

        // 设置适配器
        adapter = new TaskAdapter(this, R.layout.task_item, tasks);
        adapter.setOnTaskActionListener(this);
        taskListView.setAdapter(adapter);

        // 设置默认日期为今天
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        editTextDate.setText(sdf.format(new Date()));

        // 添加按钮点击事件
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = editTextTask.getText().toString().trim();
                String dueDate = editTextDate.getText().toString().trim();

                if (!taskName.isEmpty() && !dueDate.isEmpty()) {
                    tasks.add(new TaskItem(taskName, dueDate));
                    adapter.notifyDataSetChanged();
                    updateStats();
                    editTextTask.setText("");
                    editTextDate.setText(sdf.format(new Date()));
                }
            }
        });

        // 更新统计信息
        updateStats();
    }

    @Override
    public void onTaskStatusChanged(int position, boolean isCompleted) {
        tasks.get(position).setCompleted(isCompleted);
        updateStats();
    }

    @Override
    public void onTaskDeleted(int position) {
        if (position >= 0 && position < tasks.size()) {
            tasks.remove(position);
            adapter.notifyDataSetChanged();
            updateStats();
        }
    }

    private void updateStats() {
        int total = tasks.size();
        int completed = 0;

        for (TaskItem task : tasks) {
            if (task.isCompleted()) {
                completed++;
            }
        }

        textViewStats.setText("总计: " + total + " | 已完成: " + completed);
    }
}