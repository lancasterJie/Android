package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ArrayList<TaskItem> tasks; // 任务列表数据
    private TaskAdapter taskAdapter;   // 列表适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. 初始化视图
        EditText etTaskInput = findViewById(R.id.et_task_input);
        Button btnAddTask = findViewById(R.id.btn_add_task);
        ListView lvTasks = findViewById(R.id.lv_tasks);

        // 2. 初始化示例任务数据
        tasks = new ArrayList<>();
        tasks.add(new TaskItem("Complete Android Homework", "2025-12-20", true));
        tasks.add(new TaskItem("Buy groceries", "2024-05-01", false));
        tasks.add(new TaskItem("Walk the dog", "2024-04-20", false));
        tasks.add(new TaskItem("Call John", "2024-04-23", true));

        // 3. 创建适配器并绑定到ListView
        taskAdapter = new TaskAdapter(this, tasks);
        lvTasks.setAdapter(taskAdapter);

        // 4. 处理“添加任务”按钮点击
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = etTaskInput.getText().toString().trim();
                if (!taskName.isEmpty()) {
                    // 获取当前日期作为默认截止日期
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String currentDate = sdf.format(new Date());
                    // 添加新任务（默认非重要任务）
                    tasks.add(new TaskItem(taskName, currentDate, false));
                    taskAdapter.notifyDataSetChanged(); // 刷新列表
                    etTaskInput.setText(""); // 清空输入框
                } else {
                    Toast.makeText(MainActivity.this, "请输入任务名称", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}