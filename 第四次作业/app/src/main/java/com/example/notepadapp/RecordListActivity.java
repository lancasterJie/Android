package com.example.notepadapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordListActivity extends AppCompatActivity {
    private ListView listViewRecords;
    private MyDbHelper dbHelper;
    private List<Record> records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);

        // 初始化Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        listViewRecords = findViewById(R.id.listViewRecords);
        dbHelper = new MyDbHelper(this);

        // 加载记录列表
        loadRecords();

        // 点击列表项查看详情
        listViewRecords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Record record = records.get(position);
                Intent intent = new Intent(RecordListActivity.this, RecordDetailActivity.class);
                intent.putExtra("record_id", record.getId());
                startActivity(intent);
            }
        });

        // 长按删除记录
        listViewRecords.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Record record = records.get(position);
                dbHelper.deleteRecord(record.getId());
                loadRecords(); // 重新加载列表
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 从其他界面返回时刷新列表
        loadRecords();
    }

    /**
     * 加载记录列表
     */
    private void loadRecords() {
        records = dbHelper.getAllRecords();
        List<Map<String, String>> data = new ArrayList<>();
        
        for (Record record : records) {
            Map<String, String> map = new HashMap<>();
            map.put("title", record.getTitle());
            map.put("time", record.getTime());
            data.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                data,
                android.R.layout.simple_list_item_2,
                new String[]{"title", "time"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );
        
        listViewRecords.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

