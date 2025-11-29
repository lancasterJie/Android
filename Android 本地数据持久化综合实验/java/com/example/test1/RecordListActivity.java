package com.example.test1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RecordListActivity extends AppCompatActivity {

    private ListView listView;
    private MyDbHelper dbHelper;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);

        initViews();
        setupListView();
        loadRecords();
    }

    private void initViews() {
        listView = findViewById(R.id.listView);
        dbHelper = new MyDbHelper(this);
    }

    private void setupListView() {
        adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2,
                null,
                new String[]{"title", "time"},
                new int[]{android.R.id.text1, android.R.id.text2},
                0);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(RecordListActivity.this, "点击了记录 " + id, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadRecords() {
        Cursor cursor = dbHelper.getAllRecords();
        adapter.changeCursor(cursor);

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "暂无记录", Toast.LENGTH_SHORT).show();
        }
    }
}