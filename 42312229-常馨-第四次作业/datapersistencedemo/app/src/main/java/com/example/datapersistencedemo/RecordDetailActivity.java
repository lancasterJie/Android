package com.example.datapersistencedemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RecordDetailActivity extends AppCompatActivity {
    private TextView textViewTitle, textViewContent, textViewTime;
    private DatabaseHelper dbHelper;
    private Record record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        // 设置Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 初始化视图
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewContent = findViewById(R.id.textViewContent);
        textViewTime = findViewById(R.id.textViewTime);

        // 初始化数据库帮助类
        dbHelper = new DatabaseHelper(this);

        // 获取传递的记录ID
        int recordId = getIntent().getIntExtra("record_id", -1);

        if (recordId != -1) {
            // 从数据库获取记录
            record = dbHelper.getRecord(recordId);

            // 显示记录内容
            if (record != null) {
                textViewTitle.setText(record.getTitle());
                textViewContent.setText(record.getContent());
                textViewTime.setText("创建时间: " + record.getTime());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_record_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.action_delete) {
            deleteRecord();
            return true;
        } else if (id == R.id.action_edit) {
            editRecord();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteRecord() {
        if (record == null) return;

        new AlertDialog.Builder(this)
                .setTitle("删除记录")
                .setMessage("确定要删除这条记录吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteRecord(record.getId());
                        Toast.makeText(RecordDetailActivity.this, "记录已删除", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void editRecord() {
        // 这里可以实现编辑功能，为了简化，我们直接返回主界面
        Toast.makeText(this, "编辑功能将在主界面实现", Toast.LENGTH_SHORT).show();

        // 将记录内容传递到主界面进行编辑
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("edit_content", record.getContent());
        startActivity(intent);
        finish();
    }
}