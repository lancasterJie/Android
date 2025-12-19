package com.example.todolist;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private TaskAdapter adapter;
    private EditText etSearch;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSearch = findViewById(R.id.etSearch);
        btnAdd = findViewById(R.id.btnAdd);
        listView = findViewById(R.id.listView);

        adapter = new TaskAdapter(this);
        listView.setAdapter(adapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnAdd.setOnClickListener(v -> showAddTaskDialog());

        listView.setOnItemClickListener((parent, view, position, id) -> {
        });
    }

    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("添加新任务");

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_todo, null);
        builder.setView(dialogView);

        EditText etTaskName = dialogView.findViewById(R.id.etTaskName);
        EditText etDueDate = dialogView.findViewById(R.id.etDueDate);

        etDueDate.setOnClickListener(v -> showDatePicker(etDueDate));

        builder.setPositiveButton("添加", (dialog, which) -> {
            String taskName = etTaskName.getText().toString().trim();
            String dueDate = etDueDate.getText().toString().trim();

            if (taskName.isEmpty()) {
                Toast.makeText(this, "请输入任务名称", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dueDate.isEmpty()) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                dueDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month, day);
            }

            TaskItem newTask = new TaskItem(taskName, dueDate);
            adapter.addTask(newTask);

            // 滚动到顶部
            listView.smoothScrollToPosition(0);

            Toast.makeText(this, "任务添加成功", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("取消", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDatePicker(EditText etDueDate) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String formattedDate = String.format(Locale.getDefault(),
                            "%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                    etDueDate.setText(formattedDate);
                },
                year, month, day
        );

        datePickerDialog.show();
    }
}