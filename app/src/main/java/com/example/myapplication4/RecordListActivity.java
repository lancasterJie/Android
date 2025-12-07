package com.example.myapplication4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class RecordListActivity extends AppCompatActivity {
    private ListView lvRecords;
    private MyDbHelper dbHelper;
    private List<Record> recordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);

        lvRecords = findViewById(R.id.lvRecords);
        dbHelper = new MyDbHelper(this);

        loadRecords();

        lvRecords.setOnItemClickListener((parent, view, position, id) -> {
            Record record = recordList.get(position);
            Intent intent = new Intent(RecordListActivity.this, RecordDetailActivity.class);
            intent.putExtra("record_id", record.getId());
            startActivity(intent);
        });
    }

    private void loadRecords() {
        recordList = dbHelper.getAllRecords();

        String[] titles = new String[recordList.size()];
        for (int i = 0; i < recordList.size(); i++) {
            titles[i] = recordList.get(i).getTitle() + "\n" + recordList.get(i).getTime();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                titles) {
            @Override
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                android.widget.TextView text1 = view.findViewById(android.R.id.text1);
                android.widget.TextView text2 = view.findViewById(android.R.id.text2);

                Record record = recordList.get(position);
                text1.setText(record.getTitle());
                text2.setText(record.getTime());

                return view;
            }
        };

        lvRecords.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecords(); // 刷新列表
    }
}