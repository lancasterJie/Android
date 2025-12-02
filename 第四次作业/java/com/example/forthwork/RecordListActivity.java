package com.example.forthwork;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordListActivity extends AppCompatActivity {
    private ListView listViewRecords;
    private DatabaseHelper dbHelper;
    private List<Record> recordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);

        listViewRecords = findViewById(R.id.listViewRecords);
        dbHelper = new DatabaseHelper(this);

        // 加载记录列表
        loadRecords();

        // 列表项点击事件
        listViewRecords.setOnItemClickListener((parent, view, position, id) -> {
            Record record = recordList.get(position);
            Intent intent = new Intent(RecordListActivity.this, RecordDetailActivity.class);
            intent.putExtra("record_id", record.getId());
            startActivity(intent);
        });

        // 长按删除
        listViewRecords.setOnItemLongClickListener((parent, view, position, id) -> {
            Record record = recordList.get(position);
            dbHelper.deleteRecord(record.getId());
            Toast.makeText(this, "记录已删除", Toast.LENGTH_SHORT).show();
            loadRecords(); // 重新加载列表
            return true;
        });
    }

    private void loadRecords() {
        recordList = dbHelper.getAllRecords();

        // 创建SimpleAdapter需要的数据
        List<Map<String, String>> data = new ArrayList<>();
        for (Record record : recordList) {
            Map<String, String> map = new HashMap<>();
            map.put("title", record.getTitle());
            map.put("time", record.getTime());
            data.add(map);
        }

        // 创建适配器
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                data,
                R.layout.list_item_record,
                new String[]{"title", "time"},
                new int[]{R.id.textViewTitle, R.id.textViewTime}
        );

        listViewRecords.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecords(); // 每次返回时刷新列表
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}