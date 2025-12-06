package com.example.fourth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class RecordListActivity extends AppCompatActivity {

    private ListView lvRecords;
    private TextView tvEmpty;
    private MyDbHelper dbHelper;
    private List<Record> records;
    private RecordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_record_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        lvRecords = findViewById(R.id.lvRecords);
        tvEmpty = findViewById(R.id.tvEmpty);

        // Initialize database helper
        dbHelper = new MyDbHelper(this);
        records = new ArrayList<>();

        // Set up adapter
        adapter = new RecordAdapter();
        lvRecords.setAdapter(adapter);

        // Set item click listener
        lvRecords.setOnItemClickListener((parent, view, position, id) -> {
            Record record = records.get(position);
            Intent intent = new Intent(RecordListActivity.this, RecordDetailActivity.class);
            intent.putExtra("record_id", record.getId());
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecords();
    }

    private void loadRecords() {
        records.clear();
        records.addAll(dbHelper.getAllRecords());
        adapter.notifyDataSetChanged();

        // Show empty view if no records
        if (records.isEmpty()) {
            lvRecords.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            lvRecords.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
        }
    }

    private void deleteRecord(int position) {
        Record record = records.get(position);
        new AlertDialog.Builder(this)
                .setTitle("确认删除")
                .setMessage("确定要删除这条记录吗？")
                .setPositiveButton("删除", (dialog, which) -> {
                    dbHelper.deleteRecord(record.getId());
                    Toast.makeText(this, "已删除", Toast.LENGTH_SHORT).show();
                    loadRecords();
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private class RecordAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return records.size();
        }

        @Override
        public Object getItem(int position) {
            return records.get(position);
        }

        @Override
        public long getItemId(int position) {
            return records.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_record, parent, false);
                holder = new ViewHolder();
                holder.tvTitle = convertView.findViewById(R.id.tvItemTitle);
                holder.tvTime = convertView.findViewById(R.id.tvItemTime);
                holder.btnDelete = convertView.findViewById(R.id.btnDelete);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Record record = records.get(position);
            holder.tvTitle.setText(record.getTitle());
            holder.tvTime.setText(record.getTime());

            holder.btnDelete.setOnClickListener(v -> deleteRecord(position));

            return convertView;
        }

        class ViewHolder {
            TextView tvTitle;
            TextView tvTime;
            ImageButton btnDelete;
        }
    }
}
