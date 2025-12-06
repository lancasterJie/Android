package com.example.fifth;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ListView lvTasks;
    private EditText etTaskInput;
    private Button btnAddTask;
    private ArrayList<TaskItem> tasks;
    private TaskAdapter adapter;

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

        // 初始化视图
        lvTasks = findViewById(R.id.lv_tasks);
        etTaskInput = findViewById(R.id.et_task_input);
        btnAddTask = findViewById(R.id.btn_add_task);

        // 初始化示例数据
        initSampleData();

        // 设置适配器
        adapter = new TaskAdapter(this, tasks);
        lvTasks.setAdapter(adapter);

        // 设置列表项点击事件 - 切换完成状态
        lvTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TaskItem task = tasks.get(position);
                task.setCompleted(!task.isCompleted());
                adapter.notifyDataSetChanged();
            }
        });

        // 设置列表项长按事件 - 删除任务
        lvTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TaskItem task = tasks.get(position);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("删除任务")
                        .setMessage("确定要删除任务 \"" + task.getTaskName() + "\" 吗？")
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tasks.remove(position);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(MainActivity.this, "任务已删除", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                return true;
            }
        });

        // 设置添加任务按钮点击事件
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = etTaskInput.getText().toString().trim();
                if (!taskName.isEmpty()) {
                    // 获取当前日期作为默认截止日期
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String currentDate = sdf.format(new Date());
                    
                    TaskItem newTask = new TaskItem(taskName, currentDate);
                    tasks.add(newTask);
                    adapter.notifyDataSetChanged();
                    etTaskInput.setText("");
                    Toast.makeText(MainActivity.this, "任务已添加", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "请输入任务名称", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initSampleData() {
        tasks = new ArrayList<>();
        TaskItem task1 = new TaskItem("Complete Android Homework", "2025-12-20");
        task1.setCompleted(true);  // 根据图片，第一个任务已完成
        tasks.add(task1);
        
        tasks.add(new TaskItem("Buy groceries", "2024-05-01"));
        
        TaskItem task3 = new TaskItem("Walk the dog", "2024-04-20");
        task3.setCompleted(true);  // 根据图片，这个任务有删除线，表示已完成
        tasks.add(task3);
        
        TaskItem task4 = new TaskItem("Call John", "2024-04-23");
        task4.setCompleted(true);  // 根据图片，这个任务有星星，表示已完成
        tasks.add(task4);
    }
}