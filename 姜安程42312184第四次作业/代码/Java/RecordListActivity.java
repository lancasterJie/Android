package com.example.homework4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import java.util.List;

public class RecordListActivity extends AppCompatActivity {
    private RecyclerView recordsRecyclerView;
    private TextView emptyTextView;
    private RecordAdapter adapter;
    private List<Record> recordList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);

        recordsRecyclerView = findViewById(R.id.recordsRecyclerView);
        emptyTextView = findViewById(R.id.emptyTextView);

        dbHelper = new DatabaseHelper(this);

        setupRecyclerView();
        loadRecords();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecords();
    }

    private void setupRecyclerView() {
        recordsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecordAdapter(this, recordList);
        recordsRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // 点击查看详情
                Record record = recordList.get(position);
                Intent intent = new Intent(RecordListActivity.this, RecordDetailActivity.class);
                intent.putExtra("record_id", record.getId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position) {
                // 长按删除记录
                showDeleteDialog(position);
            }
        });
    }

    private void loadRecords() {
        recordList = dbHelper.getAllRecords();
        adapter = new RecordAdapter(this, recordList);
        recordsRecyclerView.setAdapter(adapter);

        if (recordList.isEmpty()) {
            emptyTextView.setVisibility(TextView.VISIBLE);
            recordsRecyclerView.setVisibility(RecyclerView.GONE);
        } else {
            emptyTextView.setVisibility(TextView.GONE);
            recordsRecyclerView.setVisibility(RecyclerView.VISIBLE);
        }

        // 重新设置监听器
        adapter.setOnItemClickListener(new RecordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Record record = recordList.get(position);
                Intent intent = new Intent(RecordListActivity.this, RecordDetailActivity.class);
                intent.putExtra("record_id", record.getId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position) {
                showDeleteDialog(position);
            }
        });
    }

    private void showDeleteDialog(int position) {
        new AlertDialog.Builder(this)
                .setTitle("删除记录")
                .setMessage("确定要删除这条记录吗？")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Record record = recordList.get(position);
                        dbHelper.deleteRecord(record.getId());
                        loadRecords(); // 重新加载列表
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }
}