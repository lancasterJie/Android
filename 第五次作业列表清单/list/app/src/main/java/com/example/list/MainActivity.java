package com.example.list;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<TaskItem> allTasks;
    private ArrayList<TaskItem> filteredTasks;
    private TaskAdapter adapter;
    private ListView taskListView;
    private EditText searchEditText;
    private Handler doubleClickHandler = new Handler();
    private Runnable doubleClickRunnable;
    private int lastClickedPosition = -1;
    private static final int DOUBLE_CLICK_DELAY = 300;

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

        // Initialize example data with favorites based on image
        allTasks = new ArrayList<>();
        allTasks.add(new TaskItem("Complete Android Homework", "2025-12-20", true));
        allTasks.add(new TaskItem("Buy groceries", "2024-05-01", false));
        allTasks.add(new TaskItem("Walk the dog", "2024-04-20", false));
        allTasks.add(new TaskItem("Call John", "2024-04-23", true));

        // Initialize filtered tasks with all tasks
        filteredTasks = new ArrayList<>(allTasks);

        // Setup ListView with adapter
        taskListView = findViewById(R.id.taskListView);
        adapter = new TaskAdapter(this, filteredTasks);
        adapter.setOnStarClickListener(position -> {
            TaskItem task = filteredTasks.get(position);
            task.setFavorite(!task.isFavorite());
            adapter.notifyDataSetChanged();
        });
        taskListView.setAdapter(adapter);

        // Setup search functionality
        searchEditText = findViewById(R.id.searchEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterTasks(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Setup Add Task button
        Button addTaskButton = findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(v -> {
            // For now, just clear the search. You can extend this to add new tasks.
            searchEditText.setText("");
        });

        // Setup star click listeners in adapter
        // We'll handle this by setting click listeners after view is created
        
        // Setup item click listener for double click (mark as completed)
        taskListView.setOnItemClickListener((parent, view, position, id) -> {
            // Check if this is a double click on the same item
            if (lastClickedPosition == position) {
                // Cancel the single click handler
                if (doubleClickRunnable != null) {
                    doubleClickHandler.removeCallbacks(doubleClickRunnable);
                }
                
                // Toggle completed status (double click)
                TaskItem task = filteredTasks.get(position);
                task.setCompleted(!task.isCompleted());
                adapter.notifyDataSetChanged();
                lastClickedPosition = -1;
            } else {
                // Set up for potential double click
                lastClickedPosition = position;
                doubleClickRunnable = () -> {
                    lastClickedPosition = -1;
                };
                doubleClickHandler.postDelayed(doubleClickRunnable, DOUBLE_CLICK_DELAY);
            }
        });
    }

    private void filterTasks(String keyword) {
        filteredTasks.clear();
        if (keyword == null || keyword.trim().isEmpty()) {
            filteredTasks.addAll(allTasks);
        } else {
            String lowerKeyword = keyword.toLowerCase();
            for (TaskItem task : allTasks) {
                if (task.getTaskName().toLowerCase().contains(lowerKeyword) ||
                    task.getDueDate().toLowerCase().contains(lowerKeyword)) {
                    filteredTasks.add(task);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}