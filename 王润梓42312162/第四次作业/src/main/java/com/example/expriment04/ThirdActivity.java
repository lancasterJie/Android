package com.example.expriment04;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_third);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initComponents();
    }
    void initComponents() {
        MyDBHelper helper = new MyDBHelper(this);
        ArrayList<TaskListAdapter.TaskData> taskDataList = helper.getTaskList();
        adapter = new TaskListAdapter(this, taskDataList);
        task = findViewById(R.id.task);
        time = findViewById(R.id.time);
        save = findViewById(R.id.save);
        back = findViewById(R.id.back);
        taskList = findViewById(R.id.task_list);
        taskList.setAdapter(adapter);
        if (taskDataList.isEmpty()) {
            taskDataList.add(new TaskListAdapter.TaskData("Complete Android Homework", "2025-12-20"));
            taskDataList.add(new TaskListAdapter.TaskData("Buy groceries", "2024-05-01"));
            taskDataList.add(new TaskListAdapter.TaskData("Walk the dog", "2024-04-20"));
            taskDataList.add(new TaskListAdapter.TaskData("Call John", "2024-04-23"));
            taskDataList.forEach(helper::addTaskData);
        }
        adapter.notifyDataSetChanged();
        save.setOnClickListener((v) -> {
            taskDataList.add(new TaskListAdapter.TaskData(task.getText().toString(), time.getText().toString()));
            adapter.notifyDataSetChanged();
            helper.addTaskData(taskDataList.get(taskDataList.size() - 1));
        });
        back.setOnClickListener((v) -> {
            finish();
        });
    }
    private TaskListAdapter adapter;
    private EditText task, time;
    private Button save, back;
    private ListView taskList;
}