package com.example.datapersistencedemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordListActivity extends AppCompatActivity {
    private ListView listViewRecords;
    private DatabaseHelper dbHelper;
    private List<Record> recordList;
    private SimpleAdapter adapter;
    private List<Map<String, String>> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);

        // 设置Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 初始化视图
        listViewRecords = findViewById(R.id.listViewRecords);

        // 初始化数据库帮助类
        dbHelper = new DatabaseHelper(this);

        // 初始化数据
        dataList = new ArrayList<>();

        // 设置适配器
        adapter = new SimpleAdapter(this,
                dataList,
                R.layout.item_record,
                new String[]{"title", "time"},
                new int[]{R.id.textViewTitle, R.id.textViewTime});

        listViewRecords.setAdapter(adapter);

        // 设置列表项点击监听器
        listViewRecords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 跳转到详情页面
                Record record = recordList.get(position);
                Intent intent = new Intent(RecordListActivity.this, RecordDetailActivity.class);
                intent.putExtra("record_id", record.getId());
                startActivity(intent);
            }
        });

        // 设置列表项长按监听器（删除功能）
        listViewRecords.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(RecordListActivity.this)
                        .setTitle("删除记录")
                        .setMessage("确定要删除这条记录吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Record record = recordList.get(position);
                                dbHelper.deleteRecord(record.getId());
                                loadRecords();
                                Toast.makeText(RecordListActivity.this, "记录已删除", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                return true;
            }
        });

        // 加载记录
        loadRecords();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 重新加载记录（可能在详情页面进行了编辑）
        loadRecords();
    }

    private void loadRecords() {
        // 从数据库获取所有记录
        recordList = dbHelper.getAllRecords();

        // 清空数据列表
        dataList.clear();

        // 将记录添加到数据列表
        for (Record record : recordList) {
            Map<String, String> map = new HashMap<>();
            map.put("title", record.getTitle());
            map.put("time", record.getTime());
            dataList.add(map);
        }

        // 通知适配器数据已更新
        adapter.notifyDataSetChanged();

        // 如果没有任何记录，显示提示
        if (recordList.isEmpty()) {
            Toast.makeText(this, "暂无记录", Toast.LENGTH_SHORT).show();
        }
    }
}