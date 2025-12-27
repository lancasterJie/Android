package com.example.myproject4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView;
import android.view.View;
import android.content.Intent;

public class RecordListActivity extends AppCompatActivity {

    ListView listView;
    TextView empty;

    private MyDbHelper dbHelper;

    private List<Record> recordList;

    private RecordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_record_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView=findViewById(R.id.list_view);
        empty=findViewById(R.id.textEmpty);
        dbHelper=new MyDbHelper(this);
        recordList=new ArrayList<>();

        setupAdapter();

        loadRecords();

        setupEventListeners();
    }

    private void setupAdapter()
    {
        adapter=new RecordAdapter(this,recordList);

        listView.setAdapter(adapter);

        listView.setEmptyView(empty);
    }

    private void loadRecords()//从数据库加载记录
    {
        List<Record> records = dbHelper.getAllRecords();

        recordList.clear();

        recordList.addAll(records);

        adapter.notifyDataSetChanged();

    }

    private void setupEventListeners() //点击事件监听器
    {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {

                Record record = recordList.get(position);

                goToDetailPage(record);
            }
        });

    }

    private void goToDetailPage(Record record) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("record_id", record.getId());
        startActivity(intent);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        loadRecords();
    }
}
