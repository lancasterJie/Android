package com.example.homework6;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView taskListView = findViewById(R.id.taskListView);
        ArrayList<TaskItem> tasks = new ArrayList<>();
        tasks.add(new TaskItem("Complete Android Homework", "2025-12-20"));
        tasks.add(new TaskItem("Buy groceries", "2024-05-01"));
        tasks.add(new TaskItem("Walk the dog", "2024-04-20"));
        tasks.add(new TaskItem("Call John", "2024-04-23"));

        TaskAdapter adapter = new TaskAdapter(this, tasks);
        taskListView.setAdapter(adapter);
    }
}