package com.example.datapersistence;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecordListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecordAdapter adapter;
    private DatabaseManager dbManager;
    private List<Record> recordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);

        // 初始化Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("记录列表");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 初始化数据库管理器
        dbManager = new DatabaseManager(this);
        dbManager.open();

        // 初始化RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 加载数据
        loadRecords();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.close();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    // 加载记录
    private void loadRecords() {
        recordList = dbManager.getAllRecords();
        if (recordList.isEmpty()) {
            Toast.makeText(this, "暂无记录", Toast.LENGTH_SHORT).show();
        }

        adapter = new RecordAdapter(recordList);
        recyclerView.setAdapter(adapter);
    }

    // RecyclerView适配器
    private class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {
        private List<Record> records;

        public RecordAdapter(List<Record> records) {
            this.records = records;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_record, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Record record = records.get(position);
            holder.textTitle.setText(record.getTitle());
            holder.textTime.setText(record.getTime());

            // 设置点击事件
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(RecordListActivity.this, RecordDetailActivity.class);
                intent.putExtra("record_id", record.getId());
                startActivity(intent);
            });

            // 设置长按删除事件
            holder.itemView.setOnLongClickListener(v -> {
                dbManager.deleteRecord(record.getId());
                records.remove(position);
                notifyItemRemoved(position);
                Toast.makeText(RecordListActivity.this, "记录已删除", Toast.LENGTH_SHORT).show();
                return true;
            });
        }

        @Override
        public int getItemCount() {
            return records.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView textTitle;
            public TextView textTime;

            public ViewHolder(View itemView) {
                super(itemView);
                textTitle = itemView.findViewById(R.id.textTitle);
                textTime = itemView.findViewById(R.id.textTime);
            }
        }
    }
}
