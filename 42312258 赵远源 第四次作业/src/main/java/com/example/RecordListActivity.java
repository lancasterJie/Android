package com.example;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.data.MyDbHelper;

/**
 * 展示 SQLite 中所有记录的列表页，支持点击查看详情、长按删除。
 */
public class RecordListActivity extends AppCompatActivity {

    private ListView listRecords;
    private TextView emptyView;
    private SimpleCursorAdapter adapter;
    private MyDbHelper dbHelper;
    private Cursor currentCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);

        dbHelper = new MyDbHelper(this);

        listRecords = findViewById(R.id.list_records);
        emptyView = findViewById(R.id.tv_empty);
        listRecords.setEmptyView(emptyView);

        // Use built-in two-line item layout for title/time pairs
        // 使用系统提供的双行布局展示 title / time
        adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                null,
                new String[]{"title", "time"},
                new int[]{android.R.id.text1, android.R.id.text2},
                0
        );
        listRecords.setAdapter(adapter);

        listRecords.setOnItemClickListener((parent, view, position, id) -> openDetail(id));
        listRecords.setOnItemLongClickListener((parent, view, position, id) -> {
            confirmDelete(id);
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecords(); // 页面可见时刷新列表
    }

    @Override
    protected void onDestroy() {
        if (currentCursor != null) {
            currentCursor.close();
        }
        dbHelper.close();
        super.onDestroy();
    }

    /**
     * 查询所有记录并切换 Cursor 给适配器。
     */
    private void loadRecords() {
        Cursor newCursor = dbHelper.getReadableDatabase().query(
                MyDbHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                "time DESC"
        );

        if (currentCursor != null) {
            currentCursor.close();
        }
        currentCursor = newCursor;
        adapter.changeCursor(newCursor);
    }

    /**
     * 根据点击的 ID 查询具体记录并跳转详情页。
     */
    private void openDetail(long id) {
        Cursor cursor = dbHelper.getReadableDatabase().query(
                MyDbHelper.TABLE_NAME,
                null,
                "_id = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
            String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
            RecordDetailActivity.start(this, title, content, time);
        }
        cursor.close();
    }

    /**
     * 弹出确认对话框，用户同意后删除对应记录。
     */
    private void confirmDelete(long id) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_delete_title)
                .setMessage(R.string.dialog_delete_message)
                .setPositiveButton(R.string.action_delete, (dialog, which) -> {
                    dbHelper.getWritableDatabase().delete(
                            MyDbHelper.TABLE_NAME,
                            "_id = ?",
                            new String[]{String.valueOf(id)}
                    );
                    loadRecords();
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }
}

