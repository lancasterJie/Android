package com.example.sql;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

/**
 * 展示 SQLite 中所有记录的列表。
 */
public class RecordListActivity extends AppCompatActivity {

    private database dbHelper;
    private RecordListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);

        dbHelper = new database(this);
        adapter = new RecordListAdapter(this);

        ListView listView = findViewById(R.id.list_records);
        listView.setAdapter(adapter);
        listView.setEmptyView(findViewById(R.id.text_empty));
        listView.setOnItemClickListener(this::onRecordClick);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecords();
    }

    private void loadRecords() {
        List<Record> records = dbHelper.getAllRecords();
        adapter.submit(records);
    }

    private void onRecordClick(AdapterView<?> parent, android.view.View view, int position, long id) {
        Intent intent = new Intent(this, RecordDetailActivity.class);
        intent.putExtra(RecordDetailActivity.EXTRA_RECORD_ID, id);
        startActivity(intent);
    }
}

