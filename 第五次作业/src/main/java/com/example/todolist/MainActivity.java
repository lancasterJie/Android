package com.example.todolist;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView taskListView;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private TextView addTaskBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化视图
        taskListView = findViewById(R.id.taskListView);
        addTaskBtn = findViewById(R.id.addTaskBtn);

        // 初始化任务列表数据（模拟数据）
        initTaskList();

        // 设置适配器
        taskAdapter = new TaskAdapter(this, taskList);
        taskListView.setAdapter(taskAdapter);

        // 设置列表项点击事件
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = taskList.get(position);
                Toast.makeText(MainActivity.this,
                        "选中: " + task.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        // 设置添加任务按钮点击事件
        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "添加新任务", Toast.LENGTH_SHORT).show();

                // 示例：添加一个新任务
                addNewTask("New Task", "2024-04-25", false);
            }
        });
    }

    private void initTaskList() {
        taskList = new ArrayList<>();

        // 添加示例任务（与图片中的任务一致）
        taskList.add(new Task("Complete Android Homework", "2025-12-20", false));
        taskList.add(new Task("Buy groceries", "2024-05-01", true));  // 有过期标记
        taskList.add(new Task("Walk the dog", "2024-04-20", true));   // 有过期标记
        taskList.add(new Task("Call John", "2024-04-23", false));
    }

    private void addNewTask(String title, String date, boolean isOverdue) {
        Task newTask = new Task(title, date, isOverdue);
        taskList.add(0, newTask); // 添加到列表开头
        taskAdapter.notifyDataSetChanged();
    }
}