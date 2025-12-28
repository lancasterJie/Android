package com.zzx.homework;


import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        SearchView searchView = findViewById(R.id.searchView);
        Button btnAdd = findViewById(R.id.btnAdd);

        ArrayList<TaskItem> tasks = new ArrayList<>();
        tasks.add(new TaskItem("Complete Android Homework", "2025-12-20"));
        tasks.add(new TaskItem("Buy groceries", "2024-05-01"));
        tasks.add(new TaskItem("Walk the dog", "2024-04-20"));
        tasks.add(new TaskItem("Call John", "2024-04-23"));

        adapter = new TaskAdapter(this, tasks);
        listView.setAdapter(adapter);

        // 点击切换完成状态
        listView.setOnItemClickListener((parent, view, position, id) -> {
            TaskItem task = adapter.getItem(position);
            task.toggleCompleted();
            adapter.notifyDataSetChanged();
        });

        // 搜索
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) { return false; }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });

        // 添加任务
        btnAdd.setOnClickListener(v -> showAddDialog());
    }

    private void showAddDialog() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        EditText name = new EditText(this);
        name.setHint("Task Name");

        EditText date = new EditText(this);
        date.setHint("Due Date (YYYY-MM-DD)");

        layout.addView(name);
        layout.addView(date);

        new AlertDialog.Builder(this)
                .setTitle("Add Task")
                .setView(layout)
                .setPositiveButton("Add", (d, w) -> {
                    if (!name.getText().toString().isEmpty()) {
                        adapter.addTask(new TaskItem(
                                name.getText().toString(),
                                date.getText().toString()
                        ));
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
