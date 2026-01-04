package com.example.todolistapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ListView taskListView;
    private TextView tvEmptyList;
    private TextView tvTotalTasks, tvCompletedTasks, tvPendingTasks;
    private TextView tvLastUpdated;
    private Button btnAddTask, btnSortTasks, btnClearCompleted;

    private ArrayList<TaskItem> tasks;
    private TaskAdapter taskAdapter;

    private int currentSortMode = 0; // 0:默认, 1:按截止日期, 2:按优先级, 3:按创建时间
    private String selectedDate = ""; // 用于存储选择的日期

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化视图
        initViews();

        // 初始化数据
        initializeTasks();

        // 设置适配器
        setupAdapter();

        // 设置事件监听器
        setupListeners();

        // 更新统计信息
        updateStats();
        updateLastUpdatedTime();
    }

    private void initViews() {
        taskListView = findViewById(R.id.taskListView);
        tvEmptyList = findViewById(R.id.tvEmptyList);

        // 统计视图
        tvTotalTasks = findViewById(R.id.tvTotalTasks);
        tvCompletedTasks = findViewById(R.id.tvCompletedTasks);
        tvPendingTasks = findViewById(R.id.tvPendingTasks);
        tvLastUpdated = findViewById(R.id.tvLastUpdated);

        // 按钮
        btnAddTask = findViewById(R.id.btnAddTask);
        btnSortTasks = findViewById(R.id.btnSortTasks);
        btnClearCompleted = findViewById(R.id.btnClearCompleted);
    }

    private void initializeTasks() {
        tasks = new ArrayList<>();

        // 添加示例任务
        tasks.add(new TaskItem("完成Android作业", "2025-12-20", 1));
        tasks.add(new TaskItem("购买日用品", "2024-05-01", 2));
        tasks.add(new TaskItem("遛狗", "2024-04-20", 3));
        tasks.add(new TaskItem("给John打电话", "2024-04-23", 2));
        tasks.add(new TaskItem("准备会议报告", "2024-04-25", 1));
        tasks.add(new TaskItem("去健身房", "2024-04-22", 3));

        // 标记一个任务为已完成
        tasks.get(2).setCompleted(true);
    }

    private void setupAdapter() {
        taskAdapter = new TaskAdapter(this, R.layout.task_item, tasks);
        taskListView.setAdapter(taskAdapter);

        // 检查列表是否为空
        checkEmptyList();
    }

    private void setupListeners() {
        // 添加任务按钮
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddTaskDialog();
            }
        });

        // 排序按钮
        btnSortTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSortOptionsDialog();
            }
        });

        // 清除已完成按钮
        btnClearCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCompletedTasks();
            }
        });

        // 列表项长按删除
        taskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteDialog(position);
                return true;
            }
        });

        // 列表项点击查看详情（由适配器处理）
    }

    private void showAddTaskDialog() {
        // 创建自定义对话框视图
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_task, null);

        // 获取对话框中的视图
        final android.widget.EditText etTaskName = dialogView.findViewById(R.id.etTaskName);
        final Button btnSelectDate = dialogView.findViewById(R.id.btnSelectDate);
        final TextView tvSelectedDate = dialogView.findViewById(R.id.tvSelectedDate);
        final android.widget.RadioGroup rgPriority = dialogView.findViewById(R.id.rgPriority);

        // 设置日期选择按钮点击事件
        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(tvSelectedDate);
            }
        });

        // 创建对话框
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setPositiveButton("添加", null) // 设置为null，稍后自定义点击事件
                .setNegativeButton("取消", null);

        final android.app.AlertDialog dialog = builder.create();
        dialog.show();

        // 自定义添加按钮点击事件
        dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = etTaskName.getText().toString().trim();

                // 验证输入
                if (taskName.isEmpty()) {
                    etTaskName.setError("请输入任务名称");
                    return;
                }

                if (selectedDate.isEmpty()) {
                    tvSelectedDate.setError("请选择截止日期");
                    return;
                }

                // 获取选择的优先级
                int priority = 2; // 默认中等优先级
                int selectedId = rgPriority.getCheckedRadioButtonId();
                if (selectedId == R.id.rbHigh) {
                    priority = 1;
                } else if (selectedId == R.id.rbMedium) {
                    priority = 2;
                } else if (selectedId == R.id.rbLow) {
                    priority = 3;
                }

                // 添加新任务
                tasks.add(new TaskItem(taskName, selectedDate, priority));
                taskAdapter.notifyDataSetChanged();

                // 更新UI
                updateStats();
                checkEmptyList();
                updateLastUpdatedTime();

                // 显示成功消息
                android.widget.Toast.makeText(MainActivity.this, "任务添加成功", android.widget.Toast.LENGTH_SHORT).show();

                // 关闭对话框
                dialog.dismiss();
            }
        });
    }

    private void showDatePickerDialog(final TextView tvSelectedDate) {
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // 创建日期选择对话框
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // 月份从0开始，所以需要+1
                        selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
                        tvSelectedDate.setText("已选择: " + selectedDate);
                    }
                },
                year, month, day);

        // 显示对话框
        datePickerDialog.show();
    }

    private void showSortOptionsDialog() {
        final String[] sortOptions = {"默认排序", "按截止日期", "按优先级", "按创建时间"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("选择排序方式")
                .setItems(sortOptions, (dialog, which) -> {
                    currentSortMode = which;
                    sortTasks(which);
                })
                .show();
    }

    private void sortTasks(int sortMode) {
        switch (sortMode) {
            case 0: // 默认排序
                // 按创建时间排序
                Collections.sort(tasks, new Comparator<TaskItem>() {
                    @Override
                    public int compare(TaskItem t1, TaskItem t2) {
                        return Long.compare(t1.getCreatedAt(), t2.getCreatedAt());
                    }
                });
                break;

            case 1: // 按截止日期
                Collections.sort(tasks, new Comparator<TaskItem>() {
                    @Override
                    public int compare(TaskItem t1, TaskItem t2) {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            Date d1 = sdf.parse(t1.getDueDate());
                            Date d2 = sdf.parse(t2.getDueDate());
                            return d1.compareTo(d2);
                        } catch (Exception e) {
                            return 0;
                        }
                    }
                });
                break;

            case 2: // 按优先级
                Collections.sort(tasks, new Comparator<TaskItem>() {
                    @Override
                    public int compare(TaskItem t1, TaskItem t2) {
                        // 优先级数字越小优先级越高
                        return Integer.compare(t1.getPriority(), t2.getPriority());
                    }
                });
                break;

            case 3: // 按创建时间
                Collections.sort(tasks, new Comparator<TaskItem>() {
                    @Override
                    public int compare(TaskItem t1, TaskItem t2) {
                        // 创建时间越晚越靠前
                        return Long.compare(t2.getCreatedAt(), t1.getCreatedAt());
                    }
                });
                break;
        }

        taskAdapter.notifyDataSetChanged();
        updateLastUpdatedTime();
        android.widget.Toast.makeText(this, "已按选择的方式排序", android.widget.Toast.LENGTH_SHORT).show();
    }

    private void clearCompletedTasks() {
        // 找出所有已完成的任务
        ArrayList<TaskItem> completedTasks = new ArrayList<>();
        for (TaskItem task : tasks) {
            if (task.isCompleted()) {
                completedTasks.add(task);
            }
        }

        if (completedTasks.isEmpty()) {
            android.widget.Toast.makeText(this, "没有已完成的任务", android.widget.Toast.LENGTH_SHORT).show();
            return;
        }

        // 确认对话框
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("清除已完成任务")
                .setMessage("确定要清除 " + completedTasks.size() + " 个已完成的任务吗？")
                .setPositiveButton("清除", (dialog, which) -> {
                    // 移除所有已完成的任务
                    tasks.removeAll(completedTasks);
                    taskAdapter.notifyDataSetChanged();

                    // 更新UI
                    updateStats();
                    checkEmptyList();
                    updateLastUpdatedTime();

                    android.widget.Toast.makeText(MainActivity.this, "已清除 " + completedTasks.size() + " 个任务", android.widget.Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void showDeleteDialog(final int position) {
        TaskItem task = tasks.get(position);

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("删除任务")
                .setMessage("确定要删除任务:\n" + task.getTaskName() + " 吗？")
                .setPositiveButton("删除", (dialog, which) -> {
                    tasks.remove(position);
                    taskAdapter.notifyDataSetChanged();

                    // 更新UI
                    updateStats();
                    checkEmptyList();
                    updateLastUpdatedTime();

                    android.widget.Toast.makeText(MainActivity.this, "任务已删除", android.widget.Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void updateStats() {
        int total = tasks.size();
        int completed = taskAdapter.getCompletedCount();
        int pending = taskAdapter.getPendingCount();

        tvTotalTasks.setText("总计: " + total);
        tvCompletedTasks.setText("已完成: " + completed);
        tvPendingTasks.setText("待完成: " + pending);
    }

    private void checkEmptyList() {
        if (tasks.isEmpty()) {
            tvEmptyList.setVisibility(View.VISIBLE);
            taskListView.setVisibility(View.GONE);
        } else {
            tvEmptyList.setVisibility(View.GONE);
            taskListView.setVisibility(View.VISIBLE);
        }
    }

    private void updateLastUpdatedTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String time = sdf.format(new Date());
        tvLastUpdated.setText("更新于: " + time);
    }

    // 保存数据到SharedPreferences（可选功能）
    private void saveTasks() {
        // 这里可以添加保存任务到本地存储的代码
        // 可以使用SharedPreferences或SQLite数据库
    }

    // 从SharedPreferences加载数据（可选功能）
    private void loadTasks() {
        // 这里可以添加从本地存储加载任务的代码
    }
}