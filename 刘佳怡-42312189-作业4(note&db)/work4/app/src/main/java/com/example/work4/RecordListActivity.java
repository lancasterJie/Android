// RecordListActivity.java (使用 SimpleAdapter 绑定你提供的 item_record.xml)
package com.example.work4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);

        ListView listView = findViewById(R.id.listView);
        MyDbHelper dbHelper = new MyDbHelper(this);
        List<Record> records = dbHelper.getAllRecords();

        List<Map<String, String>> data = new ArrayList<>();
        for (Record r : records) {
            Map<String, String> map = new HashMap<>();
            map.put("title", r.title);
            map.put("time", r.time);
            data.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                data,
                R.layout.item_record,
                new String[]{"title", "time"},
                new int[]{R.id.textTitle, R.id.textTime}
        );

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Record record = records.get(position);
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("record", record.toBundle());
            startActivity(intent);
        });
    }
}