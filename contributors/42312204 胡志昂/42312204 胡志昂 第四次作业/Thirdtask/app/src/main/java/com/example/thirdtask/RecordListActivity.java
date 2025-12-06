package com.example.thirdtask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordListActivity extends AppCompatActivity {
    private ListView listViewRecords;
    private RecordDao recordDao;
    private List<Record> recordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);

        // 初始化Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 显示返回按钮
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 初始化视图
        listViewRecords = findViewById(R.id.listViewRecords);

        // 初始化数据库操作类
        recordDao = new RecordDao(this);

        // 加载记录列表
        loadRecords();

        // 设置列表项点击监听器
        listViewRecords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Record record = recordList.get(position);
                Intent intent = new Intent(RecordListActivity.this, DetailActivity.class);
                intent.putExtra("record_id", record.getId());
                startActivity(intent);
            }
        });

        // 设置长按删除功能
        listViewRecords.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Record record = recordList.get(position);
                int result = recordDao.deleteRecord(record.getId());
                if (result > 0) {
                    Toast.makeText(RecordListActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    loadRecords(); // 重新加载列表
                }
                return true;
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecords(); // 每次返回时重新加载
    }

    // 加载记录列表
    private void loadRecords() {
        recordList = recordDao.getAllRecords();

        // 创建数据列表
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
                android.R.layout.simple_list_item_2,
                new String[]{"title", "time"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );

        // 设置适配器
        listViewRecords.setAdapter(adapter);
    }
}