package com.example.disici;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecordListActivity extends AppCompatActivity {

    private RecyclerView rvRecords;
    private TextView tvNoRecords;
    private DatabaseHelper dbHelper;
    private RecordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);

        // 初始化Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 初始化视图
        rvRecords = findViewById(R.id.rvRecords);
        tvNoRecords = findViewById(R.id.tvNoRecords);

        // 初始化数据库帮助类
        dbHelper = new DatabaseHelper(this);

        // 设置RecyclerView
        rvRecords.setLayoutManager(new LinearLayoutManager(this));

        // 加载数据
        loadRecords();
    }

    private void loadRecords() {
        List<Record> records = dbHelper.getAllRecords();

        if (records.isEmpty()) {
            tvNoRecords.setVisibility(TextView.VISIBLE);
            rvRecords.setVisibility(RecyclerView.GONE);
        } else {
            tvNoRecords.setVisibility(TextView.GONE);
            rvRecords.setVisibility(RecyclerView.VISIBLE);

            adapter = new RecordAdapter(records, record -> {
                // 点击跳转到详情页
                Intent intent = new Intent(RecordListActivity.this, RecordDetailActivity.class);
                intent.putExtra("record_id", record.getId());
                startActivity(intent);
            });

            rvRecords.setAdapter(adapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 刷新数据
        loadRecords();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
