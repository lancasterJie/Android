package com.dyk.homework;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class TaskDetailActivity extends AppCompatActivity {

    private MyDBHelper db;
    private CheckBox isImportant;
    private CheckBox isDone;
    private EditText taskContent;
    private Button updateTask;
    private Button addTask;
    private long ID;
    private static final int ADD_NEW_TASK_CODE = 1;
    private static final int UPDATE_TASK_CODE = 2;
    private Button taskDeadlineSelector;
    private TextView taskDeadline;
    private String selectedDateStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task_detail);

        initView();
        setListener();
    }

    private void setListener() {
        addTask.setOnClickListener(v->{
            // 添加新任务
            String task_content = taskContent.getText().toString();
            boolean is_done = isDone.isChecked();
            boolean is_important = isImportant.isChecked();

            if(task_content.isEmpty()){
                Toast.makeText(this, "请输入要完成的任务内容", Toast.LENGTH_SHORT).show();
                finish();
            }
            ToDoItem new_item = new ToDoItem(task_content,selectedDateStr,is_done,is_important);
            long insertID = db.insert(new_item);
            if(insertID == -1)
            {
                Toast.makeText(this, "新任务创建失败，请稍后重试", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this, "新任务创建成功，快去完成吧", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        updateTask.setOnClickListener(v->{
            // 添加新任务
            String task_content = taskContent.getText().toString();
            boolean is_done = isDone.isChecked();
            boolean is_important = isImportant.isChecked();

            if(task_content.isEmpty()){
                Toast.makeText(this, "请输入要完成的任务内容", Toast.LENGTH_SHORT).show();
                finish();
            }
            ToDoItem item = new ToDoItem(ID,task_content,selectedDateStr,is_done,is_important);
            long rows = db.update(item);
            if (rows == 0){
                Toast.makeText(this, "更新失败，请稍后重试", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this, "任务更新成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        taskDeadlineSelector.setOnClickListener(v->{
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH); // 0-based (0 = January)
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                            // 注意：month 是从 0 开始的，所以要 +1
                            Calendar selectedCalendar = Calendar.getInstance();
                            selectedCalendar.set(year, month, dayOfMonth);

                            // 格式化为 "yyyy-MM-dd"
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            selectedDateStr = sdf.format(selectedCalendar.getTime());

                            // 显示在 TextView 上
                            taskDeadline.setText(selectedDateStr);
                        }
                    },
                    year, month, day
            );

            // 可选：设置标题
            datePickerDialog.setTitle("选择截止日期");
            datePickerDialog.show();
        });
    }

    private void initView() {
        taskContent = findViewById(R.id.task_content);
        isDone = findViewById(R.id.isDone);
        isImportant = findViewById(R.id.isImportant);
        addTask = findViewById(R.id.add_task);
        updateTask = findViewById(R.id.update_task);
        taskDeadlineSelector = findViewById(R.id.task_deadline_selector);
        taskDeadline = findViewById(R.id.task_deadline);
        db = new MyDBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null &&!extras.isEmpty()){
            int requestCode = extras.getInt("requestCode");
            if(requestCode == ADD_NEW_TASK_CODE){
                addTask.setVisibility(View.VISIBLE);
                updateTask.setVisibility(View.GONE);
            }else if(requestCode == UPDATE_TASK_CODE){
                addTask.setVisibility(View.GONE);
                updateTask.setVisibility(View.VISIBLE);

                ID = extras.getLong("ID");
                taskContent.setText(extras.getString("taskContent"));
                isDone.setChecked(extras.getBoolean("isDone"));
                isImportant.setChecked(extras.getBoolean("isImportant"));
                selectedDateStr = extras.getString("task_date");
                taskDeadline.setText(selectedDateStr);
            }
        }
    }


}