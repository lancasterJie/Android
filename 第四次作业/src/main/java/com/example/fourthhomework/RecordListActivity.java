package com.example.fourthhomework;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecordListActivity extends AppCompatActivity {
    private RecyclerView rvRecords;
    private Button btnBack;
    private RecordAdapter adapter;
    private RecordDao recordDao;
    private List<Record> recordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);

        // 初始化控件
        initViews();

        // 初始化数据库操作类
        recordDao = new RecordDao(this);

        // 加载所有记录并显示
        loadRecords();

        // 设置按钮点击事件
        setClickListeners();
    }

    // 每次回到列表界面，刷新数据（比如编辑/新增后返回）
    @Override
    protected void onResume() {
        super.onResume();
        loadRecords();
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        btnBack = findViewById(R.id.btn_back);
        rvRecords = findViewById(R.id.rv_records);
        // 设置 RecyclerView 布局管理器（线性布局，垂直方向）
        rvRecords.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * 加载所有记录并更新 RecyclerView
     */
    private void loadRecords() {
        recordList = recordDao.getAllRecords();
        // 初始化适配器（首次加载）或更新数据（刷新时）
        if (adapter == null) {
            adapter = new RecordAdapter(recordList);
            rvRecords.setAdapter(adapter);
            // 设置列表项点击事件（跳转到详情界面）
            adapter.setOnItemClickListener(record -> {
                Intent intent = new Intent(RecordListActivity.this, RecordDetailActivity.class);
                intent.putExtra("RECORD_ID", record.get_id()); // 传递记录 ID
                startActivity(intent);
            });
            // 设置列表项长按事件（删除记录）
            adapter.setOnItemLongClickListener(record -> {
                boolean isDeleted = recordDao.deleteRecord(record.get_id());
                if (isDeleted) {
                    Toast.makeText(RecordListActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    loadRecords(); // 刷新列表
                } else {
                    Toast.makeText(RecordListActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            adapter.updateData(recordList); // 刷新数据
        }
    }

    /**
     * 设置按钮点击事件
     */
    private void setClickListeners() {
        // 返回主界面
        btnBack.setOnClickListener(v -> finish());
    }
}