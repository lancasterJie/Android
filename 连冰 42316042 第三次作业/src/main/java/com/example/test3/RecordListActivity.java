package com.example.test3;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class RecordListActivity extends AppCompatActivity {

    private MyDbHelper dbHelper;
    private RecordListAdapter adapter;
    private List<Record> records = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);

        ListView listView = findViewById(R.id.listRecords);
        TextView emptyView = findViewById(R.id.textEmpty);

        dbHelper = new MyDbHelper(this);
        adapter = new RecordListAdapter(this, records);

        listView.setAdapter(adapter);
        listView.setEmptyView(emptyView);

        listView.setOnItemClickListener(this::onRecordClicked);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecords();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

    private void loadRecords() {
        records.clear();
        Cursor cursor = dbHelper.getReadableDatabase()
                .query("records", null, null, null, null, null, "_id DESC");
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
            String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
            records.add(new Record(id, title, content, time));
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    private void onRecordClicked(AdapterView<?> parent, View view, int position, long id) {
        Record record = records.get(position);
        Intent intent = new Intent(this, RecordDetailActivity.class);
        intent.putExtra("title", record.getTitle());
        intent.putExtra("content", record.getContent());
        intent.putExtra("time", record.getTime());
        startActivity(intent);
    }
}

