package com.example.fileinputoutput;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecordListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecordAdapter adapter;
    private List<Record> recordList;
    private MyDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_record_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 初始化数据库帮助类
        dbHelper = new MyDbHelper(this);
        
        // 初始化RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        // 加载记录数据
        loadRecords();
        
        // 设置适配器
        adapter = new RecordAdapter(this, recordList, record -> {
            // 点击列表项，跳转到详情界面
            Intent intent = new Intent(RecordListActivity.this, RecordDetailActivity.class);
            intent.putExtra("record_id", record.getId());
            intent.putExtra("record_title", record.getTitle());
            intent.putExtra("record_content", record.getContent());
            intent.putExtra("record_time", record.getTime());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }

    /**
     * 从数据库加载所有记录
     */
    private void loadRecords() {
        recordList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        
        // 查询所有记录
        Cursor cursor = db.query("records", null, null, null, null, null, "_id DESC");
        
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                
                // 创建Record对象并添加到列表
                Record record = new Record(id, title, content, time);
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 重新加载数据，确保显示最新记录
        loadRecords();
        adapter.notifyDataSetChanged();
    }
}