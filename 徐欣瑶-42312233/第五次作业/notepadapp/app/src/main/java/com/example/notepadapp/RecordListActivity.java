package com.example.notepadapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordListActivity extends AppCompatActivity {
    private ListView listView;
    private MyDbHelper dbHelper;
    private List<Map<String, String>> records;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);

        listView = findViewById(R.id.listView);
        backButton = findViewById(R.id.backButton);
        dbHelper = new MyDbHelper(this);

        backButton.setOnClickListener(v -> {
            finish(); // 返回主界面
        });

        loadRecords();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String content = records.get(position).get("content");
            String title = records.get(position).get("title");
            showDetailDialog(title, content);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            String recordId = records.get(position).get("id");
            String recordTitle = records.get(position).get("title");
            showDeleteDialog(Integer.parseInt(recordId), recordTitle);
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecords();
    }

    private void loadRecords() {
        records = dbHelper.getAllRecords();

        List<Map<String, String>> data = new ArrayList<>();
        for (Map<String, String> record : records) {
            Map<String, String> item = new HashMap<>();
            item.put("title", record.get("title"));
            item.put("time", "时间: " + record.get("time"));
            data.add(item);
        }

        SimpleAdapter adapter = new SimpleAdapter(this,
                data,
                android.R.layout.simple_list_item_2,
                new String[]{"title", "time"},
                new int[]{android.R.id.text1, android.R.id.text2});

        listView.setAdapter(adapter);

        if (records.isEmpty()) {
            Toast.makeText(this, "暂无记录", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDetailDialog(String title, String content) {
        new AlertDialog.Builder(this)
                .setTitle("记录详情 - " + title)
                .setMessage(content)
                .setPositiveButton("确定", null)
                .show();
    }

    private void showDeleteDialog(final int id, final String title) {
        new AlertDialog.Builder(this)
                .setTitle("删除记录")
                .setMessage("确定要删除记录 \"" + title + "\" 吗？")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteRecord(id);
                        loadRecords();
                        Toast.makeText(RecordListActivity.this, "已删除", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }
}