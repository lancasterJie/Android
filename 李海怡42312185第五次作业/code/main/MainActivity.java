package com.example.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnTaskStatusChangedListener {

    private ListView listViewTasks;
    private ArrayList<TaskItem> taskList;
    private TaskAdapter adapter;
    private FloatingActionButton fabAddTask;
    private Toolbar toolbar;
    private Calendar selectedCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化工具栏
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("待办事项清单");

        // 初始化ListView
        listViewTasks = findViewById(R.id.listViewTasks);

        // 初始化添加按钮
        fabAddTask = findViewById(R.id.fabAddTask);

        // 创建示例数据
        initializeSampleData();

        // 创建并设置适配器
        adapter = new TaskAdapter(this, taskList);
        adapter.setOnTaskStatusChangedListener(this);
        listViewTasks.setAdapter(adapter);

        // 设置列表项点击事件
        listViewTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TaskItem selectedTask = taskList.get(position);
                String message = "任务: " + selectedTask.getTaskName() +
                        "\n截止日期: " + selectedTask.getDueDate() +
                        "\n状态: " + (selectedTask.isCompleted() ? "已完成" : "未完成");
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        // 设置长按删除事件
        listViewTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteConfirmationDialog(position);
                return true;
            }
        });

        // 设置添加按钮点击事件
        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddTaskDialog();
            }
        });
    }

    // 初始化示例数据
    private void initializeSampleData() {
        taskList = new ArrayList<>();
        taskList.add(new TaskItem("Complete Android Homework", "2025-12-20"));
        taskList.add(new TaskItem("Buy groceries", "2024-05-01"));
        taskList.add(new TaskItem("Walk the dog", "2024-04-20"));
        taskList.add(new TaskItem("Call John", "2024-04-23"));    }

    // 显示添加任务对话框
    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("添加新任务");

        // 加载自定义布局
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_task, null);
        final EditText etTaskName = dialogView.findViewById(R.id.etTaskName);
        final EditText etDueDate = dialogView.findViewById(R.id.etDueDate);
        final Button btnDatePicker = dialogView.findViewById(R.id.btnDatePicker);

        // 初始化日历，默认设置为明天
        selectedCalendar = Calendar.getInstance();
        selectedCalendar.add(Calendar.DAY_OF_YEAR, 1);

        // 设置默认日期为明天
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String defaultDate = sdf.format(selectedCalendar.getTime());
        etDueDate.setText(defaultDate);

        // 设置日期选择按钮点击事件
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(etDueDate);
            }
        });

        // 设置日期输入框点击事件
        etDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(etDueDate);
            }
        });

        builder.setView(dialogView);

        // 设置对话框按钮
        builder.setPositiveButton("添加", (dialog, which) -> {
            String taskName = etTaskName.getText().toString().trim();
            String dueDate = etDueDate.getText().toString().trim();

            if (taskName.isEmpty()) {
                Toast.makeText(MainActivity.this, "请输入任务名称", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dueDate.isEmpty()) {
                Toast.makeText(MainActivity.this, "请选择截止日期", Toast.LENGTH_SHORT).show();
                return;
            }

            // 添加新任务
            TaskItem newTask = new TaskItem(taskName, dueDate);
            taskList.add(0, newTask); // 添加到列表顶部
            adapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "任务已添加", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // 显示日期选择器对话框
    private void showDatePickerDialog(final EditText etDueDate) {
        if (selectedCalendar == null) {
            selectedCalendar = Calendar.getInstance();
            selectedCalendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        int year = selectedCalendar.get(Calendar.YEAR);
        int month = selectedCalendar.get(Calendar.MONTH);
        int day = selectedCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // 更新日历对象
                        selectedCalendar.set(year, month, dayOfMonth);

                        // 格式化日期为 yyyy-MM-dd
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        String selectedDate = sdf.format(selectedCalendar.getTime());
                        etDueDate.setText(selectedDate);
                    }
                },
                year, month, day
        );

        // 设置最小日期为今天
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        datePickerDialog.show();
    }

    // 显示删除确认对话框
    private void showDeleteConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确认删除");
        builder.setMessage("确定要删除这个任务吗？");

        builder.setPositiveButton("删除", (dialog, which) -> {
            TaskItem taskToRemove = taskList.get(position);
            taskList.remove(position);
            adapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this,
                    "已删除: " + taskToRemove.getTaskName(),
                    Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // 实现任务状态变化监听器
    @Override
    public void onTaskStatusChanged(int position, boolean isCompleted) {
        TaskItem task = taskList.get(position);
        // 这里可以添加其他逻辑，比如保存到数据库等
        // 不需要显示Toast，因为删除由Adapter处理
    }

    // 创建选项菜单（右上角菜单）
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // 处理菜单项点击
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add) {
            showAddTaskDialog();
            return true;
        } else if (id == R.id.action_clear_completed) {
            clearCompletedTasks();
            return true;
        } else if (id == R.id.action_stats) {
            showTaskStats();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // 清除已完成的任务
    private void clearCompletedTasks() {
        int originalSize = taskList.size();
        taskList.removeIf(TaskItem::isCompleted);
        int removedCount = originalSize - taskList.size();
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "已清除 " + removedCount + " 个已完成任务", Toast.LENGTH_SHORT).show();
    }

    // 显示任务统计
    private void showTaskStats() {
        int totalTasks = taskList.size();
        int completedTasks = 0;

        for (TaskItem task : taskList) {
            if (task.isCompleted()) {
                completedTasks++;
            }
        }

        String stats = "总任务数: " + totalTasks +
                "\n已完成: " + completedTasks +
                "\n未完成: " + (totalTasks - completedTasks);

        new AlertDialog.Builder(this)
                .setTitle("任务统计")
                .setMessage(stats)
                .setPositiveButton("确定", null)
                .show();
    }
}
