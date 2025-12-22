package com.example.txte5;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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

        ListView listView = findViewById(R.id.listViewTasks);

        ArrayList<TaskItem> tasks = new ArrayList<>();
        tasks.add(new TaskItem("Complete Android Homework", "2025-12-20"));
        tasks.add(new TaskItem("Buy groceries", "2024-05-01"));
        tasks.add(new TaskItem("Walk the dog", "2024-04-20"));
        tasks.add(new TaskItem("Call John", "2024-04-23"));

        TaskAdapter adapter = new TaskAdapter(this, tasks);
        listView.setAdapter(adapter);
    }
}