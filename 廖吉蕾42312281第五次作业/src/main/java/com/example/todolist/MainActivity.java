package com.example.todolist;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ListView lvTasks;
    private TaskAdapter taskAdapter;
    private ArrayList<TaskItem> taskList;
    private EditText etKeyword;
    private Button btnAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化视图
        lvTasks = findViewById(R.id.lv_tasks);
        etKeyword= findViewById(R.id.et_keyword);
        btnAddTask= findViewById(R.id.btn_add_task);

        // 初始化任务数据
        taskList = new ArrayList<>();
        taskList.add(new TaskItem("Complete Android Homework", "2025-12-20",true));
        taskList.add(new TaskItem("Buy groceries", "2024-05-01",false));
        taskList.add(new TaskItem("Walk the dog", "2024-04-20",false));
        taskList.add(new TaskItem("Call John", "2024-04-23",true));

        // 初始化适配器并设置给ListView
        taskAdapter = new TaskAdapter(this, taskList);
        lvTasks.setAdapter(taskAdapter);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewTask();
            }
        });
    }
    private void addNewTask(){
        // 获取输入框中的任务名称
        String taskName = etKeyword.getText().toString().trim();

        // 校验输入是否为空
        if (taskName.isEmpty()) {
            Toast.makeText(this, "请输入任务名称", Toast.LENGTH_SHORT).show();
            return;
        }

        // 生成默认截止日期（当前日期加7天，格式：yyyy-MM-dd）
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date currentDate = new Date();
        // 计算7天后的日期（毫秒数）
        long sevenDaysLater = currentDate.getTime() + (7 * 24 * 60 * 60 * 1000);
        Date dueDate = new Date(sevenDaysLater);
        String dueDateStr = sdf.format(dueDate);

        // 新建任务（默认设为非重要任务，如需标记重要可后续扩展）
        TaskItem newTask = new TaskItem(taskName, dueDateStr, false);

        // 添加到列表并刷新适配器
        taskList.add(newTask);
        taskAdapter.notifyDataSetChanged(); // 通知列表数据已更新

        // 清空输入框
        etKeyword.setText("");

        // 提示添加成功
        Toast.makeText(this, "任务已添加", Toast.LENGTH_SHORT).show();
    }

}