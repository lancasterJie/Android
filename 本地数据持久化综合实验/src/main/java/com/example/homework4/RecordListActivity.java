package com.example.homework4;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
    }

    private void loadRecords() {
        Cursor cursor = dbHelper.getAllRecords();
        // 处理 cursor 数据，显示在 ListView 中
        // 例如: 使用 SimpleCursorAdapter
    }
}
