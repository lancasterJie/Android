package com.example.work4;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.work4.adapter.RecordAdapter;
import com.example.work4.db.MyDbHelper;

public class RecordListActivity extends AppCompatActivity {
    private ListView listViewRecords;
    private MyDbHelper dbHelper;
    private RecordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);

        listViewRecords = findViewById(R.id.listViewRecords);
        dbHelper = new MyDbHelper(this);

        loadRecords();

        listViewRecords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) adapter.getItem(position);
                int recordId = cursor.getInt(cursor.getColumnIndexOrThrow(MyDbHelper.COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MyDbHelper.COLUMN_TITLE));
                String content = cursor.getString(cursor.getColumnIndexOrThrow(MyDbHelper.COLUMN_CONTENT));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(MyDbHelper.COLUMN_TIME));

                Intent intent = new Intent(RecordListActivity.this, RecordDetailActivity.class);
                intent.putExtra("id", recordId);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                intent.putExtra("time", time);
                startActivity(intent);
            }
        });
    }

    private void loadRecords() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                MyDbHelper.TABLE_RECORDS,
                null, null, null, null, null,
                MyDbHelper.COLUMN_TIME + " DESC"
        );

        adapter = new RecordAdapter(this, cursor);
        listViewRecords.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecords(); // 刷新列表
    }
}