package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ListView taskListView;
    private TaskAdapter taskAdapter;
    private ArrayList<TaskItem> tasks;
    private Button addButton;
    private TextView taskCountTextView;
    private TextView completedCountTextView;
    private TextView appTitleTextView;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化视图
        taskListView = findViewById(R.id.taskListView);
        addButton = findViewById(R.id.addButton);
        taskCountTextView = findViewById(R.id.taskCountTextView);
        completedCountTextView = findViewById(R.id.completedCountTextView);
        appTitleTextView = findViewById(R.id.appTitleTextView);

        // 设置应用标题
        appTitleTextView.setText("待办事项清单");

        // 初始化日历
        calendar = Calendar.getInstance();

        // 初始化示例数据
        initializeTasks();

        // 创建适配器并设置到ListView
        taskAdapter = new TaskAdapter(this, tasks);
        taskListView.setAdapter(taskAdapter);

        // 更新任务计数
        updateTaskCount();

        // 设置任务完成监听器
        taskAdapter.setOnTaskCompletedListener(new TaskAdapter.OnTaskCompletedListener() {
            @Override
            public void onTaskCompleted(int position) {
                updateTaskCount();
                TaskItem task = tasks.get(position);
                String status = task.isCompleted() ? "已完成" : "未完成";
                Toast.makeText(MainActivity.this,
                        "任务 '" + task.getTaskName() + "' 标记为" + status,
                        Toast.LENGTH_SHORT).show();
            }
        });

        // 设置ListView点击事件
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TaskItem selectedTask = tasks.get(position);
                String status = selectedTask.isCompleted() ? "已完成" : "未完成";
                String message = "任务: " + selectedTask.getTaskName() +
                        "\n截止日期: " + selectedTask.getDueDate() +
                        "\n状态: " + status;
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });

        // 设置添加按钮点击事件
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddTaskDialog();
            }
        });
    }

    private void initializeTasks() {
        tasks = new ArrayList<>();

        // 添加示例任务
        tasks.add(new TaskItem("完成Android作业", "2025-12-20"));
        tasks.add(new TaskItem("购买杂货", "2024-05-01"));
        tasks.add(new TaskItem("遛狗", "2024-04-20", true));
        tasks.add(new TaskItem("打电话给John", "2024-04-23"));
        tasks.add(new TaskItem("准备会议", "2024-04-25"));
        tasks.add(new TaskItem("阅读书籍", "2024-04-30"));
        tasks.add(new TaskItem("健身房锻炼", "2024-04-21", true));
        tasks.add(new TaskItem("支付账单", "2024-04-22"));
        tasks.add(new TaskItem("清理房间", "2024-04-24"));
        tasks.add(new TaskItem("学习新技能", "2024-05-10"));
    }

    private void showAddTaskDialog() {
        // 创建自定义对话框
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_task);
        dialog.setTitle("添加新任务");

        // 获取对话框中的视图
        final EditText taskNameEditText = dialog.findViewById(R.id.taskNameEditText);
        final EditText dueDateEditText = dialog.findViewById(R.id.dueDateEditText);
        Button cancelButton = dialog.findViewById(R.id.cancelButton);
        Button saveButton = dialog.findViewById(R.id.saveButton);

        // 设置日期选择器
        dueDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(dueDateEditText);
            }
        });

        // 取消按钮
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // 保存按钮
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = taskNameEditText.getText().toString().trim();
                String dueDate = dueDateEditText.getText().toString().trim();

                if (taskName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "请输入任务名称", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (dueDate.isEmpty()) {
                    dueDate = getCurrentDate();
                }

                // 添加新任务
                TaskItem newTask = new TaskItem(taskName, dueDate);
                taskAdapter.addTask(newTask);
                updateTaskCount();

                Toast.makeText(MainActivity.this, "任务已添加", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showDatePicker(final EditText dueDateEditText) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
                        dueDateEditText.setText(date);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(calendar.getTime());
    }

    private void updateTaskCount() {
        int totalTasks = taskAdapter.getCount();
        int completedTasks = taskAdapter.getCompletedTaskCount();

        taskCountTextView.setText("总任务: " + totalTasks);
        completedCountTextView.setText("已完成: " + completedTasks);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTaskCount();
    }
}