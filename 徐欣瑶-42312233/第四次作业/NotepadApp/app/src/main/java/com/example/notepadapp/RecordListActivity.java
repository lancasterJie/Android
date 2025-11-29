package com.example.notepadapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class RecordListActivity extends AppCompatActivity {
    private ListView listView;
    private MyDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);

        listView = findViewById(R.id.listView);
        dbHelper = new MyDbHelper(this);

        loadRecords();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Record record = (Record) parent.getItemAtPosition(position);
            Intent intent = new Intent(this, RecordDetailActivity.class);
            intent.putExtra("content", record.getContent());
            startActivity(intent);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Record record = (Record) parent.getItemAtPosition(position);
            dbHelper.deleteRecord(record.getId());
            loadRecords();
            Toast.makeText(this, "已删除", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void loadRecords() {
        List<Record> records = dbHelper.getAllRecords();
        ArrayAdapter<Record> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, records);
        listView.setAdapter(adapter);
    }
}